package org.fmemetaj.service;

import org.fmemetaj.dao.UserDAO;
import org.fmemetaj.entity.User;

import java.util.List;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public void createUser(String name, String email) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
            return;
        }
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Error: Email cannot be empty.");
            return;
        }
        User user = new User(name.trim(), email.trim());
        userDAO.saveUser(user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(Long id) {
        if (id == null || id <= 0) {
            System.out.println("Error: Invalid ID.");
            return null;
        }
        return userDAO.getUser(id);
    }

    public void updateUser(Long id, String newName, String newEmail) {
        if (id == null || id <= 0) {
            System.out.println("Error: Invalid ID.");
            return;
        }

        User existingUser = userDAO.getUser(id);
        if (existingUser == null) {
            System.out.println("Error: User with ID " + id + " not found.");
            return;
        }

        if (newName != null && !newName.trim().isEmpty()) {
            existingUser.setName(newName.trim());
        }

        if (newEmail != null && !newEmail.trim().isEmpty()) {
            existingUser.setEmail(newEmail.trim());
        }

        userDAO.updateUser(existingUser);
    }

    public void deleteUser(Long id) {
        if (id == null || id <= 0) {
            System.out.println("Error: Invalid ID.");
            return;
        }
        userDAO.deleteUser(id);
    }
}
