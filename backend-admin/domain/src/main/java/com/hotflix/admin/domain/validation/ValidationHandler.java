package com.hotflix.admin.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(DomainError anError);

    ValidationHandler append(ValidationHandler anHandler);

    <T> T validate(Validation<T> aValidation);

    List<DomainError> getErrors();

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default DomainError firstError() {
        if (getErrors() != null && !getErrors().isEmpty()) {
            return getErrors().get(0);
        } else {
            return null;
        }
    }

    interface Validation<T> {
        T validate();
    }
}
