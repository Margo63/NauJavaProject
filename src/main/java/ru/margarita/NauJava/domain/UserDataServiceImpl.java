package ru.margarita.NauJava.domain;

import org.springframework.stereotype.Service;
import ru.margarita.NauJava.entities.UserData;
import ru.margarita.NauJava.repositories.UserDataRepository;

import java.util.List;

@Service
public class UserDataServiceImpl implements UserDataService{

    private final UserDataRepository userDataRepository;

    public UserDataServiceImpl(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    @Override
    public boolean createUserData(String surname, String patronymic, String job) {
        userDataRepository.save(new UserData(surname,patronymic,job));
        return true;
    }

    @Override
    public UserData findUserDataById(Long id) {

        if(userDataRepository.findById(id).isPresent()) return userDataRepository.findById(id).get();
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        userDataRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateUser(Long id, String surname, String patronymic, String job) {
        userDataRepository.updateUserData(id,surname,patronymic,job);
        return true;
    }

    @Override
    public List<UserData> getAllUsersData() {
        return (List<UserData>) userDataRepository.findAll();
    }
}
