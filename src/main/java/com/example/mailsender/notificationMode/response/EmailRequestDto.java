package com.example.mailsender.notificationMode.response;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record EmailRequestDto(String to, String offerName, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time, String company) {
}
