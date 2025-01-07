package com.example.mailsender.notificationMode;


import com.example.mailsender.notificationMode.reminder.ReservationToTomorrow;
import com.example.mailsender.notificationMode.response.NotificationFacadeResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class NotificationFacade {
    private final EmailService emailService;


    public NotificationFacadeResponse sendAnEmailWhenClientHasAccount(String to, String offerName, LocalDateTime time,String company) {
        Boolean isSuccess = emailService.sendHtmlEmail(to, offerName, time, company);
        return new NotificationFacadeResponse(isSuccess);
    }

    public NotificationFacadeResponse sendAnEmailWhenClientDoNotHasAccount(String to, String offerName, String password,LocalDateTime time, String company) {
        Boolean isSuccess = emailService.sendHtmlEmailWithPassword(to, offerName, password,time,company);
        return new NotificationFacadeResponse(isSuccess);
    }

    public List<NotificationFacadeResponse> sendRemind(List<ReservationToTomorrow>  allReservations) {
        return allReservations.stream()
                .map(reservation -> remindUserToReservation(
                        reservation.to(),
                        reservation.reservationName(),
                        reservation.salonName(),
                        reservation.street(),
                        reservation.number(),
                        reservation.city(),
                        reservation.reservationDataTime()
                ))
                .toList();
    }

    private NotificationFacadeResponse remindUserToReservation(String to, String offerName, String salonName, String street, String number, String city, LocalDateTime time) {
        Boolean isSuccess = emailService.sendHtmlEmailToRemindAboutReservation(to,offerName, salonName, city,
                street, number,time);
        return new NotificationFacadeResponse(isSuccess);
    }


}
