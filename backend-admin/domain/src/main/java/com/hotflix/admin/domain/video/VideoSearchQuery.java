package com.hotflix.admin.domain.video;

import com.hotflix.admin.domain.castmember.CastMemberID;
import com.hotflix.admin.domain.category.CategoryId;
import com.hotflix.admin.domain.genre.GenreID;

import java.util.Set;

public record VideoSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction,
        Set<CastMemberID> castMembers,
        Set<CategoryId> categories,
        Set<GenreID> genres
) {
}
