package com.demo.first.app6;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user6")
public class UserController6 {

    private UserService6 userService = new UserService6();

    public UserController6(UserService6 userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User6> createUser(@RequestBody User6 user){
        User6 createdUser = userService.createUser(user);
        return  new ResponseEntity<>(createdUser,HttpStatus.CREATED);
    }



    @PutMapping
    public ResponseEntity<User6> updateUser(@RequestBody User6 user){
        User6 updated = userService.updateUser(user);
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
    public List<User6> getUsers(){
        return userService.getAllUsers();
    }

    // retrieve only one user
    @GetMapping("/{userId}")
    public ResponseEntity<User6> getUser(@PathVariable(value = "userId",required = false) int id){
        User6 user = userService.getUserById(id);
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(user);
    }


    // Ek URL mei multiple path variable accept kar skte hai
    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<User6> getUserOrder(
            @PathVariable("userId") int id,
            @PathVariable int orderId
    ){
        User6 user = userService.getUserById(id);
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(user);
    }

// ************************************************

    // RequestParam bhi multiple pass kar skte hai
    // http://localhost:8080/user1/search?name=afifa222&email=afifa222@gmail.com

    @GetMapping("/search")
    public ResponseEntity<List<User6>> searchUsers(
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

    // EXCEPTION HANDLING METHOD
    // If koi bhi IllegalException aati hai poori ki poori apps mei then ye wala method trigger ho jayega
    // Ye method mapped hai sirf illegalArgument exception se mapped hai. To kon sa method kis exception se mapped hai wo @ExceptionHandler btata hai
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException exception){
//        Map<String,Object> errorResponse = new HashMap<>();
//        errorResponse.put("Message", exception.getMessage());
//        // or bhi cheez add kar skte hai like default
//        errorResponse.put("Timestamp: ", LocalDateTime.now());
//        errorResponse.put("Status", HttpStatus.BAD_REQUEST.value());
//        errorResponse.put("Error", "Bad Request");
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//
//    }

    // EK se jyada exception se mapped: + Centralized kar diye isiliye comments kiye hai
//    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
//    public ResponseEntity<Map<String, Object>> handleBothException(Exception  exception){
//        Map<String,Object> errorResponse = new HashMap<>();
//        errorResponse.put("Message", exception.getMessage());
//        // or bhi cheez add kar skte hai like default
//        errorResponse.put("Timestamp: ", LocalDateTime.now());
//        errorResponse.put("Status", HttpStatus.BAD_REQUEST.value());
//        errorResponse.put("Error", "Bad Request");
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//
//    }

    // Exceptions ke code ko controller mei rakhna is not a good practice. So centralized it in one file




}



