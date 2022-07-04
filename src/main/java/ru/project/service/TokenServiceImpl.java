package ru.project.service;

import org.springframework.stereotype.Service;
import ru.project.dao.TokenRepository;
import ru.project.dao.UserRepository;
import ru.project.model.CustomException;
import ru.project.model.ExceptionResponseCode;
import ru.project.model.entity.OAuthAccessToken;
import ru.project.model.entity.User;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public TokenServiceImpl(TokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OAuthAccessToken findTokenForUser(String username) {
        return tokenRepository.findByUsername(username).orElseThrow(() -> new CustomException(ExceptionResponseCode.TOKEN_NOT_FOUND));
    }

    @Override
    public User findUserByToken(String token) {
        OAuthAccessToken oAuthAccessToken = tokenRepository.findByAccessToken(token).orElseThrow(() -> new CustomException(ExceptionResponseCode.TOKEN_NOT_FOUND));
        User user = userRepository.findByUsername(oAuthAccessToken.getUsername()).orElseThrow(() -> new CustomException(ExceptionResponseCode.USER_WITH_THIS_TOKEN_NOT_FOUND));
        return user;
    }

    @Override
    public Boolean isPresent(String token) {
        return tokenRepository.findByAccessToken(token).isPresent();
    }

    @Override
    public OAuthAccessToken getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (!authHeader.split(" ")[0].equals("Bearer")) {
            throw new CustomException(ExceptionResponseCode.AUTHORIZATION_HEADER_WRONG_FORMAT);
        }
        String token = authHeader.split(" ")[1];
        if (token.isEmpty()) {
            throw new CustomException(ExceptionResponseCode.AUTHORIZATION_WRONG_TOKEN);
        }
        if (!isPresent(token)) {
            throw new CustomException(ExceptionResponseCode.AUTHORIZATION_WRONG_TOKEN);
        }
        return tokenRepository.findByAccessToken(token).orElseThrow(() -> new CustomException(ExceptionResponseCode.TOKEN_NOT_FOUND));
    }
}
