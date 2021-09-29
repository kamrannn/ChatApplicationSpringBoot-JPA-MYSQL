package com.chatapplicationspringBoot.Controller;

import com.chatapplicationspringBoot.Model.User;
import com.chatapplicationspringBoot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.http.HTTPException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private String key = "40dc498b-e837-4fa9-8e53-c1d51e01af15";

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


    @GetMapping("")
    public ResponseEntity<Object> list(@RequestHeader("Authorization") String key1) {
        try {
            if (authorization(key1) == true) {
                List<User> userList= userService.listAllUser();
                return new ResponseEntity<>(userList,HttpStatus.OK);
            } else {
                return UnAuthorizeUser();
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Data not found",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestHeader("Authorization") String key1, @RequestBody User user)  {
        if (authorization(key1)) {
            try{
                userService.saveUser(user);
                return new ResponseEntity("User has been successfully added",HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity("The email already Exists", HttpStatus.CONFLICT);
            }
        }
        else{
            return UnAuthorizeUser();
        }
    }

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

/*    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) {
        try {
            User existUser = userService.getUser(id);
            user.setId(id);
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String key1, @RequestBody User user) {
        if (authorization(key1) == true) {
            try {
                userService.saveUser(user);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @DeleteMapping("/delete/{id}")
    public void delete( @PathVariable Long id,@RequestHeader("Authorization") String key1) {

        if (authorization(key1) == true) {
            userService.deleteUser(id);
        }
    }

    @DeleteMapping("/delete")
    public void delete(@RequestHeader("Authorization") String key1,@RequestParam ("delete") Long id) {
        if (authorization(key1) == true) {
            userService.deleteUser(id);
        }
    }
}
