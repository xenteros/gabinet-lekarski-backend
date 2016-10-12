package pl.com.gurgul.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by agurgul on 11.10.2016.
 */

@Entity
@Table(name = "Surveys")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String username;

    public Survey(String username) {
        this.username = username;
    }

    public Survey(long id) {
        this.id = id;
    }

    public Survey() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
