package com.hotflix.admin.domain.category;

import com.hotflix.admin.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category category);

    void deleteById(CategoryId id);

    Optional<Category> findById(CategoryId id);

    Category update(Category category);

    Pagination<Category> findAll(CategoryQuery query);
}
