package ch.heig.dai.mail;

/**
 * Represents a victim with an email address
 *
 * @author Jarod Streckeisen, Timothee Van Hove
 */
public class Victim {
    private final String email;

    public Victim(String email) {
        this.email = email;
    }

    /**
     * Get email of a victim
     *
     * @return a string representation of the email
     */
    public String getEmail() {
        return email;
    }
}
