package ru.margarita.NauJava.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.margarita.NauJava.data.repositories.UserRepository;
import ru.margarita.NauJava.domain.UserDetailServiceImpl;
import ru.margarita.NauJava.entities.User;

import java.util.Collections;
import java.util.List;

class UserDetailServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тестирование поиск пользователя по имени
     * положительный <br>
     * <ol>
     * <b>Подготовка</b>
     * <li>Создать пользователя</li>
     * <li>установлена роль админа</li>
     * <br>
     * <b>Действие</b>
     * <li>Получение пользователя по имени</li>
     * <br>
     * <b>Проверки</b>
     * <li>Полученные данные не нулевые</li>
     * <li>имя полученного пользователя совпадает с созданным</li>
     * <li>пароль полученного пользователя совпадает с созданным</li>
     * <li>роль пользователя-админ</li>
     * <ol/>
     */
    @Test
    void testLoadUserByUsernamePositive() {
        //подготовка
        User mockUser = new User("testuser", "mail", "password");
        mockUser.setAdmin(true);
        when(userRepository.findByName(mockUser.getName())).thenReturn(List.of(mockUser));

        //действие
        UserDetails userDetails = userDetailService.loadUserByUsername(mockUser.getName());

        //проверки
        assertNotNull(userDetails);
        assertEquals(mockUser.getName(), userDetails.getUsername());
        assertEquals(mockUser.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }

    /**
     * Тестирование поиск пользователя по имени
     * негативный <br>
     * <ol>
     * <b>Подготовка</b>
     * <li>Получение пользователя по не существующему имени</li>
     * <br>
     * <b>Проверки</b>
     * <li>Получена ошибка UsernameNotFoundException</li>
     */
    @Test
    void testLoadUserByUsernameNegative() {
        //подготовка
        String name = "unknown";
        when(userRepository.findByName(name)).thenReturn(Collections.emptyList());

        //проверка
        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername(name));
    }
}