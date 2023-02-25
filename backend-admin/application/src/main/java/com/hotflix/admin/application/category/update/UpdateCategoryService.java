package com.hotflix.admin.application.category.update;

import com.hotflix.admin.application.UseCase;
import com.hotflix.admin.domain.validation.handler.NotificationHandler;
import io.vavr.control.Either;

public abstract class UpdateCategoryService extends UseCase<UpdateCategoryCommand, Either<NotificationHandler, UpdateCategoryOutput>> {
}
