package com.hotflix.admin.application.category.delete;

import com.hotflix.admin.domain.category.CategoryGateway;
import com.hotflix.admin.domain.category.CategoryId;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public void execute(final String anIn) {
        this.categoryGateway.deleteById(CategoryId.from(anIn));
    }
}
