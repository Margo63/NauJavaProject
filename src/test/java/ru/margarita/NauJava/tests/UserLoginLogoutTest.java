package ru.margarita.NauJava.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserLoginLogoutTest {

    private WebDriver driver;
    @Value("${server.port}")
    private int port;


    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Тестирование авторизации и входа <br>
     * <ol>
     * <b>Подготовка</b>
     * <li>открытие страницы входа</li>
     * <br>
     * <b>Действие</b>
     * <li>получение элементов страницы входа</li>
     * <li>Заполнение форм</li>
     * <li>Нажатие на кнопку входа</li>
     * <li>получение элементов страницы</li>
     * <li>Нажатие на кнопку выхода</li>
     * <br>
     * <b>Проверки</b>
     * <li>Совершен переход на страницу пользователя после входа</li>
     * <li>Совершен переход на страницу входа, после выхода из системы</li>
     * <ol/>
     */
    @Test
    void testSuccessfulLoginAndLogout(){
        //подготовка
        driver.get("http://localhost:"+port+"/login");

        //действие
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Sign in']"));


        usernameInput.sendKeys("admin");
        passwordInput.sendKeys("admin");

        loginButton.click();
        String loginState = Objects.requireNonNull(driver.getCurrentUrl());

        WebElement logoutButton = driver.findElement(By.id("logout"));
        logoutButton.click();

        //проверки
        assertTrue(loginState.contains("/custom/users/view/user"));
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}