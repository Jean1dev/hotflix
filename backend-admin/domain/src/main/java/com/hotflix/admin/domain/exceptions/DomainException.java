package com.hotflix.admin.domain.exceptions;

import com.hotflix.admin.domain.validation.DomainError;

import java.util.List;

public class DomainException extends NoStackTraceException {

    protected final List<DomainError> errors;

    protected DomainException(final String aMessage, final List<DomainError> anErrors) {
        super(aMessage);
        this.errors = anErrors;
    }

    public static DomainException with(final DomainError anErrors) {
        return new DomainException(anErrors.message(), List.of(anErrors));
    }

    public static DomainException with(final List<DomainError> anErrors) {
        return new DomainException("", anErrors);
    }

    public List<DomainError> getErrors() {
        return errors;
    }
}
