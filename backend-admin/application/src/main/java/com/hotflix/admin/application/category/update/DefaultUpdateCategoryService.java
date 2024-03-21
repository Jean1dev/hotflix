package com.hotflix.admin.application.category.update;

import com.hotflix.admin.domain.category.Category;
import com.hotflix.admin.domain.category.CategoryGateway;
import com.hotflix.admin.domain.category.CategoryId;
import com.hotflix.admin.domain.exceptions.DomainException;
import com.hotflix.admin.domain.exceptions.NotFoundException;
import com.hotflix.admin.domain.validation.handler.NotificationHandler;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdateCategoryService extends UpdateCategoryService {

    private final CategoryGateway gateway;

    public DefaultUpdateCategoryService(CategoryGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Either<NotificationHandler, UpdateCategoryOutput> execute(final UpdateCategoryCommand aCommand) {
        final var anId = CategoryId.from(aCommand.id());
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.active();

        final var aCategory = gateway.findById(anId)
                .orElseThrow(notFound(anId));

        final var notification = NotificationHandler.create();
        aCategory
                .update(aName, aDescription, isActive)
                .validate(notification);

        return notification.hasError() ? Left(notification) : update(aCategory);
    }

    private Either<NotificationHandler, UpdateCategoryOutput> update(final Category aCategory) {
        return Try(() -> this.gateway.update(aCategory))
                .toEither()
                .bimap(NotificationHandler::create, UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notFound(final CategoryId anId) {
        return () -> NotFoundException.with(Category.class, anId);
    }
}
