package org.launchcode.mediareview.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(unique=true)
    private String username;

    @NotNull
    @Email
    @Column(unique=true)
    private String email;

    @NotNull
    @Size(min = 5, message = "Password must be at least five characters")
    private String password;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public List<String> getRoles() {
        ArrayList<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        return roles;
    }

    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User user = (User) obj;
        if (!username.equals(user.username)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
