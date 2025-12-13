package ru.margarita.NauJava.domain.status;

import ru.margarita.NauJava.entities.Status;
import ru.margarita.NauJava.entities.StatusCodes;

import java.util.List;

public interface StatusService {

    List<Status> getAllStatuses();
}
