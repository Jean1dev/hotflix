package com.hotflix.admin.infra.config.usecases;

import com.hotflix.admin.application.category.create.CreateCategoryUseCase;
import com.hotflix.admin.application.category.create.DefaultCreateCategoryUseCase;
import com.hotflix.admin.application.category.delete.DefaultDeleteCategoryUseCase;
import com.hotflix.admin.application.category.delete.DeleteCategoryUseCase;
import com.hotflix.admin.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.hotflix.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.hotflix.admin.application.category.retrieve.list.DefaultListCategoriesUseCase;
import com.hotflix.admin.application.category.retrieve.list.ListCategoriesUseCase;
import com.hotflix.admin.application.category.update.DefaultUpdateCategoryService;
import com.hotflix.admin.application.category.update.UpdateCategoryService;
import com.hotflix.admin.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryService updateCategoryUseCase() {
        return new DefaultUpdateCategoryService(categoryGateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoriesUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }
}
