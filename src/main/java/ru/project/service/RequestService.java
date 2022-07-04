package ru.project.service;

import ru.project.model.entity.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RequestService {

    Request saveRequest(HttpServletRequest request);

    List<Request> findLastForHourRequestsForIp(String ip);

}
