package com.company.e_summit;

public class UserClass {
    public String email;
    public String summit;

    public UserClass() {
    }

    public UserClass(String email, String summit) {
        this.email = email;
        this.summit = summit;
    }

    public String getEmail() {
        return email;
    }

    public String getSummit() {
        return summit;
    }
}
