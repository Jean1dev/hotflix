package com.hotflix.admin.infra.category;

import com.hotflix.admin.domain.category.Category;
import com.hotflix.admin.domain.category.CategoryGateway;
import com.hotflix.admin.domain.category.CategoryId;
import com.hotflix.admin.domain.category.CategoryQuery;
import com.hotflix.admin.domain.pagination.Pagination;
import com.hotflix.admin.infra.category.persistence.CategoryJpaEntity;
import com.hotflix.admin.infra.category.persistence.CategoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.hotflix.admin.infra.utils.SpecificationUtils.like;

@Component
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(final Category aCategory) {
        return save(aCategory);
    }

    @Override
    public void deleteById(final CategoryId anId) {
        final String anIdValue = anId.getValue();
        if (this.repository.existsById(anIdValue)) {
            this.repository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<Category> findById(final CategoryId anId) {
        return this.repository.findById(anId.getValue())
                .map(CategoryJpaEntity::toAggregate);
    }

    @Override
    public Category update(final Category aCategory) {
        return save(aCategory);
    }

    @Override
    public Pagination<Category> findAll(CategoryQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var specifications = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult =
                this.repository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public List<CategoryId> existsByIds(Iterable<CategoryId> categoryIDs) {
        final var ids = StreamSupport.stream(categoryIDs.spliterator(), false)
                .map(CategoryId::getValue)
                .toList();
        return this.repository.existsByIds(ids).stream()
                .map(CategoryId::from)
                .toList();
    }

    private Category save(final Category aCategory) {
        return this.repository.save(CategoryJpaEntity.from(aCategory)).toAggregate();
    }

    private Specification<CategoryJpaEntity> assembleSpecification(final String str) {
        final Specification<CategoryJpaEntity> nameLike = like("name", str);
        final Specification<CategoryJpaEntity> descriptionLike = like("description", str);
        return nameLike.or(descriptionLike);
    }
}
