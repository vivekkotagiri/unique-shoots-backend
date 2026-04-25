package com.uniqueshoots.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*") // Allows your HTML frontend to communicate with this backend
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/submit")
    public String submitBooking(@RequestBody Booking booking) {
        
        // 1. Save the client details to your Oracle Database
        bookingRepository.save(booking);

        // 2. Fire the instant email notification
        sendEmailNotification(booking);

        return "Booking Saved and Notification Sent!";
    }

    private void sendEmailNotification(Booking booking) {
        // Look for a line that looks like this:
SimpleMailMessage message = new SimpleMailMessage();
message.setFrom("kotagiri.vivek321@gmail.com"); // This is you sending it

// CHANGE THIS LINE BELOW:
message.setTo("kotagiri.vivek321@gmail.com"); // Put your real email here!
        
        message.setSubject("🚨 NEW BOOKING ALERT: UniqueShoots");
        message.setText("Awesome news! A new client just booked a shoot from your Instagram link:\n\n" +
                "Client Name: " + booking.getClientName() + "\n" +
                "Phone (WhatsApp): " + booking.getClientPhone() + "\n" +
                "Occasion: " + booking.getEventType() + "\n" +
                "Date: " + booking.getEventDate() + "\n" +
                "Location: " + booking.getEventAddress() + "\n" +
                "Package Chosen: " + booking.getSelectedPackage() + "\n\n" +
                "Message them on WhatsApp right now to secure the advance payment!");
                
        mailSender.send(message);
    }
}