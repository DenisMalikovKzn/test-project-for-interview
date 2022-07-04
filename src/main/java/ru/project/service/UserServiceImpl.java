package ru.project.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.project.dao.TokenRepository;
import ru.project.dao.UserRepository;
import ru.project.model.CustomException;
import ru.project.model.ExceptionResponseCode;
import ru.project.model.dto.user.SignInDto;
import ru.project.model.dto.user.SignUpDto;
import ru.project.model.entity.OAuthAccessToken;
import ru.project.model.entity.User;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    public OAuthAccessToken signUp(SignUpDto signUpDto) {
        User user = saveUser(signUpDto);
        return authorize(user);
    }

    public OAuthAccessToken signIn(SignInDto signInDto) {
        User user = new User(signInDto.getUsername(), signInDto.getPassword());
        validateUser(user);
        user = generateUser(signInDto.getUsername(), signInDto.getPassword());
        return authorize(user);
    }

    private User saveUser(SignUpDto signUpDto) {
        checkNewUser(signUpDto.getUsername());
        User user = generateUser(signUpDto.getUsername(), signUpDto.getPassword());
        user = userRepository.save(user);
        return user;
    }

    private User generateUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }

    private void checkNewUser(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new CustomException(ExceptionResponseCode.USER_ALREADY_EXISTS);
        });
    }

    private void validateUser(User user) {
        Optional<User> existsUser = userRepository.findByUsername(user.getUsername());
        if (!existsUser.isPresent()) {
            throw new CustomException(ExceptionResponseCode.USER_DOES_NOT_EXISTS);
        }
        if (!passwordEncoder.matches(user.getPassword(), existsUser.get().getPassword())) {
            throw new CustomException(ExceptionResponseCode.WRONG_PASSWORD);
        }
    }

    private OAuthAccessToken authorize(User user) {
        Optional<OAuthAccessToken> token = tokenRepository.findByUsername(user.getUsername());
        OAuthAccessToken accessToken;
        if (token.isPresent()) {
            accessToken = token.get();
        } else {
            accessToken = new OAuthAccessToken(user.getUsername());
            tokenRepository.save(accessToken);
        }
        return accessToken;
    }

}
