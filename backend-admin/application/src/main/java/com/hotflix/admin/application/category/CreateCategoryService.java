package com.hotflix.admin.application.category;

import com.hotflix.admin.application.UseCase;
import com.hotflix.admin.domain.validation.handler.NotificationHandler;
import io.vavr.control.Either;

public abstract class CreateCategoryService extends UseCase<CreateCategoryCommand, Either<NotificationHandler, CreateCategoryOutput>> {
}
