package com.demo.first.app2;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private Map<Integer,User> userDb = new HashMap<>();

    // Yha par user aana hai i.e., user ka object milna hai,to ye hamlog pass krenge request body mei using @RequestBody annotation
    // qk data hamesha request body mei jata hai.Request body se data fetch krna and json mei convert krke pass krna abb SpringBoot dekhega
    // And SpringBoot ko inta btana hai ki ,ye jo Request ki jo body hai usme User ka object aane wala hai
    // We pass data using postman tool. Choose POST request.
    /*
       {
        "id":10,
        "name":"Aaisha",
        "email":"aaisha119@gmail.com"
        }
        - pass in body and in json format on postman(Json se object mei convert ho jata hai).Then send
    */
    @PostMapping
    public String createUser(@RequestBody User user){
        System.out.println(user.getEmail());
        // tab hee put krega jab key unique hai ya absent hai
        userDb.putIfAbsent(user.getId(),user);
        return "User Created";
    }


    // 1: Aaisha, aaisha119@gmail.com
    // 1: Sadaf, aaisha119@gmail.com --> check id 1 exist krti hai ya nhi ? If yes, Update name
    @PutMapping
    public String updateUser(@RequestBody User user){
        if(userDb.containsKey(user.getId())){
            userDb.put(user.getId(), user);
        }
        System.out.println(user.getName());
        return "Update Successfully";
    }

    // Dynamic Url : /user/1, /user/2, /user/3
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id){
        userDb.remove(id);
        return "Deleted Successfully";
    }


    // .values() ek Collection deta hai, List nahi. Isliye usko ArrayList me convert kar rahe ho.
    /*
        Collection: Ek group of objects, jo aapas me ek jagah store hote hain.
        Ex: [10, 20, 30]
            ["Ali", "Zara", "Shaaz"]
            [User1, User2, User3]

    */
    // But Spring Boot (Jackson library) JSON banate waqt List ya array jaisa structure expect karta hai — jisse Postman/UI me clean JSON array ban sake.
    @GetMapping
    public List<User> getUsers(){
        return new ArrayList<>(userDb.values());
    }

}

/*
- POST ke liye ::
Spring automatically karega:
   - JSON read
   - fields match karega
   - User class ka object banayega:
User user = new User();
user.setId(1);
user.setName("Shaaz");

- Tumne new User() kahin nahi likha, par Spring ne khud bana diya.
- @RequestBody lagaya → Spring bolega: "Is JSON ko Java object me convert karna hai"
*/