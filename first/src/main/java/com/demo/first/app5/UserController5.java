package com.demo.first.app5;

import com.demo.first.app5.User5;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user5")
public class UserController5 {

    private UserService userService = new UserService();

    public UserController5(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User5> createUser(@RequestBody User5 user){
        User5 createdUser = userService.createUser(user);
        return  new ResponseEntity<>(createdUser,HttpStatus.CREATED);
    }



    @PutMapping
    public ResponseEntity<User5> updateUser(@RequestBody User5 user){
        User5 updated = userService.updateUser(user);
        if(updated == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(updated);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        boolean isDeleted = userService.deleteUser(id);
        if(!isDeleted)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.noContent().build();
    }

    // ye method sara users retrieve krne ke liye hai
    @GetMapping
    public List<User5> getUsers(){
        return userService.getAllUsers();
    }

    // retrieve only one user
    @GetMapping("/{userId}")
    public ResponseEntity<User5> getUser(@PathVariable(value = "userId",required = false) int id){
        User5 user = userService.getUserById(id);
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(user);
    }


    // Ek URL mei multiple path variable accept kar skte hai
    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<User5> getUserOrder(
            @PathVariable("userId") int id,
            @PathVariable int orderId
    ){
        User5 user = userService.getUserById(id);
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(user);
    }

// ************************************************

    // RequestParam bhi multiple pass kar skte hai
    // http://localhost:8080/user1/search?name=afifa222&email=afifa222@gmail.com

    @GetMapping("/search")
    public ResponseEntity<List<User5>> searchUsers(
            @RequestParam(required = false,defaultValue = "Aaisha") String name,
            @RequestParam(required = false,defaultValue = "Aaisha119@gmail.com") String email
            ){

        return ResponseEntity.ok(userService.searchUsers(name,email));
    }

    // "name=afifa222&email=afifa222@gmail.com" :- This part is called query parameter.

    // Using all annotation in one method
    @GetMapping("/info/{id}")
    public String getInfo(
            @PathVariable int id,
            @RequestParam String name,
            @RequestHeader("User-Agent") String userAgent){
        return "User Agent: " + userAgent + " : " + id + " : " + name;
    }









}



