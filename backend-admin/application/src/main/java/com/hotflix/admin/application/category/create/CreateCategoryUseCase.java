package com.hotflix.admin.application.category.create;

import com.hotflix.admin.application.UseCase;
import com.hotflix.admin.domain.validation.handler.NotificationHandler;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase
        extends UseCase<CreateCategoryCommand, Either<NotificationHandler, CreateCategoryOutput>> {
}
