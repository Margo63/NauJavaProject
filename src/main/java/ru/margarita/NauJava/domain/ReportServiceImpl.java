package ru.margarita.NauJava.domain;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.margarita.NauJava.repositories.ReportRepository;
import ru.margarita.NauJava.repositories.TaskRepository;
import ru.margarita.NauJava.repositories.UserRepository;
import ru.margarita.NauJava.entities.Report;
import ru.margarita.NauJava.entities.ReportStatus;
import ru.margarita.NauJava.entities.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Класс для обработки отчета
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-11-11
 */
@Service
public class ReportServiceImpl {
    public final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private StringBuilder html;

    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public Long createReport() {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        reportRepository.save(report);
        return report.getId();
    }

    public Optional<Report> getReport(Long id) {
        return reportRepository.findById(id);
    }

    @Async
    public void generateReport(Long reportId) {
        CompletableFuture.runAsync(() -> {
            Report report = reportRepository.findById(reportId).orElseThrow();

            long totalStart = System.currentTimeMillis();

            try {
                AtomicLong usersCount = new AtomicLong();
                final List<Task> tasks = new ArrayList<>();
                AtomicLong usersElapsed = new AtomicLong();
                AtomicLong tasksElapsed = new AtomicLong();
                CompletableFuture<Void> threadCountUsers = runTask(userRepository::count, usersElapsed, usersCount::set);

                CompletableFuture<Void> threadGetTasks = runTask( () -> new ArrayList<>((Collection<? extends Task>) taskRepository.findAll()),tasksElapsed , tasks::addAll);

                threadCountUsers.join();
                threadGetTasks.join();


                long totalElapsed = System.currentTimeMillis() - totalStart;

                // html report
                html = new StringBuilder("<html><body>");
                addText("amount users: " + usersCount);
                addText("time count users: " + usersElapsed);
                addText("amount tasks: " + tasks.size());
                addText("time count tasks: " + tasksElapsed);
                addText("all time: " + totalElapsed);
                addText("List tasks: ");
                String tblR = "</td><td>";
                String tblC = "</th><th>";
                html.append("<table border='1'><tr><th>ID").append(tblC).append("title").append(tblC).append("user</th></tr>");
                for (var task : tasks) {
                    html.append("<tr><td>").append(task.getId()).append(tblR)
                            .append(task.getTitle()).append(tblR)
                            .append(task.getUser().getName()).append("</td></tr>");
                }
                html.append("</table></body></html>");

                report.setContent(html.toString());
                report.setStatus(ReportStatus.COMPLETED);
                reportRepository.save(report);

            } catch (CompletionException e) {
                report.setStatus(ReportStatus.ERROR);
                report.setContent("Ошибка при формировании отчёта: " + e.getMessage());
                reportRepository.save(report);
            }
        });
    }

    public String getMessage(Report report) {
        return switch (report.getStatus()) {
            case CREATED -> "Отчет еще формируется";
            case ERROR -> "Ошибка формирования: " + report.getContent();
            case COMPLETED -> "done";
        };
    }

    private void addText(String text) {
        html.append("<p>").append(text).append("</p>");
    }

    private static <T> CompletableFuture<Void> runTask(Supplier<T> task, AtomicLong elapsedTime, Consumer<T> resultConsumer) {
        return CompletableFuture.runAsync(() -> {
            long start = System.currentTimeMillis();
            T result = task.get();
            elapsedTime.set(System.currentTimeMillis() - start);
            resultConsumer.accept(result);
        });
    }
}