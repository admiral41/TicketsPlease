package com.example.ticket_please.Entity;

import lombok.Data;

import java.util.Map;

@Data
public class ReserveSeatConfiguration {

    private SeatReservaion seatReservation;
    private Map<String, Boolean> map;

    private Long id;
    private String string;
    private boolean active;

    public boolean isActive() {
        return active;
    }
}