package com.demo.first.app3;

import com.demo.first.app3.User3;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user3")
public class UserController3 {

    private Map<Integer, User3> userDb = new HashMap<>();


//    @PostMapping
//    public ResponseEntity<String> createUser(@RequestBody User3 user){
//        System.out.println(user.getEmail());
//        // tab hee put krega jab key unique hai ya absent hai
//        userDb.putIfAbsent(user.getId(),user);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body("User Created");
//    }

    // or konsa user create hua wo bhi dekh skte hai, change type from string to User object
    @PostMapping
    public ResponseEntity<User3> createUser(@RequestBody User3 user){
        System.out.println(user.getEmail());
        // tab hee put krega jab key unique hai ya absent hai
        userDb.putIfAbsent(user.getId(),user);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(user);
        // or
        return  new ResponseEntity<>(user,HttpStatus.CREATED);
    }


    // String ke jagah User3 bhi kar skte ho
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User3 user){
        if(!userDb.containsKey(user.getId())){
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body("User Not found");
            // or
//            return ResponseEntity.notFound().build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            // body ki jagah build isiliye qk user mila nhi hai.
        }

        userDb.put(user.getId(), user);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Update Successfully");

        // or
        return ResponseEntity.ok("Update Successfully");

    }

    // Now koi bhi id se delete kar rhe hai to delete ho jaa rha hai.So apply check. After hitting on postman, checks to apply hogya but
    // status code abhi 200 show ho rha which is OK or SUCCESS(Ye nhi hona chahiye tha). It means ke status code par kuch bhi control nhi hai.
    // Iska solution hai Response Entity class
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        if(!userDb.containsKey(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        userDb.remove(id);
//        return ResponseEntity.ok("Delete Successfully");
        // delete ho chuka hai but dikhane ke liye kuch bhi content nhi hai : then show 204: noContent
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public List<User3> getUsers(){
        return new ArrayList<>(userDb.values());
    }


}

/*
* HttsStatus enum
- It is an enum that contains all official HTTP status codes like:
- 200 OK
- 201 CREATED
- 400 BAD_REQUEST
- 404 NOT_FOUND
- 500 INTERNAL_SERVER_ERROR
- Ye bas name + number ka combination hota hai.

* Example — HttpStatus values
| Status Name                      | Number | Meaning              |
| -------------------------------- | ------ | -------------------- |
| HttpStatus.OK                    | 200    | Request successful   |
| HttpStatus.CREATED               | 201    | New resource created |
| HttpStatus.BAD_REQUEST           | 400    | Invalid input        |
| HttpStatus.NOT_FOUND             | 404    | Resource not found   |
| HttpStatus.INTERNAL_SERVER_ERROR | 500    | Server failed        |

* Why HttpStatus class exists?
- Numbers yaad nahi rakhne padte → HttpStatus.OK likho instead of 200
- Spring internally map kar deta : enum → number

* Important: HttpStatus is an enum. Spring ne ye already create kiya hua hota hai.

- Ex-1 ::
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("User not found");

*/

