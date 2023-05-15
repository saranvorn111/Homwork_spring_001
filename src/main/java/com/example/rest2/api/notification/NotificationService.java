package com.example.rest2.api.notification;

import com.example.rest2.api.notification.web.CreateNotificationDto;
import com.example.rest2.api.notification.web.NotificationDto;

public interface NotificationService {
    boolean pushNotification(CreateNotificationDto notificationDto);
}
