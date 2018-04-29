package com.blacklgames.healthdairy.db;

import java.util.List;

public interface IDatabaseHandler {
    public void addUser(User user);
    public User getUser(int id);
    public List<User> getAllUsers();
    public int getUsersCount();
    public int updateUser(User user);
    public void deleteUser(User user);
    public void deleteAll();
}
