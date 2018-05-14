package model;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
	private double frequency;
	private List<RemoteObserver> remotesToUpdate = new ArrayList<RemoteObserver>();
	
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public List<RemoteObserver> getRemotesToUpdate() {
		return remotesToUpdate;
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
		for (RemoteObserver remoteObserver : this.remotesToUpdate) {
			remoteObserver.updateFrequency(newFrequency);
		}
	}
	
}
