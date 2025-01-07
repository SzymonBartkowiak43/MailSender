package com.example.mailsender.notificationMode;

import org.springframework.data.mongodb.repository.MongoRepository;

interface SentEmailRepository extends MongoRepository<SentEmail, String> {
}