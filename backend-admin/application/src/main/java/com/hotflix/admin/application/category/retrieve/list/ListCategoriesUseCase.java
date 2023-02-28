package com.hotflix.admin.application.category.retrieve.list;

import com.hotflix.admin.application.UseCase;
import com.hotflix.admin.domain.category.CategoryQuery;
import com.hotflix.admin.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase
        extends UseCase<CategoryQuery, Pagination<CategoryListOutput>> {
}
