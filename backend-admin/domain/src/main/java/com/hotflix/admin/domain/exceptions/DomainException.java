package com.hotflix.admin.domain.exceptions;

import com.hotflix.admin.domain.validation.DomainError;

import java.util.List;

public class DomainException extends NoStackTraceException {

    private final List<DomainError> errors;


    protected DomainException(final String message, List<DomainError> errors) {
        super(message);
        this.errors = errors;
    }

    public static DomainException with(final List<DomainError> errors) {
        return new DomainException("", errors);
    }
}
