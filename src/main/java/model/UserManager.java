package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "userManager")
public class UserManager implements SubjectInterface{
	@Id
	private long iduserManager;
	@Column(name="frequency")
	private double frequency = 0.0;
	@Transient
	private List<Remote> remotesToUpdate = new ArrayList<Remote>();

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public List<Remote> getRemotesToUpdate() {
		return remotesToUpdate;
	}

	public void addRemoteToList(Remote remote) {
		remotesToUpdate.add(remote);
	}

	public void addRemote(Remote remoteToAdd) {
		remotesToUpdate.add(remoteToAdd);
		remoteToAdd.setFrequency(this.frequency);
		remoteToAdd.setActive(true);
	}

	public void removeRemote(Remote remoteToRemove) {
		remotesToUpdate.remove(remoteToRemove);
		remoteToRemove.setActive(false);
	}

	public void updateFrequency(double newFrequency) {
		this.frequency = newFrequency;
		for (Remote remote : this.remotesToUpdate) {
			remote.updateFrequency(newFrequency);
		}
	}

	@Override
	public String toString() {
		return "User Manager frequency=" + frequency;
	}

}
