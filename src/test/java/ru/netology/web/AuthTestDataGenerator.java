package ru.netology.web;

import com.github.javafaker.Faker;

import java.util.Locale;

public class AuthTestDataGenerator {

    static public RegistrationDto generateUser(String locale, String status) {
        Faker faker = new Faker(new Locale(locale));
        return new RegistrationDto(
                faker.name().username(),
                faker.internet().password(),
                status
        );
    }

    static public RegistrationDto generateUser(String locale, String login, String status) {
        Faker faker = new Faker(new Locale(locale));
        return new RegistrationDto(
                login,
                faker.internet().password(),
                status
        );
    }

    static public String generateLogin(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().username();
    }

}
