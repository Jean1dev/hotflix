package com.hotflix.admin.domain.category;

import com.hotflix.admin.domain.validation.DomainError;
import com.hotflix.admin.domain.validation.ValidationHandler;
import com.hotflix.admin.domain.validation.Validator;

import java.util.Objects;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(final Category category, ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        if (Objects.isNull(category.getName()))
            validationHandler().append(new DomainError("Nome cannot be null"));

        if (category.getName().isBlank())
            validationHandler().append(new DomainError("Nome cannot be empty"));
    }
}
