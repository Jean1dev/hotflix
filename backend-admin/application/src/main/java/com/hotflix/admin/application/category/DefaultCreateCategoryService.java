package com.hotflix.admin.application.category;

import com.hotflix.admin.domain.category.Category;
import com.hotflix.admin.domain.category.CategoryGateway;
import com.hotflix.admin.domain.validation.handler.NotificationHandler;
import io.vavr.control.Either;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateCategoryService extends CreateCategoryService {

    private final CategoryGateway gateway;

    public DefaultCreateCategoryService(CategoryGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Either<NotificationHandler, CreateCategoryOutput> execute(CreateCategoryCommand createCategoryCommand) {
        Category newCategory = Category.newCategory(createCategoryCommand.name(), createCategoryCommand.description(), createCategoryCommand.active());
        final NotificationHandler notification = NotificationHandler.create();
        newCategory.validate(notification);

        gateway.create(newCategory);
        return notification.hasError() ? Left(notification) : create(newCategory);
    }

    private Either<NotificationHandler, CreateCategoryOutput> create(final Category aCategory) {
        return Try(() -> this.gateway.create(aCategory))
                .toEither()
                .bimap(NotificationHandler::create, CreateCategoryOutput::from);
    }
}
