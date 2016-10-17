package pl.com.gurgul.model;

import pl.com.gurgul.utils.UserRoles;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by agurgul on 17.10.2016.
 */
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated
    private UserRoles userRole;
}
