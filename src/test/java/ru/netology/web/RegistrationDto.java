package ru.netology.web;

import com.google.gson.annotations.SerializedName;
import lombok.Value;

// Data-класс
@Value
public class RegistrationDto {
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_BLOCKED = "blocked";

    @SerializedName("login") String login;
    @SerializedName("password") String password;
    @SerializedName("status") String status;
}
