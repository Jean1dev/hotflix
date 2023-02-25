package com.hotflix.admin.application.category;

import com.hotflix.admin.domain.category.Category;
import com.hotflix.admin.domain.category.CategoryId;

public record CreateCategoryOutput(
        CategoryId id
) {
    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId());
    }
}
