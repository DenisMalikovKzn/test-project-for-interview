package ru.project.filter;

import ru.project.model.CustomException;
import ru.project.model.ExceptionResponseCode;
import ru.project.service.RequestService;
import ru.project.service.TokenService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class AuthorizationFilter implements Filter {

    private final RequestService requestService;

    private final TokenService tokenService;

    private final String[] PUBLIC_URLS = {
            "/user/signUp", "/user/signIn", "/error"
    };

    private final String[] PROTECTED_URLS = {
            "/user/signUp"
    };

    public AuthorizationFilter(RequestService requestService, TokenService tokenService) {
        this.requestService = requestService;
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        validateUriForUser(httpServletRequest, httpServletResponse);
        requestService.saveRequest(httpServletRequest);
        if (Arrays.stream(PUBLIC_URLS).noneMatch(s -> httpServletRequest.getRequestURI().equals(s))) {
            validateUserAuth(httpServletRequest, httpServletResponse);
        }
        chain.doFilter(request, response);
    }

    private void validateUserAuth(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            throw new CustomException(ExceptionResponseCode.AUTHORIZATION_HEADER_EMPTY);
        }
        if (authHeader.split(" ").length != 2) {
            throw new CustomException(ExceptionResponseCode.AUTHORIZATION_HEADER_WRONG_FORMAT);
        }
        tokenService.getTokenFromRequest(request);
    }

    private void validateUriForUser(HttpServletRequest request, HttpServletResponse response) {
        String currentUri = request.getRequestURI();
        if (Arrays.asList(PROTECTED_URLS).contains(currentUri)) {
            String currentIp = request.getRemoteHost();
            int countRequestsByIp = requestService.findLastForHourRequestsForIp(currentIp).size();
            if (countRequestsByIp > 10) {
                throw new CustomException(ExceptionResponseCode.TOO_MANY_REQUESTS);
            }
        }

    }
}
