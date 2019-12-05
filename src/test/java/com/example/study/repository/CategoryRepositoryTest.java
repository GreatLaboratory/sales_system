package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Category;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;


public class CategoryRepositoryTest extends StudyApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create() {

        String type = "EARPHONE";
        String title = "이어폰";
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        Category category = new Category();
        category.setType(type);
        category.setTitle(title);
        category.setCreatedAt(createdAt);
        category.setCreatedBy(createdBy);

        Category newCategory = categoryRepository.save(category);

        Assert.assertNotNull(newCategory);
        Assert.assertEquals(newCategory.getType(), type);
        Assert.assertEquals(newCategory.getTitle(), title);
    }

    @Test
    public void read() {

        String type = "EARPHONE";

        Optional<Category> optionalCategory = categoryRepository.findByType(type);

        optionalCategory.ifPresent(selectedCategory -> {

            Assert.assertEquals(selectedCategory.getType(), type);

            System.out.println(selectedCategory.getId());
            System.out.println(selectedCategory.getTitle());
            System.out.println(selectedCategory.getType());
        });

    }
}
