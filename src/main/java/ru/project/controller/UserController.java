package ru.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.project.model.dto.user.SignInDto;
import ru.project.model.dto.user.SignUpDto;
import ru.project.model.entity.OAuthAccessToken;
import ru.project.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<OAuthAccessToken> signUp(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(userService.signUp(signUpDto));
    }

    @PostMapping("/signIn")
    public ResponseEntity<OAuthAccessToken> signIn(@RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(userService.signIn(signInDto));
    }

}
