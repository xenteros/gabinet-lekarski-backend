package pl.com.gurgul.dto;

import pl.com.gurgul.model.User;
import pl.com.gurgul.utils.UserRoles;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by agurgul on 10.12.2016.
 */
public class UserTO {

    @NotNull
    private String email;

    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phoneNumber;

    private String gender;

    private String pesel;

    private boolean isDoctor;

    public boolean isDoctor() {
        return isDoctor;
    }

    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.isDoctor = user.getUserRole().equals(UserRoles.ROLE_DOCTOR);
        this.pesel = user.getPesel();
        this.phoneNumber = user.getPhoneNumber();
    }

    public UserTO() {
    }
}
