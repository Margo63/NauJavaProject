package ru.margarita.NauJava.domain;


import ru.margarita.NauJava.entities.UserData;

import java.util.List;

public interface UserDataService {
    boolean createUserData(String name, String email);
    UserData findUserDataById(Long id);
    boolean deleteById(Long id);
    boolean updateUser(Long id, String newEmail, String newPassword);
    List<UserData> getAllUsersData();
}
