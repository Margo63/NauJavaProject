package ru.margarita.NauJava.domain.userData;


import ru.margarita.NauJava.entities.UserData;

import java.util.List;

/**
 * Класс сервис для взаимодействия с бд через слой данных
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-12-15
 */
public interface UserDataService {

    /**
     * метод для создания данных о пользователе
     * */
    UserData createUserData(String surname, String patronymic, String job);
    /**
     * метод для создания данных о пользователе
     * */
    UserData createUserData(UserData userData);

    /**
     * метод для поиске данных о пользователе по id
     * */
    UserData findUserDataById(Long id);

    /**
     * метод удаления по id
     * */
    void deleteById(Long id);

    /**
     * метод для обновления данных о пользователе
     * */
    void updateUser(Long id, String surname, String patronymic, String newJob);

    /**
     * метод для получения всех данных о пользователях
     * */
    List<UserData> getAllUsersData();
}
