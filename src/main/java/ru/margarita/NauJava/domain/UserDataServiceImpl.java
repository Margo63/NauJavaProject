package ru.margarita.NauJava.domain;

import org.springframework.stereotype.Service;
import ru.margarita.NauJava.entities.UserData;

import java.util.List;

@Service
public class UserDataServiceImpl implements UserDataService{
    @Override
    public boolean createUserData(String name, String email) {
        return false;
    }

    @Override
    public UserData findUserDataById(Long id) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean updateUser(Long id, String newEmail, String newPassword) {
        return false;
    }

    @Override
    public List<UserData> getAllUsersData() {
        return List.of();
    }
}
