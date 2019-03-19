package org.launchcode.mediareview.user;

public class AccountExistsException extends Exception {
    private boolean usernameExists;
    private boolean emailExists;

    public AccountExistsException(boolean usernameExists, boolean emailExists) {
        super();
        this.usernameExists = usernameExists;
        this.emailExists = emailExists;
    }

    public boolean doesEmailExist() {
        return emailExists;
    }

    public boolean doesUsernameExist() {
        return usernameExists;
    }

    public String usernameExistsMessage() {
        return "Username already exists";
    }

    public String emailExistsMessage() {
        return "Email already exists";
    }
}
