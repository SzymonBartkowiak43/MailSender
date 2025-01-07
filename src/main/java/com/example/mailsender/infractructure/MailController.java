package com.example.mailsender.infractructure;

import com.example.mailsender.notificationMode.reminder.ReservationToTomorrow;
import com.example.mailsender.notificationMode.response.EmailRequestDto;
import com.example.mailsender.notificationMode.NotificationFacade;
import com.example.mailsender.notificationMode.response.EmailRequestWithPasswordDto;
import com.example.mailsender.notificationMode.response.NotificationFacadeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final NotificationFacade notificationFacade;

    public MailController(NotificationFacade notificationFacade) {
        this.notificationFacade = notificationFacade;
    }

    @PostMapping("/send")
    public ResponseEntity<NotificationFacadeResponse> sendEmail(@RequestBody EmailRequestDto emailRequest) {

        NotificationFacadeResponse response = notificationFacade.sendAnEmailWhenClientHasAccount(
                emailRequest.to(),
                emailRequest.offerName(),
                emailRequest.time(),
                emailRequest.company()
        );

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/send-with-password")
    public ResponseEntity<NotificationFacadeResponse> sendEmail(@RequestBody EmailRequestWithPasswordDto emailRequest) {

        NotificationFacadeResponse response = notificationFacade.sendAnEmailWhenClientDoNotHasAccount(
                emailRequest.to(),
                emailRequest.offerName(),
                emailRequest.password(),
                emailRequest.time(),
                emailRequest.company()
        );

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/send-reminders")
    public ResponseEntity<List<NotificationFacadeResponse>> sendReminders(
            @RequestBody List<ReservationToTomorrow> allReservations) {

        List<NotificationFacadeResponse> responses = notificationFacade.sendRemind(allReservations);

        boolean allSuccess = responses.stream().allMatch(NotificationFacadeResponse::isSuccess);

        if (allSuccess) {
            return ResponseEntity.ok(responses);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responses);
        }
    }


}
