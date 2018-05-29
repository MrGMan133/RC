package galekop.be.RC;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import generator.RandomOpenGenerator;
import model.Remote;

public class AppConcurrency {
	static final Logger logger = LogManager.getLogger(AppConcurrency.class.getName());
	
	public static void main(String[] args) {
		logger.info("Starting tasks");
		RandomOpenGenerator generator = new RandomOpenGenerator();
		
		generator.randomMultipleRequests();
		logger.info("Finishing tasks");
	}

}
