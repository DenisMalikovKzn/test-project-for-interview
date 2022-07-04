package ru.project.service;

import ru.project.model.dto.user.SignInDto;
import ru.project.model.dto.user.SignUpDto;
import ru.project.model.entity.OAuthAccessToken;

public interface UserService {

    OAuthAccessToken signUp(SignUpDto signUpDto);

    OAuthAccessToken signIn(SignInDto signInDto);

}
