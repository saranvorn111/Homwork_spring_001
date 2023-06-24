package com.example.rest2.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandle implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        response.getWriter().write("Can not Access");
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_ATOM_XML_VALUE);
        response.setStatus(403);
        Map<String, Object> body = new HashMap<>();
        body.put("status",false);
        body.put("Message",accessDeniedException.getMessage());
        body.put("Code", HttpStatus.FORBIDDEN.value());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),body);

    }
}
