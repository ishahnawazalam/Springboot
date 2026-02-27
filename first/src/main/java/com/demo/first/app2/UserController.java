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

// -------------------------------------
/*
Iske liye Notes :: new ArrayList<>(userDb.values());
Tum likhte ho:
   new ArrayList<>(userDb.values());

Assume:
Map<Long, User> userDb = new HashMap<>();

🔍 Step-by-Step Behind the Scenes
1️⃣ userDb.values()
- Ye HashMap ka method hai
- Ye return karta hai: Collection<User>

⚠ Important:
- Ye copy nahi hota, ye map ka live view hota hai.Matlab agar map change hoga to values view bhi reflect karega.

2️⃣ new ArrayList<>(collection)
- Ab yaha ArrayList ka constructor call hota hai:
- Internally ye constructor kuch aisa karta hai:

public ArrayList(Collection<? extends E> c) {
    Object[] a = c.toArray();   // Step A
    elementData = Arrays.copyOf(a, a.length); // Step B
    size = a.length;
}

🔥 Actual Behind-the-Scenes Flow
✅ Step A → c.toArray()
- Collection ke saare elements ek array me convert ho jate hain.
- Example: [User1, User2, User3]

✅ Step B → Copy into internal array
- ArrayList internally ek array use karta hai: Object[] elementData;
- Wo values us array me copy ho jati hain.

✅ Final Result
- Ab: new ArrayList<>(userDb.values());
- Matlab:
- Map ke values ko ek new independent list me copy kar diya.

*/