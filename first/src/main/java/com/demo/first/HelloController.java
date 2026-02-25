package com.demo.first;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class HelloController {

    @GetMapping("/believeInAllah")
    public String sayHello(){
        return "Bhai bas Samajh aa jaye Spring Boot";
    }

//    @GetMapping("/user")
//    @RequestMapping(value = "/user", method = {RequestMethod.GET,RequestMethod.POST})
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUser(){
//        User user = new User(1,"John","John@gmail.com");
        return new User(1,"John","John@gmail.com");
    }
}

// Flow is like that :
//    Browser(hit request on endpoint) --> /user --> Created Pojo --> Spring boot(converts into JSON) --> JSON Output


