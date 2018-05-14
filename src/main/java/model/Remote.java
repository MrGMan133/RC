package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="remotes")
public class Remote implements RemoteObserver{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(name="frequency")
	private double frequency = 0.0;
	@Column(name="isActive")
	private boolean isActive = false;
	
	public Remote() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	
	public void updateFrequency(double newFrequency) {
		this.frequency = newFrequency;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Remote [id=" + id + ", frequency=" + frequency + "]";
	}

}
