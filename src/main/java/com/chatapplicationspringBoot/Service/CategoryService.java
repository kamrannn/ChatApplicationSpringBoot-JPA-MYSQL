package com.chatapplicationspringBoot.Service;

import com.chatapplicationspringBoot.Model.Category;
import com.chatapplicationspringBoot.Repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //Get all chats from the Database
    public List<Category> ListAllCategory(){
        return categoryRepository.findAll();
    }

    //Save chat into database by getting values from controller
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }
}
