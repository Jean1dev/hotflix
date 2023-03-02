package com.hotflix.admin.application.category.update;

import com.hotflix.admin.domain.category.Category;
import com.hotflix.admin.domain.category.CategoryId;

public record UpdateCategoryOutput(
        String id
) {

    public static UpdateCategoryOutput from(final String anId) {
        return new UpdateCategoryOutput(anId);
    }

    public static UpdateCategoryOutput from(final Category aCategory) {
        return new UpdateCategoryOutput(aCategory.getId().getValue());
    }
}
