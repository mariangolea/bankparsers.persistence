package org.mariangolea.fintrack.bank.parser.persistence.users;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.mariangolea.fintrack.bank.parser.persistence.FintrackEntityBase;
import org.mariangolea.fintrack.user.UserInterface;
import org.mariangolea.fintrack.user.UserPreferencesInterface;

@Entity
@Table(name = "users")
public class User extends FintrackEntityBase implements Serializable, UserInterface {

	private static final long serialVersionUID = 3696724292592234147L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @JoinColumn(name = "preferences_id")
	@OneToOne(cascade = CascadeType.ALL)
    private UserPreferences preferences;

    public User() {
    	this(null, null, null);
    }

    public User(String name, String password, UserPreferences preferences) {
        this.name = adjustString(name, 250, "");
        this.password = adjustString(password, 250, "");
        this.preferences = preferences;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = adjustString(name, 250, "");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = adjustString(password, 250, "");
    }

    public UserPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(UserPreferences preferences) {
        this.preferences = preferences;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + Objects.hashCode(this.password);
        hash = 83 * hash + Objects.hashCode(this.preferences);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.preferences, other.preferences);
    }

    @Override
    public String toString() {
        return "User{" + "name=" + name + ", password=" + password + ", preferences=" + preferences + '}';
    }
    
	@Override
	public void setPreferences(UserPreferencesInterface preferences) {
		this.preferences = new UserPreferences(preferences);
		
	}
}
