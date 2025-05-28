package com.example.aswe.demo.models;

import jakarta.persistence.Entity;

public enum Status {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED,
    BLOCKED,
    ON_HOLD,
    CANCELLED
}
