package com.leszek.szymaszek.demo.user;

class ValidationException extends RuntimeException {
    ValidationException(String message) {
        super(message);
    }
}
