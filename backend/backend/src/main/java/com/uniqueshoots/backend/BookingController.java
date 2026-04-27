package com.uniqueshoots.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
// The @CrossOrigin annotation is vital to allow your website to talk to Render
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/submit")
    public ResponseEntity<String> submitBooking(@RequestBody Booking booking) {
        try {
            // 1. Save the client details to your Database first
            // This ensures you don't lose the lead even if the email fails
            bookingRepository.save(booking);

            // 2. Attempt to send the email notification
            try {
                sendEmailNotification(booking);
            } catch (Exception emailEx) {
                // Log the email error but don't stop the process
                System.err.println("Email notification failed: " + emailEx.getMessage());
            }

            return ResponseEntity.ok("Booking Saved Successfully");
            
        } catch (Exception e) {
            // If the database save fails, return an error to the frontend
            return ResponseEntity.status(500).body("Server Error: " + e.getMessage());
        }
    }

    private void sendEmailNotification(Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        // This must match the email in your application.properties
        message.setFrom("kotagiri.vivek321@gmail.com"); 
        
        // This is where you want to receive the notification
        message.setTo("kotagiri.vivek321@gmail.com"); 
        
        message.setSubject("🚨 NEW BOOKING ALERT: UniqueShoots");
        
        String emailBody = String.format(
            "Awesome news! A new client just booked a shoot:\n\n" +
            "Client Name: %s\n" +
            "Phone (WhatsApp): %s\n" +
            "Occasion: %s\n" +
            "Date: %s\n" +
            "Location: %s\n" +
            "Package Chosen: %s\n\n" +
            "Action: Message them on WhatsApp right now to secure the advance payment!",
            booking.getClientName(),
            booking.getClientPhone(),
            booking.getEventType(),
            booking.getEventDate(),
            booking.getEventAddress(),
            booking.getSelectedPackage()
        );

        message.setText(emailBody);
        mailSender.send(message);
    }
}