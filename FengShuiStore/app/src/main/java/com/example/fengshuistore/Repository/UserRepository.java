package com.example.fengshuistore.Repository;

import android.content.Context;

import com.example.fengshuistore.Model.User;
import com.example.fengshuistore.SqliteOpenHelper.SqliteOpenHelper;

import java.util.ArrayList;

public class UserRepository {
    private SqliteOpenHelper dbHelper;
    private static ArrayList<User> userList = new ArrayList<>();

    public UserRepository(Context context) {
        dbHelper = new SqliteOpenHelper(context);
        if (userList.isEmpty()) {
            userList = dbHelper.getAllUsers(); // Load from DB if the list is empty
        }
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public boolean checkExistedUser(User u) {
        for (User user : userList) {
            if (user.getName().equals(u.getName()) || user.getUsername().equals(u.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean addUser(User u) {
        if (!checkExistedUser(u)) {
            dbHelper.insertUser(u.getName(), u.getUsername(), u.getPassword());
            userList.add(u); // Add to the static list
            return true;
        }else{
            return false;
        }
    }

    public void removeUser(User u) {
        dbHelper.deleteUser(u.getUsername());
        userList.remove(u); // Remove from the static list
    }

    public User getUser(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
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
            if (user.getUsername().equals(u.getUsername())) {
                user.setName(u.getName());
                user.setPassword(u.getPassword());
                dbHelper.updateUser(u.getUsername(), u.getName(), u.getPassword());
                return true;
            }
        }
        return false;
    }
}