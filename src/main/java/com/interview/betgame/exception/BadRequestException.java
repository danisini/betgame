package com.interview.betgame.exception;

import java.util.HashMap;
import java.util.Map;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
