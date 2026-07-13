package model;

/**
 * Model: UserSession
 * Singleton that stores the identity of the currently logged-in cashier/admin.
 * Read by every Controller to know "who" is using the app right now.
 */
public class UserSession {
    private static UserSession instance;
    private User currentUser;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public User getCurrentUser() { 
        return currentUser; 
    }
    public void setCurrentUser(User currentUser) { 
        this.currentUser = currentUser; 
    }

    public boolean isLoggedIn() { 
        return currentUser != null; 
    }

    public void logout() { 
        currentUser = null; 
    }
}
