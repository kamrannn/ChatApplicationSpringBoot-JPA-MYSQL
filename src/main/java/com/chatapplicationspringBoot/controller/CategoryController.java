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

@EnableSwagger2
@RestController
@RequestMapping("/category")
public class CategoryController {
    CategoryService categoryService;
    private static final Logger LOG =  LogManager.getLogger(CategoryController.class);
    private static final String token = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * @Author "Kamran"
     * @Description "Authorizing the token"
     * @param token
     * @return
     */
    public boolean Authorization(String token) {
        return CategoryController.token.equals(token);
    }

    /**
     * @Author "Kamran"
     * @Description "if the user is un-authorized"
     * @return
     */
    public ResponseEntity<Object> UnAuthorizeUser() {
        LOG.info("Error: Unauthorized User");
        return new ResponseEntity<>("Kindly login first", HttpStatus.UNAUTHORIZED);
    }

    /**
     * @Author "Kamran"
     * @return all the list of categories
     */
    @GetMapping("/all")
    public ResponseEntity<Object> ListAllCategories(@RequestHeader("Authorization") String token) {
        if(Authorization(token)){
            return categoryService.ListAllCategories();
        }
        else
        {
            return UnAuthorizeUser();
        }
    }

    //This API just add the user

    /**
     * @Author "Kamran"
     * @Description "This API just adds the category in the database"
     * @param categories
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> AddCategory( @RequestBody List<Category> categories) {
        if(Authorization(token)){
            return categoryService.saveCategory(categories);
        }
        else {
            return UnAuthorizeUser();
        }
    }

    /**
     *
     * @param token
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> DeleteUser(@RequestHeader("Authorization") String token, @RequestParam("delete") Long id) {
            if(Authorization(token)){
                return categoryService.DeleteCategory(id);
            }
            else{
                return UnAuthorizeUser();
            }
    }

    /**
     * @Author "Kamran"
     * @Description
     * @param token
     * @param categories
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Object> UpdateCategory(@RequestHeader("Authorization") String token,@RequestBody  List<Category> categories) {
        if (Authorization(token)) {
            try {
                return categoryService.saveCategory(categories);
            } catch (Exception exception) {
                LOG.info("Error: "+ exception.getMessage());
                return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return UnAuthorizeUser() ;
        }
    }
}
