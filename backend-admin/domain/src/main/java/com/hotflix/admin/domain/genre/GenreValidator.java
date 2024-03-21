package com.hotflix.admin.domain.genre;

import com.hotflix.admin.domain.validation.DomainError;
import com.hotflix.admin.domain.validation.ValidationHandler;
import com.hotflix.admin.domain.validation.Validator;

public class GenreValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 1;

    private final Genre genre;

    protected GenreValidator(final Genre aGenre, final ValidationHandler aHandler) {
        super(aHandler);
        this.genre = aGenre;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.genre.getName();
        if (name == null) {
            this.validationHandler().append(new DomainError("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new DomainError("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new DomainError("'name' must be between 1 and 255 characters"));
        }
    }
}
