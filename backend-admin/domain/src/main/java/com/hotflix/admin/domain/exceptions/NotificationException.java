package com.hotflix.admin.domain.exceptions;

import com.hotflix.admin.domain.validation.handler.NotificationHandler;

public class NotificationException extends DomainException {

    public NotificationException(final String aMessage, final NotificationHandler notification) {
        super(aMessage, notification.getErrors());
    }
}
