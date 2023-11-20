package com.hotflix.admin.application.genre.create;

import com.hotflix.admin.domain.category.CategoryGateway;
import com.hotflix.admin.domain.category.CategoryId;
import com.hotflix.admin.domain.exceptions.NotificationException;
import com.hotflix.admin.domain.genre.Genre;
import com.hotflix.admin.domain.genre.GenreGateway;
import com.hotflix.admin.domain.validation.DomainError;
import com.hotflix.admin.domain.validation.ValidationHandler;
import com.hotflix.admin.domain.validation.handler.NotificationHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultCreateGenreUseCase extends CreateGenreUseCase {

    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;

    public DefaultCreateGenreUseCase(
            final CategoryGateway categoryGateway,
            final GenreGateway genreGateway
    ) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public CreateGenreOutput execute(final CreateGenreCommand aCommand) {
        final var aName = aCommand.name();
        final var isActive = aCommand.isActive();
        final var categories = toCategoryID(aCommand.categories());

        final var notification = NotificationHandler.create();
        notification.append(validateCategories(categories));

        final var aGenre = notification.validate(() -> Genre.newGenre(aName, isActive));

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Genre", notification);
        }

        aGenre.addCategories(categories);

        return CreateGenreOutput.from(this.genreGateway.create(aGenre));
    }

    private ValidationHandler validateCategories(final List<CategoryId> ids) {
        final var notification = NotificationHandler.create();
        if (ids == null || ids.isEmpty()) {
            return notification;
        }

        final var retrievedIds = categoryGateway.existsByIds(ids);

        if (ids.size() != retrievedIds.size()) {
            final var missingIds = new ArrayList<>(ids);
            missingIds.removeAll(retrievedIds);

            final var missingIdsMessage = missingIds.stream()
                    .map(CategoryId::getValue)
                    .collect(Collectors.joining(", "));

            notification.append(new DomainError("Some categories could not be found: %s".formatted(missingIdsMessage)));
        }

        return notification;
    }

    private List<CategoryId> toCategoryID(final List<String> categories) {
        return categories.stream()
                .map(CategoryId::from)
                .toList();
    }
}
