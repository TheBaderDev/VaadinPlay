package com.application.authentication;

import java.io.Serializable;

import com.application.beatseshDB.User;

/**
 * Simple interface for authentication and authorization checks.
 */
public interface AccessControl extends Serializable {

    String ADMIN_ROLE_NAME = "admin";
    String ADMIN_USERNAME = "admin";

    boolean signIn(String username, String password);

    boolean isUserSignedIn();

    boolean isUserInRole(String role);

    String getPrincipalName();

    void signOut();
    
    boolean signInDj(User user, String partyname);
    
    boolean signInNormal(User user);
}
