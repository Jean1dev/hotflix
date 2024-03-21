package com.hotflix.admin.domain.category;

public record CategoryQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
