package pl.com.gurgul.model;

/**
 * Created by agurgul on 17.10.2016.
 */
public enum Gender {
    MALE("MALE"), FEMALE("FEMALE");

    private String value;

    private Gender(String value) {this.value = value;}

    private static final Gender fromValue(String key) {
        for (Gender g : Gender.values()) {
            if (g.value.equals(key)) {
                return g;
            }
        }
        return null;
    }

}
