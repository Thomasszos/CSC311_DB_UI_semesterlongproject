package service;

import java.util.HashSet;
import java.util.Set;
import java.util.prefs.Preferences;

public class UserSession {

    private static volatile UserSession instance;

    private String userName;

    private String password;
    private String privileges;

    private static final Preferences userPreferences = Preferences.userRoot().node("UserSession");

    private UserSession(String userName, String password, String privileges) {
        this.userName = userName;
        this.password = password;
        this.privileges = privileges;

        userPreferences.put("USERNAME",userName);
        userPreferences.put("PASSWORD",password);
        userPreferences.put("PRIVILEGES",privileges);
    }



    public static UserSession getInstance(String userName,String password, String privileges) {
        if(instance == null) {
            synchronized (UserSession.class) {
                if(instance == null) {
                    instance = new UserSession(userName, password, privileges);
                }
            }
        }
        return instance;
    }

    public static UserSession getInstance(String userName,String password) {
        if(instance == null) {
            synchronized (UserSession.class) {
                if(instance == null) {
                    instance = new UserSession(userName, password, "NONE");
                }
            }
        }
        return instance;
    }
    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPrivileges() {
        return this.privileges;
    }

    public void cleanUserSession() {
        this.userName = "";// or null
        this.password = "";
        this.privileges = "";// or null
    }

    public static UserSession loadFromPreferences() {
        if (instance == null) {
            synchronized (UserSession.class) {
                if (instance == null) {
                    String savedUsername = userPreferences.get("USERNAME", null);
                    String savedPassword = userPreferences.get("PASSWORD", null);
                    String savedPrivileges = userPreferences.get("PRIVILEGES", "NONE");

                    if (savedUsername != null && savedPassword != null) {
                        instance = new UserSession(savedUsername, savedPassword, savedPrivileges);
                    }
                }
            }
        }
        return instance;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + this.userName + '\'' +
                ", privileges=" + this.privileges +
                '}';
    }
}
