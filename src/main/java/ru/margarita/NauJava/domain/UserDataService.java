package ru.margarita.NauJava.domain;


import ru.margarita.NauJava.entities.UserData;

import java.util.List;

public interface UserDataService {
    boolean createUserData(String surname, String patronymic, String job);
    UserData findUserDataById(Long id);
    boolean deleteById(Long id);
    boolean updateUser(Long id, String surname, String patronymic, String newJob);
    List<UserData> getAllUsersData();
}
