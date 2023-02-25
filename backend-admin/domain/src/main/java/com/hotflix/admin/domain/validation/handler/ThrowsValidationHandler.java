package com.hotflix.admin.domain.validation.handler;

import com.hotflix.admin.domain.exceptions.DomainException;
import com.hotflix.admin.domain.validation.DomainError;
import com.hotflix.admin.domain.validation.ValidationHandler;

import java.util.Collections;
import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(DomainError error) {
        throw DomainException.with(List.of(error));
    }

    @Override
    public ValidationHandler append(ValidationHandler another) {
        throw DomainException.with(another.getErrors());
    }

    @Override
    public ValidationHandler validate(Validation validation) {
        try {
            validation.validate();
        } catch (final Exception e) {
            throw DomainException.with(Collections.singletonList(new DomainError(e.getMessage())));
        }

        return this;
    }

    @Override
    public boolean hasError() {
        return ValidationHandler.super.hasError();
    }

    @Override
    public List<DomainError> getErrors() {
        return List.of();
    }
}
