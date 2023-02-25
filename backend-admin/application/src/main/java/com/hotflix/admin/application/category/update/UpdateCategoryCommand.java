package com.hotflix.admin.application.category.update;

public record UpdateCategoryCommand(
        String id,
        String name,
        String description,
        boolean active
) {
}
