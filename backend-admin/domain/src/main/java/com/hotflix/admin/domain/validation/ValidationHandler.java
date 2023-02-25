package com.hotflix.admin.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(DomainError error);

    ValidationHandler append(ValidationHandler another);

    ValidationHandler validate(Validation validation);

    default boolean hasError() {
        return getErrors() != null && !(getErrors().size() == 0);
    }

    List<DomainError> getErrors();

    interface Validation {
        void validate();
    }
}
