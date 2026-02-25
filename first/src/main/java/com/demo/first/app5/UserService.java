package com.demo.first.app5;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private Map<Integer, User5> userDb = new HashMap<>();

    public User5 createUser(User5 user) {
        System.out.println(user.getEmail());
        userDb.putIfAbsent(user.getId(), user);
        return user;
    }

    public User5 updateUser(User5 user) {
        if(!userDb.containsKey(user.getId())){
            return null;
        }
        userDb.put(user.getId(), user);
        return user;
    }

    public boolean deleteUser(int id) {
        if(!userDb.containsKey(id))
            return false;

        userDb.remove(id);
        return true;
    }

    public List<User5> getAllUsers() {
        return new ArrayList<>(userDb.values());
    }

    public User5 getUserById(int id) {
        return userDb.get(id);
    }

    public List<User5> searchUsers(String name, String email) {
        return userDb.values().stream()
                .filter(u-> u.getName().equalsIgnoreCase(name))
                .filter(u-> u.getEmail().equalsIgnoreCase(email))
                .toList();
    }
}
