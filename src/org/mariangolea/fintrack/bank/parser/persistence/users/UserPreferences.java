package org.mariangolea.fintrack.bank.parser.persistence.repository.users;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userpreferences")
public class UserPreferences implements Serializable {

	private static final long serialVersionUID = 4737913401569904628L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time_frame")
    private Integer timeFrameInterval;

    @Column(name = "input_folder")
    private String inputFolder;
    
    public UserPreferences() {
    }

    public UserPreferences(Integer timeFrameInterval, String inputFolder) {
        this.timeFrameInterval = timeFrameInterval;
        this.inputFolder = inputFolder;
    }

    public UserPreferences(Long id, Integer timeFrameInterval, String inputFolder) {
        this(timeFrameInterval, inputFolder);
        this.id = id;
    }

    public Integer getTimeFrameInterval() {
        return timeFrameInterval;
    }

    public void setTimeFrameInterval(Integer timeFrameInterval) {
        this.timeFrameInterval = timeFrameInterval;
    }

    public String getInputFolder() {
        return inputFolder;
    }

    public void setInputFolder(String inputFolder) {
        this.inputFolder = inputFolder;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.id);
        hash = 61 * hash + Objects.hashCode(this.timeFrameInterval);
        hash = 61 * hash + Objects.hashCode(this.inputFolder);
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
        final UserPreferences other = (UserPreferences) obj;
        if (!Objects.equals(this.inputFolder, other.inputFolder)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.timeFrameInterval, other.timeFrameInterval)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserPreferences{" + "timeFrameInterval=" + timeFrameInterval + ", inputFolder=" + inputFolder + '}';
    }

}
