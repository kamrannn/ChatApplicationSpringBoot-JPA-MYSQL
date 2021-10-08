package com.chatapplicationspringBoot.service;

import com.chatapplicationspringBoot.model.entity.Category;
import com.chatapplicationspringBoot.repository.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    private static final Logger LOG =  LogManager.getLogger(CategoryService.class);
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * @Author "Kamran"
     * @Description "Get all chats from the Database"
     * @return
     */
    public ResponseEntity<Object> ListAllCategories(){
        try{
            List<Category> categoryList =categoryRepository.findAll();
            if(categoryList.isEmpty()){
                return new ResponseEntity<>("There are no categories in the database", HttpStatus.NOT_FOUND);
            }
            else{
                return new ResponseEntity<>(categoryList, HttpStatus.OK);
            }
        }catch (Exception e){
            LOG.info("Error: "+ e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @Author "Kamran"
     * @Description "Save categories into database by getting values from controller"
     * @param categories
     * @return
     */
    public ResponseEntity<Object> saveCategory(List<Category> categories) {
        int listSize = categories.size();
        for(int i=0;i<listSize;i++){
            if(categories.get(i).getName().trim().isEmpty()){
                return new ResponseEntity<>("You are entering the null values", HttpStatus.BAD_REQUEST);
            }
        }
        try {
            for (Category newCategory : categories) {
                categoryRepository.save(newCategory);
            }
            return new ResponseEntity<>("Categories has been successfully added", HttpStatus.OK);
        } catch (Exception e) {
            LOG.info("Error: "+ e.getMessage());
            return new ResponseEntity<>("One of the Category that you are adding already Exists", HttpStatus.CONFLICT);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public ResponseEntity<Object> DeleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>("Category is successfully deleted", HttpStatus.OK);
        }catch (Exception exception){
            LOG.info("Error: "+ exception.getMessage());
            return new ResponseEntity<>("This category doesn't exist in the database", HttpStatus.NOT_FOUND);
        }
    }
}
