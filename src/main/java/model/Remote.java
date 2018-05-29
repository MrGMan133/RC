package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Table(name="remotes")
@NamedQuery(
		name = "Remote.findIsActive",
		query = "SELECT r FROM Remote r WHERE r.isActive = true")
public class Remote implements RemoteObserver, Runnable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(name="frequency")
	private double frequency = 0.0;
	@Column(name="isActive")
	private boolean isActive = false;
	@Transient
	private Gate gate;
	static final Logger logger = LogManager.getLogger(Remote.class.getName());
	public void setGate(Gate gate) {
		this.gate = gate;
	}
	
	public Gate getGate() {
		return this.gate;
	}
	
	public Remote() {}
	
	public Remote(Gate gate) {
		this.setGate(gate);
	}

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
	public synchronized boolean sendRequest(Gate gate) {
		if (gate.handleRequest(this.getFrequency(), this.isActive)) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Remote " + id + " - Frequency: " + frequency + " - Is Active: " + isActive;
	}

	public void run() {
		try {
			sendRequest(gate);
			Thread.sleep(100);
			logger.info("Completed task");
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		
	}

}
