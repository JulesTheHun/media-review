package org.launchcode.mediareview.user;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {

    @NotNull
    @Size(min = 5, max = 20)
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 5, message = "Password must be at least five characters")
    private String password;

    @NotNull(message = "Passwords do not match")
    private String verify;

    public UserDto() { }

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

    public void setVerify(String verify) {
        this.verify = verify;
        this.checkPasswordMatch();
    }

    public String getVerify() { return verify; }

    private void checkPasswordMatch() {
        if (password != verify) {
            verify = null;
        }
    }
}
