package com.chatapplicationspringBoot.Controller;

import com.chatapplicationspringBoot.Model.User;
import com.chatapplicationspringBoot.Service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.NoSuchElementException;

@EnableSwagger2
@RestController
@RequestMapping("/users")
@Api(value="User Operations", description="CRUD REST API's for the User")
public class UserController {
    final UserService userService;
    //UserService constructor, used in place of Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    //This is token for checking authorization
    private String key = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

    //Login it takes email and password from frontend then check from database by calling object with email
    @GetMapping("/login")
    public ResponseEntity IsLogin(@RequestParam("email") String email, @RequestParam("password") String password) {
        int check = userService.FindByEmail(email, password);
        switch (check) {
            case 1:
                return new ResponseEntity("You are successfully logged in", HttpStatus.FOUND);
            case 2:
                return new ResponseEntity("Your Password is Wrong", HttpStatus.UNAUTHORIZED);
            default:
                return new ResponseEntity("The account doesn't exists", HttpStatus.UNAUTHORIZED);
        }
    }

    public boolean authorization(String key1) {
        if (key.equals(key1)) return true;
        else return false;
    }

    public ResponseEntity UnAuthorizeUser() {
        return new ResponseEntity("Kindly login first", HttpStatus.UNAUTHORIZED);
    }

    //This API shows all the users
    @GetMapping("")
    public ResponseEntity<Object> list(@RequestHeader("Authorization") String key1) {
        try {
            if (authorization(key1) == true) {
                List<User> userList = userService.listAllUser();
                return new ResponseEntity<>(userList, HttpStatus.OK);
            } else {
                return UnAuthorizeUser();
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
        }
    }

    //This API just add the user
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestHeader("Authorization") String key1, @RequestBody User user) throws Exception {
        if (authorization(key1)) {
            try {
                userService.saveUser(user);
                return new ResponseEntity("User has been successfully added", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity("The email already Exists", HttpStatus.CONFLICT);
            }
        } else {
            return UnAuthorizeUser();
        }
    }

    //This API only show certain object by taking on ID number
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByID(@RequestHeader("Authorization") String key1, @PathVariable Long id) {
        try {
            if (authorization(key1) == true) {
                User user = userService.getUser(id);
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    //This API updates the user by just giving certain ID all values should be update otherwise other fields will be NULL
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestHeader("Authorization") String key1, @RequestBody User user) {
        if (authorization(key1) == true) {
            try {
                userService.updateUser(user);
                return new ResponseEntity<>("User has been successfully Updated",HttpStatus.OK);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<>("User is not Updated",HttpStatus.NOT_FOUND);
            }
        } else {
            return UnAuthorizeUser() ;
        }
    }

    //This API deletes the user by using Path variable
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("Authorization") String key1) {

        if (authorization(key1) == true) {
            userService.deleteUser(id);
        }
    }

    //This API deletes the user by using Request Parameter
    @DeleteMapping("/delete")
    public void delete(@RequestHeader("Authorization") String key1, @RequestParam("delete") Long id) {
        if (authorization(key1) == true) {
            userService.deleteUser(id);
        }
    }
}
