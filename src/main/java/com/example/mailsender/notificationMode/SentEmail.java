package com.example.mailsender.notificationMode;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("sent_emails")
record SentEmail(@Id String id,
                 String recipient,
                 String subject,
                 String message,
                 LocalDateTime sentAt) {
}
