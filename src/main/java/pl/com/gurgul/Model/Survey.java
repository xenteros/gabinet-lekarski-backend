package pl.com.gurgul.Model;

import javax.persistence.*;

/**
 * Created by agurgul on 11.10.2016.
 */

@Entity
@Table(name = "surveys")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

}
