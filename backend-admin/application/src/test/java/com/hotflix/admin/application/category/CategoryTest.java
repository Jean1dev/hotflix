package com.hotflix.admin.application.category;

import com.hotflix.admin.domain.category.Category;
import com.hotflix.admin.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class CategoryTest {

    @DisplayName("deve testar a criacao da categoria")
    @Test
    public void deveTestar() {
        final var name = "filmes";
        final var description = "a categoria";
        final var active = true;

        final var command = new CreateCategoryCommand(name, description, active);

        final var gateway = Mockito.mock(CategoryGateway.class);

        Mockito.when(gateway.create(ArgumentMatchers.any(Category.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var servie = new DefaultCreateCategoryService(gateway);

        final var output = servie.execute(command);

        Assertions.assertNotNull(output);
    }
}
