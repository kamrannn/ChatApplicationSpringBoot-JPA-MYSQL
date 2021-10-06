package com.chatapplicationspringBoot.Controller;

import com.chatapplicationspringBoot.Model.Category;
import com.chatapplicationspringBoot.Service.CategoryService;
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
    private static final Logger LOG =  LogManager.getLogger(ChatController.class);

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * @Author "Kamran"
     * @return all the list of categories
     */
    @GetMapping("/all")
    public ResponseEntity<Object> list() {

            try{
                List<Category> categoryList= categoryService.ListAllCategory();
                return new ResponseEntity<>(categoryList, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>("There is no data in chat table ",HttpStatus.NOT_FOUND);
            }
    }

    //This API just add the user

    /**
     * @Author "Kamran"
     * @Description "This API just adds the category in the database"
     * @param category
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> add( @RequestBody Category category) {
            try {
                categoryService.saveCategory(category);
                return new ResponseEntity<>("Category has been successfully added", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Category already Exists", HttpStatus.CONFLICT);
            }
    }
}
