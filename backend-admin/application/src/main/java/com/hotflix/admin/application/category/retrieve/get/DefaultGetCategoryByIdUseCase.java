package com.hotflix.admin.application.category.retrieve.get;

import com.hotflix.admin.domain.category.Category;
import com.hotflix.admin.domain.category.CategoryGateway;
import com.hotflix.admin.domain.category.CategoryId;
import com.hotflix.admin.domain.exceptions.NotFoundException;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryOutput execute(final String anIn) {
        final var anCategoryID = CategoryId.from(anIn);

        return this.categoryGateway.findById(anCategoryID)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(anCategoryID));
    }

    private Supplier<NotFoundException> notFound(final CategoryId anId) {
        return () -> NotFoundException.with(Category.class, anId);
    }
}
