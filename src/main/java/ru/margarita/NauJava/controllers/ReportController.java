package ru.margarita.NauJava.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.margarita.NauJava.domain.ReportServiceImpl;
import ru.margarita.NauJava.entities.Report;
import java.util.Optional;

/**
 * Класс контроллер для отчета
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-11-11
 */
@Controller
public class ReportController {
    private final ReportServiceImpl reportService;

    public ReportController(ReportServiceImpl reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/reports")
    public String createReport() {
        Long reportId = reportService.createReport();
        reportService.generateReport(reportId);
        return "redirect:/reports/" + reportId;
    }

    @GetMapping("/reports/{id}")
    public String getReport(@PathVariable Long id, Model model) {
        Optional<Report> reportOpt = reportService.getReport(id);
        if (reportOpt.isEmpty()) {
            return ResponseEntity.notFound().toString();
        }
        Report report = reportOpt.get();
        model.addAttribute("report", report.getContent());
        model.addAttribute("message", reportService.getMessage(report));
        return "report";

    }
}