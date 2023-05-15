package com.example.rest2.api.notification.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateNotificationDto(@JsonProperty("included_segments")
                                    String[] includedSegments,
                                    ContentDto contents) {
}
