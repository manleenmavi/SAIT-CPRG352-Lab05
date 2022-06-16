package service;

import models.User;

public class AccountService {
    private final String[] acceptedUsernames = {"abe", "barb"};
    private final String acceptedPassword = "password";
    
    public User login (String username, String password) {
        
        boolean isValidUsername = false;
        
        // Verifying valid user
        for(String currValidUser : acceptedUsernames) {
            if(username.equals(currValidUser)) {
                isValidUsername = true;
                break;
            }
        }
        
        //Verifying valid password with the valid user
        if (isValidUsername && password.equals(acceptedPassword)) {
            return new User(username, null);
        } else {
            return null;
        }
    }
}
