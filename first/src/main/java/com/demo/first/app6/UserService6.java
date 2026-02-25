package com.demo.first.app6;

import com.demo.first.app6.Exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UserService6 {

    private Map<Integer, User6> userDb = new HashMap<>();

    public User6 createUser(User6 user) {
        logger.info("Creating User.... INFO");
        logger.debug("Creating User.... DEBUG");
        logger.trace("Creating User.... TRACE");
        logger.warn("Creating User.... WARN");
        logger.error("Creating User.... ERROR");
        System.out.println(user.getEmail());
        userDb.putIfAbsent(user.getId(), user);
        return user;
    }

    public User6 updateUser(User6 user) {
        if(!userDb.containsKey(user.getId())){
//            throw new IllegalArgumentException("User with ID "+ user.getId() + " does not exist.");
//            return null;

            // custom exception
            logger.error("Error when finding user with id {} ",user.getId());
            throw new UserNotFoundException("User with ID "+ user.getId() + " does not exist.");

        }
        userDb.put(user.getId(), user);
        return user;
    }

    public boolean deleteUser(int id) {
        if(!userDb.containsKey(id))
            throw new UserNotFoundException("User with ID "+ id + " does not exist.");


        userDb.remove(id);
        return true;
    }

    public List<User6> getAllUsers() {
        if(userDb.isEmpty())
            throw new NullPointerException("No users found in the database");
        return new ArrayList<>(userDb.values());
    }

    public User6 getUserById(int id) {
        return userDb.get(id);
    }

    public List<User6> searchUsers(String name, String email) {
        return userDb.values().stream()
                .filter(u-> u.getName().equalsIgnoreCase(name))
                .filter(u-> u.getEmail().equalsIgnoreCase(email))
                .toList();
    }

    // ---------------------------------------
    // taking logger instance
    Logger logger = LoggerFactory.getLogger(UserService6.class);
    // Jo class mei likh rhe uss class ka hee instace lena chahiye i.e., service class mei likh rhe to issi ka pass krenge("UserService.class")
}
