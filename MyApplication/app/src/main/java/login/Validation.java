package login;

/**
 * Created by paulszh on 2/12/16.
 */
public class Validation {

    /*
       check valid email address
     */
    static public boolean isValidEmail(String email) {
        return email != null && email.trim().length() > 0 && email.indexOf("@") > 0;
    }

    /*
        check if the password is valid. Also can be applied later to set minimal password length.
    */
    static public boolean isValidPassword(String password) {
        return password != null && password.trim().length() > 0;
    }
}