package com.hotflix.admin.domain.validation;

public class DomainError {

    private final String message;

    public DomainError(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
