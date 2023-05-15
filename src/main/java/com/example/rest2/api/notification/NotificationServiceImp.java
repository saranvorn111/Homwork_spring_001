package com.example.rest2.api.notification;

import com.example.rest2.api.notification.web.CreateNotificationDto;
import com.example.rest2.api.notification.web.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotificationServiceImp implements NotificationService{
    @Value("${onesignal.rest-api-key}")
    private String restApiKey;
    @Value("${onsignal.app-id}")
    private String appId;
    private RestTemplate restTemplate;
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean pushNotification(CreateNotificationDto notificationDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("accept","application/json");
        httpHeaders.set("Authorization","Basic " +restApiKey);
        httpHeaders.set("content-type","application/json");

        NotificationDto body = NotificationDto.builder()
                .appId(appId)
                .includedSegments(notificationDto.includedSegments())
                .contents(notificationDto.contents())
                .build();


        HttpEntity<NotificationDto> requestBody= new HttpEntity<>(body,httpHeaders);

        ResponseEntity<?> response = restTemplate.postForEntity(
                "https://onesignal.com/api/v1/notifications"
                ,requestBody,
                Map.class);


        return response.getStatusCode()== HttpStatus.OK;
    }
}
