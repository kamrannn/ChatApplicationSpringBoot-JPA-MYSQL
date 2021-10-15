package com.chatapplicationspringBoot.controller;

import com.chatapplicationspringBoot.model.entity.Category;
import com.chatapplicationspringBoot.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * The type Category controller.
 */
@EnableSwagger2
@RestController
@RequestMapping("/category")
public class CategoryController {
    /**
     * The Category service.
     */
    CategoryService categoryService;
    private static final Logger LOG =  LogManager.getLogger(CategoryController.class);
    private static final String token = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

    /**
     * Instantiates a new Category controller.
     *
     * @param categoryService the category service
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Authorization boolean.
     *
     * @param token the token
     * @return boolean
     * @Author "Kamran"
     * @Description "Authorizing the token"
     */
    public boolean Authorization(String token) {
        return CategoryController.token.equals(token);
    }

    /**
     * Un authorize user response entity.
     *
     * @return response entity
     * @Author "Kamran"
     * @Description "if the user is un-authorized"
     */
    public ResponseEntity<Object> UnAuthorizeUser() {
        LOG.info("Error: Unauthorized User");
        return new ResponseEntity<>("Kindly do the authorization first", HttpStatus.UNAUTHORIZED);
    }

    /**
     * List all categories response entity.
     *
     * @param token the token
     * @return all the list of categories
     * @Author "Kamran"
     */
    @GetMapping("/list")
    public ResponseEntity<Object> ListAllCategories(@RequestHeader("Authorization") String token) {
        if(Authorization(token)){
            return categoryService.ListAllCategories();
        }
        else
        {
            return UnAuthorizeUser();
        }
    }

    /**
     * Add category response entity.
     *
     * @param categories the categories
     * @return response entity
     * @Author "Kamran"
     * @Description "This API just adds the category in the database"
     */
    @PostMapping("/add")
    public ResponseEntity<Object> AddCategory( @RequestBody List<Category> categories) {
        if(Authorization(token)){
            return categoryService.AddCategory(categories);
        }
        else {
            return UnAuthorizeUser();
        }
    }

    /**
     * Delete category response entity.
     * @param token the token
     * @param id    the id
     * @return response entity
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> DeleteCategory(@RequestHeader("Authorization") String token, @RequestParam("delete") Long id) {
            if(Authorization(token)){
                return categoryService.DeleteCategory(id);
            }
            else{
                return UnAuthorizeUser();
            }
    }

    /**
     * Update category response entity.
     * @param token    the token
     * @param category the category
     * @return response entity
     * @Author "Kamran"
     * @Description
     */
    @PutMapping("/update")
    public ResponseEntity<Object> UpdateCategory(@RequestHeader("Authorization") String token,@RequestBody Category category) {
        if (Authorization(token)) {
            try {
                return categoryService.updateCategory(category);
            } catch (Exception exception) {
                LOG.info("Error: "+ exception.getMessage());
                return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return UnAuthorizeUser() ;
        }
    }
}
