package com.chatapplicationspringBoot.service;

import com.chatapplicationspringBoot.model.entity.Category;
import com.chatapplicationspringBoot.model.entity.User;
import com.chatapplicationspringBoot.repository.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The Category service.
 * @Author "Kamran"
 * @CreatedData "12-10-2021"
 */
@Service
public class CategoryService {
    private static final Logger LOG =  LogManager.getLogger(CategoryService.class);
    /**
     * The Category repository.
     * @Author "Kamran"
     * @CreatedData "12-10-2021"
     */
    CategoryRepository categoryRepository;

    /**
     * Instantiates a new Category service.
     * @Author "Kamran"
     * @CreatedData "12-10-2021"
     * @param categoryRepository the category repository
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Get all chats from the Database.
     * @Author "Kamran"
     * @CreatedData "12-10-2021"
     * @return response entity
     */
    public ResponseEntity<Object> ListAllCategories(){
        try{
            List<Category> categoryList =categoryRepository.findAllByStatus(true);
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
     * Save categories into database by getting values from controller.
     *
     * @param categories the categories
     * @return response entity
     * @Author "Kamran"
     * @CreatedData "12-10-2021"
     */
    public ResponseEntity<Object> AddCategory(List<Category> categories) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = formatter.format(new Date());
        int listSize = categories.size();

        for(int i=0;i<listSize;i++){
            if(categories.get(i).getName().trim().isEmpty()){
                return new ResponseEntity<>("You are entering the null values", HttpStatus.BAD_REQUEST);
            }
        }
        try {
            for (Category newCategory : categories) {
                Category category = categoryRepository.findByName(newCategory.getName());
                if(null!=category){
                    category.setStatus(true);
                    updateCategory(category);
                }else {
                    newCategory.setCreateDate(date);
                    newCategory.setStatus(true);
                    categoryRepository.save(newCategory);
                }
            }
            return new ResponseEntity<>("Categories has been successfully added", HttpStatus.OK);
        } catch (Exception e) {
            LOG.info("Error: "+ e.getMessage());
            return new ResponseEntity<>("One of the Category that you are adding already Exists", HttpStatus.CONFLICT);
        }
    }

    /**
     * this method is deleting the category by taking category id.
     *
     * @param id the id
     * @return response entity
     * @Author "Kamran"
     */
    public ResponseEntity<Object> DeleteCategory(Long id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            if(category.isPresent()){
                category.get().setStatus(false);
                categoryRepository.save(category.get());
            }
//            categoryRepository.deleteById(id);
            return new ResponseEntity<>("Category is successfully deleted", HttpStatus.OK);
        }catch (Exception exception){
            LOG.info("Error: "+ exception.getMessage());
            return new ResponseEntity<>("This category doesn't exist in the database", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updating the categories by category ID.
     * @Author "Kamran"
     * @param category the category
     * @return response entity
     */
    public ResponseEntity<Object> updateCategory(Category category) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = formatter.format(new Date());
            category.setUpdateDate(date);
            category.setStatus(true);
            categoryRepository.save(category);
            return new ResponseEntity<>("Category has been successfully Updated", HttpStatus.OK);
        } catch (Exception e) {
            LOG.info("Exception: " + e.getMessage());
            return new ResponseEntity<>("Category is not Updated", HttpStatus.BAD_REQUEST);
        }
    }
}
