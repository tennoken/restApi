package com.example.example.repositoryTest;

import com.example.example.ExampleApplicationTests;
import com.example.example.model.entity.Category;
import com.example.example.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class CategoryRepositoryTest extends ExampleApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create(){
        String title = "Mac book Pro";
        String type = "Laptop computer";
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        Category category = new Category();

        category.setTitle(title);
        category.setType(type);
        category.setCreatedAt(createdAt);
        category.setCreatedBy(createdBy);

        Category newCategory = categoryRepository.save(category);

        Assertions.assertNotNull(newCategory);
        Assertions.assertEquals(newCategory.getTitle(), title);
        Assertions.assertEquals(newCategory.getType(), type);


    }

    @Test
    public void read(){

        String type = "Laptop computer";

        Optional<Category> category = categoryRepository.findByType(type);

        category.ifPresent(selectCategory ->{
            Assertions.assertEquals(selectCategory.getType(), type);
            System.out.print(selectCategory);
        });
    }
}
