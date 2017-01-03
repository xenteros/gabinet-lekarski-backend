package pl.com.gurgul.dto;

/**
 * Created by agurgul on 03.01.2017.
 */
public class PasswordTO {

    private String oldPassword;
    private String newPassword;
    private String pesel;

    public PasswordTO() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
}
