package com.demo.first.app4;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user1")
public class UserController1 {

    private Map<Integer, User1> userDb = new HashMap<>();



    @PostMapping
    public ResponseEntity<User1> createUser(@RequestBody User1 user){
        System.out.println(user.getEmail());
        userDb.putIfAbsent(user.getId(),user);

        return  new ResponseEntity<>(user,HttpStatus.CREATED);
    }



    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User1 user){
        if(!userDb.containsKey(user.getId())){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        userDb.put(user.getId(), user);

        return ResponseEntity.ok("Update Successfully");

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        if(!userDb.containsKey(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        userDb.remove(id);

        return ResponseEntity.noContent().build();

    }

    // ye method sara users retrieve krne ke liye hai
    @GetMapping
    public List<User1> getUsers(){
        return new ArrayList<>(userDb.values());
    }

    // Dynamic URL
    // creating an endpoint to retrieve only single user
//    @GetMapping("/{id}")
//    public ResponseEntity<User1> getUser(@PathVariable int id ){
//        if(!userDb.containsKey(id))
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//
//        return ResponseEntity.ok(userDb.get(id));
//    }

    // Suppose getMapping ke saath jo variable bhej rhe hai and jo whi same variable method mei bhej rhe hai to agar variable ka naam change ho to
    // error aayega . so usko fix krne ke liye :: niche ka code
//    @GetMapping("/{userId}")
//    public ResponseEntity<User1> getUser(@PathVariable("userId") int id ){
//        if(!userDb.containsKey(id))
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//
//        return ResponseEntity.ok(userDb.get(id));
//    }


    // Ek URL mei multiple path variable accept kar skte hai
    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<User1> getUserOrder(
            @PathVariable("userId") int id,
            @PathVariable int orderId
    ){
        System.out.println("ORDER ID: " + orderId);
        if(!userDb.containsKey(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(userDb.get(id));
    }

    // Aise scenarios bhi aayege jab aap, har path variable ka value app url mei pass nhi krna chahoge, qk variable kbhi available ho bhi sakta hai and kbhi nhi bhi.
    // To isko optional mark kar skte hai, so if value pass nhi bhi kre URL mei, phir bhi work krega.
    @GetMapping("/{userId}")
    public ResponseEntity<User1> getUser(
            @PathVariable(value = "userId",required = false) int id ){
        if(!userDb.containsKey(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(userDb.get(id));
    }

//    @GetMapping({"/users", "/user/{id}"}):
//    Ek method ko 2 url endpoint se map kar rhe hai ek hee annotation ke saath.
//    Ye endpoint nhi to dusra wala

// ************************************************

// RequestParam
    // /search?name=john
//    @GetMapping("/search")
//    public ResponseEntity<List<User1>> searchUsers(@RequestParam(required = false,defaultValue = "Aaisha") String name){
//        System.out.println(name);
        // filhal filter ke wajaye, sare users ko return kar dete hai
        // return ResponseEntity.ok(new ArrayList<>(userDb.values()));

        // Now filter krte hai
//        List<User1> users = userDb.values().stream()
//                .filter(u-> u.getName().equalsIgnoreCase(name))
//                .toList();
//        return ResponseEntity.ok(users);
//    }

    // Optional wala concept pagination mei use hota hai, i.e., koi application ka next page ya filter wala concept mei, i.e., url mei suppose page-2 pass nhi ho rhi thi phir bhi phla wala page run kr rha tha



    // RequestParam bhi multiple pass kar skte hai
    // http://localhost:8080/user1/search?name=afifa222&email=afifa222@gmail.com

    @GetMapping("/search")
    public ResponseEntity<List<User1>> searchUsers(
            @RequestParam(required = false,defaultValue = "Aaisha") String name,
            @RequestParam(required = false,defaultValue = "Aaisha119@gmail.com") String email
            ){
        System.out.println(name);
        System.out.println(email);
        List<User1> users = userDb.values().stream()
                .filter(u-> u.getName().equalsIgnoreCase(name))
                .filter(u-> u.getEmail().equalsIgnoreCase(email))
                .toList();
        return ResponseEntity.ok(users);
    }

    // "name=afifa222&email=afifa222@gmail.com" :- This part is called query parameter.


    // @RequestHeader annotation
    // Request Header mei "User-Agent" name se value aani hai and userAgent variable mei assign honi hai.
//    @GetMapping("/info")
//    public String getInfo(@RequestHeader("User-Agent") String userAgent){
//        return "User Agent: " + userAgent;
//    }
    // key: User-Agent, value: jo bhi string pass kro
    // jwt tokens wagaira, headers mei hee bheje jaate hai


    // Using all annotation in one method
    @GetMapping("/info/{id}")
    public String getInfo(
            @PathVariable int id,
            @RequestParam String name,
            @RequestHeader("User-Agent") String userAgent){
        return "User Agent: " + userAgent + " : " + id + " : " + name;
    }









}



