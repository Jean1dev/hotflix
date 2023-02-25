package com.hotflix.admin.application.category.update;

import com.hotflix.admin.domain.category.Category;
import com.hotflix.admin.domain.category.CategoryId;

public record UpdateCategoryOutput(
        CategoryId id
) {

    public static UpdateCategoryOutput from(final Category category) {
        return new UpdateCategoryOutput(category.getId());
    }
}
