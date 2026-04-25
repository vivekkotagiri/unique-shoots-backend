package com.uniqueshoots.backend;

import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;
    private String clientPhone;
    private String eventType;
    private String eventDate;
    private String eventAddress;
    private String selectedPackage;

    // Constructors
    public Booking() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getClientPhone() { return clientPhone; }
    public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public String getEventAddress() { return eventAddress; }
    public void setEventAddress(String eventAddress) { this.eventAddress = eventAddress; }

    public String getSelectedPackage() { return selectedPackage; }
    public void setSelectedPackage(String selectedPackage) { this.selectedPackage = selectedPackage; }
}