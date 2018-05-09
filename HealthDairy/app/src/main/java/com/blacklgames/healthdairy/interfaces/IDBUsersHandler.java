package com.blacklgames.healthdairy.interfaces;
import com.blacklgames.healthdairy.db.dataobjects.User;

import java.util.List;

public interface IDBUsersHandler extends IDBHandler
{
    public void addUser(User user);
    public User getUser(int id);
    public List<User> getAllUsers();
    public int updateUser(User user);
    public void deleteUser(User user);
    public void deleteAll();
    public int getUsersCount();
}
