package com.hotflix.admin.domain.validation.handler;

import com.hotflix.admin.domain.exceptions.DomainException;
import com.hotflix.admin.domain.validation.DomainError;
import com.hotflix.admin.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class NotificationHandler implements ValidationHandler {

    private final List<DomainError> errors;

    private NotificationHandler(final List<DomainError> errors) {
        this.errors = errors;
    }

    public static NotificationHandler create() {
        return new NotificationHandler(new ArrayList<>());
    }

    public static NotificationHandler create(final Throwable t) {
        return create(new DomainError(t.getMessage()));
    }

    public static NotificationHandler create(final DomainError anError) {
        return new NotificationHandler(new ArrayList<>()).append(anError);
    }

    @Override
    public NotificationHandler append(final DomainError anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public NotificationHandler append(final ValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());
        return this;
    }

    @Override
    public <T> T validate(final Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (final Throwable t) {
            this.errors.add(new DomainError(t.getMessage()));
        }
        return null;
    }

    @Override
    public List<DomainError> getErrors() {
        return this.errors;
    }

}
