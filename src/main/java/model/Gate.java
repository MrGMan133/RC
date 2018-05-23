package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="gate")
public class Gate{
	@Id
	private long idGate;
	@Column(name="frequency")
	private double frequency;

	public Gate() {	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	
	public boolean handleRequest(Double requestorFrequency, boolean isActive) {
		if (isActive) {
			if (requestorFrequency == this.frequency) {
				return true;
			}
		}
		return false;
	}

	public void updateFrequency(double newFrequency) {
		this.frequency = newFrequency;
	}
}
