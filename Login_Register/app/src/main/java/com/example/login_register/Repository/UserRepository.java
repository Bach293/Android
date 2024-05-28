package com.example.login_register.Repository;

import android.content.Context;

import com.example.login_register.model.User;
import com.example.login_register.sqliteOpenHelper.UserDatabaseHelper;

import java.util.ArrayList;

public class UserRepository {
    private UserDatabaseHelper dbHelper;
    private static ArrayList<User> userList = new ArrayList<>();

    public UserRepository(Context context) {
        dbHelper = new UserDatabaseHelper(context);
        if (userList.isEmpty()) {
            userList = dbHelper.getAllUsers(); // Load from DB if the list is empty
        }
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public boolean checkExistedUser(User u) {
        for (User user : userList) {
            if (user.getEmail().equals(u.getEmail()) && user.getPassword().equals(u.getPassword())) {
                return true;
            }
        }
        return false;
    }
    
    public void addUser(User u) {
        if (!checkExistedUser(u)) {
            dbHelper.insertUser(u.getEmail(), u.getPassword());
            userList.add(u); // Add to the static list
        }
    }

    public void removeUser(User u) {
        dbHelper.deleteUser(u.getEmail());
        userList.remove(u); // Remove from the static list
    }

    public User getUser(String email) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getAllUsers() {
        return userList;
    }

    public int getUserCount() {
        return userList.size();
    }

    public boolean updateUser(User u) {
        for (User user : userList) {
            if (user.getEmail().equals(u.getEmail())) {
                user.setPassword(u.getPassword());
                dbHelper.updateUser(u.getEmail(), u.getPassword());
                return true;
            }
        }
        return false;
    }
}