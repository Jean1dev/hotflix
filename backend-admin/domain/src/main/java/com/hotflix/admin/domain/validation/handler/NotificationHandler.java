package com.hotflix.admin.domain.validation.handler;

import com.hotflix.admin.domain.exceptions.DomainException;
import com.hotflix.admin.domain.validation.DomainError;
import com.hotflix.admin.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class NotificationHandler implements ValidationHandler {

    private final List<DomainError> errors;

    private NotificationHandler(List<DomainError> errors) {
        this.errors = errors;
    }

    public static NotificationHandler create(final DomainError error) {
        return new NotificationHandler(new ArrayList<>()).append(error);
    }


    public static NotificationHandler create() {
        return new NotificationHandler(new ArrayList<>());
    }


    public static NotificationHandler create(Throwable throwable) {
        return new NotificationHandler(new ArrayList<>()).append(new DomainError(throwable.getMessage()));
    }

    @Override
    public NotificationHandler append(DomainError error) {
        this.errors.add(error);
        return this;
    }

    @Override
    public NotificationHandler append(ValidationHandler another) {
        this.errors.addAll(another.getErrors());
        return this;
    }

    @Override
    public NotificationHandler validate(Validation validation) {
        try {
            validation.validate();
        } catch (final DomainException domainException) {
            this.errors.add(new DomainError(domainException.getMessage()));
        } catch (final Throwable throwable) {
            this.errors.add(new DomainError(throwable.getMessage()));
        }

        return this;
    }

    @Override
    public boolean hasError() {
        return ValidationHandler.super.hasError();
    }

    @Override
    public List<DomainError> getErrors() {
        return errors;
    }

}
