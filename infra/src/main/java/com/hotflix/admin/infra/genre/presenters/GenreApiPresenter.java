package com.hotflix.admin.infra.genre.presenters;

import com.hotflix.admin.application.genre.retrieve.get.GenreOutput;
import com.hotflix.admin.application.genre.retrieve.list.GenreListOutput;
import com.hotflix.admin.infra.genre.models.GenreListResponse;
import com.hotflix.admin.infra.genre.models.GenreResponse;

public interface GenreApiPresenter {

    static GenreResponse present(final GenreOutput output) {
        return new GenreResponse(
                output.id(),
                output.name(),
                output.categories(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static GenreListResponse present(final GenreListOutput output) {
        return new GenreListResponse(
                output.id(),
                output.name(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
