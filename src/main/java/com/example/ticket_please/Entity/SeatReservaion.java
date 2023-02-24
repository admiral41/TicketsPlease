package com.example.ticket_please.Entity;

import lombok.Data;

@Data
public class SeatReservaion {

    private String seat;
    private boolean active;

    public boolean isActive() {
        return active;
    }
}