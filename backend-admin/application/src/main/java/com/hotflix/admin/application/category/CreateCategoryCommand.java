package com.hotflix.admin.application.category;

public record CreateCategoryCommand(
        String name,
        String description,
        boolean active
) {
}
