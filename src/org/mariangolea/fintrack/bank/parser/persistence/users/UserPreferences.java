package org.mariangolea.fintrack.bank.parser.persistence.users;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.mariangolea.fintrack.bank.parser.persistence.FintrackEntityBase;
import org.mariangolea.fintrack.user.UserPreferencesInterface;

@Entity
@Table(name = "userpreferences")
public class UserPreferences extends FintrackEntityBase implements Serializable, UserPreferencesInterface {

	private static final long serialVersionUID = 4737913401569904628L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "time_frame", nullable = false)
	private Integer timeFrameInterval;

	@Column(name = "input_folder", nullable = false)
	private String inputFolder;

	@Column(name = "page_size", nullable = false)
	private Integer pageSize;
	
	public UserPreferences() {
		this(null, null, null);
	}

	public UserPreferences(Integer timeFrameInterval, String inputFolder, Integer pageSize) {
		this.timeFrameInterval = adjustInteger(timeFrameInterval, 1);
		this.inputFolder = adjustString(inputFolder,250, "");
		this.pageSize = adjustInteger(pageSize, 50);
	}
	
	public UserPreferences(UserPreferencesInterface preferences) {
		this.timeFrameInterval = preferences == null ? null : preferences.getTimeFrameInterval();
		this.inputFolder = preferences == null ? null : preferences.getInputFolder();
		this.pageSize = preferences == null ? null : preferences.getPageSize();
		
		this.timeFrameInterval = adjustInteger(timeFrameInterval, 1);
		this.inputFolder = adjustString(inputFolder,250, "");
		this.pageSize = adjustInteger(pageSize, 50);
	}

	public Integer getTimeFrameInterval() {
		return timeFrameInterval;
	}

	public void setTimeFrameInterval(Integer timeFrameInterval) {
		this.timeFrameInterval = adjustInteger(timeFrameInterval, 1);
	}

	public String getInputFolder() {
		return inputFolder;
	}

	public void setInputFolder(String inputFolder) {
		this.inputFolder = adjustString(inputFolder,250, "");
	}

	public Long getId() {
		return id;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = adjustInteger(pageSize, 50);
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputFolder, pageSize, timeFrameInterval);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPreferences other = (UserPreferences) obj;
		return Objects.equals(inputFolder, other.inputFolder) && Objects.equals(pageSize, other.pageSize)
				&& Objects.equals(timeFrameInterval, other.timeFrameInterval);
	}

	@Override
	public String toString() {
		return "UserPreferences{" + "timeFrameInterval=" + timeFrameInterval + ", inputFolder=" + inputFolder
				+ ", pageSize=" + pageSize + '}';
	}
}
