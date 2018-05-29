package generator;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Gate;
import model.Remote;
import persistency.GenericDAO;

public class RandomOpenGenerator {
	ExecutorService executorService = Executors.newCachedThreadPool();
	private GenericDAO<Gate> gateDAO = new GenericDAO<Gate>(Gate.class);
	private GenericDAO<Remote> remoteDAO = new GenericDAO<Remote>(Remote.class);
	private Gate gate = gateDAO.findOne(1);;
	private List<Remote> remotes = remoteDAO.findAll();
	static final Logger logger = LogManager.getLogger(RandomOpenGenerator.class.getName());
	Random random = new Random();
	public void testMethod() {
		for (Remote remote : remotes) {
			boolean openGate = remote.sendRequest(gate);
			logger.info("Can open gate: " + openGate);
		}
	}
	public void randomRequest() {
			for (int i = 0; i < remotes.size(); i++) {
				Remote chosenRemote = remotes.get(random.nextInt(remotes.size()));
				chosenRemote.setGate(gate);
				executorService.execute(chosenRemote);
				logger.info("Can open gate: " + chosenRemote.sendRequest(gate));
			}
			executorService.shutdown();
	}
	public void randomMultipleRequests() {
		for (Remote remote : remotes) {
			for (int i = 0; i < random.nextInt(5); i++) {
				remote.setGate(gate);
				executorService.execute(remote);
				logger.info("Remote: " + remote.toString() + " can open gate: " + remote.sendRequest(gate));
			}
		}
		executorService.shutdown();
	}
}
