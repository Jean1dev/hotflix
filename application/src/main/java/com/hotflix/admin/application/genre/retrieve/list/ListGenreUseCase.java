package com.hotflix.admin.application.genre.retrieve.list;

import com.hotflix.admin.application.UseCase;
import com.hotflix.admin.domain.pagination.Pagination;
import com.hotflix.admin.domain.pagination.SearchQuery;

public abstract class ListGenreUseCase
        extends UseCase<SearchQuery, Pagination<GenreListOutput>> {
}
