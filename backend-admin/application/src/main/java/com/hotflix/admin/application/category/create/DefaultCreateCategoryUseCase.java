package com.hotflix.admin.application.category.create;

import com.hotflix.admin.domain.category.Category;
import com.hotflix.admin.domain.category.CategoryGateway;
import com.hotflix.admin.domain.validation.handler.NotificationHandler;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<NotificationHandler, CreateCategoryOutput> execute(final CreateCategoryCommand aCommand) {
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var notification = NotificationHandler.create();

        final var aCategory = Category.newCategory(aName, aDescription, isActive);
        aCategory.validate(notification);

        return notification.hasError() ? Left(notification) : create(aCategory);
    }

    private Either<NotificationHandler, CreateCategoryOutput> create(final Category aCategory) {
        return Try(() -> this.categoryGateway.create(aCategory))
                .toEither()
                .bimap(NotificationHandler::create, CreateCategoryOutput::from);
    }
}
