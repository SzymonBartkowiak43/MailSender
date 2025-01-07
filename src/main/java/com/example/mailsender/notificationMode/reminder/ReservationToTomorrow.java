package com.example.mailsender.notificationMode.reminder;

import java.time.LocalDateTime;

public record ReservationToTomorrow(String to, String reservationName, String salonName, String street, String number, String city, LocalDateTime reservationDataTime) {
}
