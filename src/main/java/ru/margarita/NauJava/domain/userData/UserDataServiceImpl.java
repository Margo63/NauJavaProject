package ru.margarita.NauJava.domain.userData;

import org.springframework.stereotype.Service;
import ru.margarita.NauJava.entities.UserData;
import ru.margarita.NauJava.repositories.UserDataRepository;

import java.util.List;

/**
 * Реализация {@link UserDataService}
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-12-15
 */
@Service
public class UserDataServiceImpl implements UserDataService{

    private final UserDataRepository userDataRepository;

    public UserDataServiceImpl(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    @Override
    public UserData createUserData(String surname, String patronymic, String job) {
        UserData userData = new UserData(surname,patronymic,job);
        userDataRepository.save(userData);
        return userData;
    }

    @Override
    public UserData createUserData(UserData userData) {
        userDataRepository.save(userData);
        return userData;
    }

    @Override
    public UserData findUserDataById(Long id) {

        if(userDataRepository.findById(id).isPresent()) return userDataRepository.findById(id).get();
        return null;
    }

    @Override
    public void deleteById(Long id) {
        userDataRepository.deleteById(id);
    }

    @Override
    public void updateUser(Long id, String surname, String patronymic, String job) {
        userDataRepository.updateUserData(id,surname,patronymic,job);
    }

    @Override
    public List<UserData> getAllUsersData() {
        return (List<UserData>) userDataRepository.findAll();
    }
}
