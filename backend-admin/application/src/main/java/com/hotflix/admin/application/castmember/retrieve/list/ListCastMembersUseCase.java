package com.hotflix.admin.application.castmember.retrieve.list;

import com.hotflix.admin.application.UseCase;
import com.hotflix.admin.domain.pagination.Pagination;
import com.hotflix.admin.domain.pagination.SearchQuery;

public sealed abstract class ListCastMembersUseCase
        extends UseCase<SearchQuery, Pagination<CastMemberListOutput>>
        permits DefaultListCastMembersUseCase {
}
