package com.hotflix.admin.domain.category;

import com.hotflix.admin.domain.AggregateRoot;
import com.hotflix.admin.domain.validation.ValidationHandler;
import com.hotflix.admin.domain.validation.handler.ThrowsValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class Category extends AggregateRoot<CategoryId> {
    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final CategoryId id,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt) {
        super(id);
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "'updatedAt' should not be null");
        this.deletedAt = deletedAt;
    }

    public static Category with(
            final CategoryId from,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Category(
                from,
                name,
                description,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public Category update(final String name, final String description, final boolean active) {
        this.name = name;
        this.description = description;

        if (active) {
            activate();
        } else {
            desactivate();
        }

        this.updatedAt = Instant.now();
        return this;
    }

    private void desactivate() {
        this.deletedAt = Instant.now();
        this.active = false;
    }

    private void activate() {
        this.active = true;
    }

    public static Category newCategory(String expectedName, String expectedDescription, boolean expectedIsActive) {
        return with(
                CategoryId.unique(),
                expectedName,
                expectedDescription,
                expectedIsActive,
                Instant.now(),
                Instant.now(),
                Instant.now()
        );
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public void validate(ValidationHandler handler) {
        if (Objects.isNull(handler))
            handler = new ThrowsValidationHandler();

        new CategoryValidator(this, handler).validate();
    }
}