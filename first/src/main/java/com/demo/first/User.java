package com.demo.first;

// POJO --> Plain Old Java Objects
public class User {
    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Error dega code qk jab spring boot java objects ko json mei convert krta hai then uske(objects ke) field ka access hona chahiye
    // Since private field hai to access nhi hoga .Isiliye getters lagenge


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
