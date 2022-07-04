package ru.project.service;

import org.springframework.stereotype.Service;
import ru.project.dao.RequestRepository;
import ru.project.model.entity.Request;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final TokenService tokenService;

    public RequestServiceImpl(RequestRepository requestRepository, TokenService tokenService) {
        this.requestRepository = requestRepository;
        this.tokenService = tokenService;
    }

    @Override
    public Request saveRequest(HttpServletRequest request) {
        return requestRepository.save(buildRequest(request));
    }

    @Override
    public List<Request> findLastForHourRequestsForIp(String ip) {
        return requestRepository.findAllByIp(ip)
                .stream()
                .filter(r -> r.getTime().isAfter(LocalDateTime.now().minusHours(1)))
                .collect(Collectors.toList());
    }

    private Request buildRequest(HttpServletRequest request) {
        Request result = new Request();
        result.setIp(request.getRemoteHost());
        result.setTime(LocalDateTime.now());
        result.setUri(request.getRequestURI());
        if (tokenService.isPresent(request.getHeader("Authorization")))
            result.setUsername(tokenService.findUserByToken(request.getHeader("Authorization")).getUsername());
        else
            result.setUsername("anonymous");
        return result;
    }
}
