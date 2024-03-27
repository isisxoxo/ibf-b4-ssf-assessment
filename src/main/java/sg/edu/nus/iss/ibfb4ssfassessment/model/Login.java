package sg.edu.nus.iss.ibfb4ssfassessment.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class Login {

    @NotEmpty(message = "Email is a mandatory field")
    @Email(message = "Invalid Email")
    @Size(max = 50, message = "Email is too long")
    private String email;

    @Past(message = "Invalid Birth Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    public Login() {
    }

    public Login(String email, Date birthDate) {
        this.email = email;
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}
