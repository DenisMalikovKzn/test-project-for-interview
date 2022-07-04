package ru.project.service;

import ru.project.model.entity.OAuthAccessToken;
import ru.project.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    OAuthAccessToken findTokenForUser(String username);

    User findUserByToken(String token);

    Boolean isPresent(String token);

    OAuthAccessToken getTokenFromRequest(HttpServletRequest request);
}
