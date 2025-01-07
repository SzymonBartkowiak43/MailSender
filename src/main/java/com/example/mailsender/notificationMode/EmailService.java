package com.example.mailsender.notificationMode;


import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@AllArgsConstructor
class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final SentEmailRepository sentEmailRepository;

    public Boolean sendHtmlEmail(String to, String offerName, LocalDateTime time, String company) {
        String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Map<String, Object> templateParams = Map.of(
                "subject", "Service Confirmation",
                "offerName", offerName,
                "time", formattedTime,
                "company", company
        );

        String body = createHtmlBody("emailTemplate", templateParams);

        saveSentEmail(to, "Reservation Confirm", body);

        return sendEmail(to, "Reservation Confirm", body);
    }



    public Boolean sendHtmlEmailWithPassword(String to, String offerName, String password, LocalDateTime time, String company) {
        String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Map<String, Object> templateParams = Map.of(
                "subject", "Service Confirmation",
                "offerName", offerName,
                "password", password,
                "time", formattedTime,
                "company", company
        );

        String body = createHtmlBody("emailTemplateWithPassword", templateParams);

        saveSentEmail(to, "Reservation Confirm with password", body);

        return sendEmail(to, offerName, body);
    }

    public Boolean sendHtmlEmailToRemindAboutReservation(String to, String offerName, String salonName, String city,
                                                         String street, String number, LocalDateTime time) {
        String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Map<String, Object> templateParams = Map.of(
                "subject", "Reservation reminder",
                "offerName", offerName,
                "salonName", salonName,
                "city", city,
                "street", street,
                "number", number,
                "time", formattedTime
        );

        String body = createHtmlBody("emailTemplateReservationReminder", templateParams);

        saveSentEmail(to, "Reservation reminder", body);

        return sendEmail(to, "Reservation reminder", body);
    }


    private Boolean sendEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String createHtmlBody(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        variables.forEach(context::setVariable);
        return templateEngine.process(templateName, context);
    }

    private void saveSentEmail(String to, String subject, String body) {
        SentEmail sentEmail = new SentEmail(null,to, subject, body, LocalDateTime.now());
        sentEmailRepository.save(sentEmail);
    }
}