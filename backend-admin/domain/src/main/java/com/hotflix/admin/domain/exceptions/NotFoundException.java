package com.hotflix.admin.domain.exceptions;

import com.hotflix.admin.domain.AggregateRoot;
import com.hotflix.admin.domain.Identifier;
import com.hotflix.admin.domain.validation.DomainError;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {

    protected NotFoundException(final String aMessage, final List<DomainError> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Identifier id
    ) {
        final var anError = "%s with ID %s was not found".formatted(
                anAggregate.getSimpleName(),
                id.getValue()
        );
        return new NotFoundException(anError, Collections.emptyList());
    }

    public static NotFoundException with(final DomainError error) {
        return new NotFoundException(error.getMessage(), List.of(error));
    }
}
