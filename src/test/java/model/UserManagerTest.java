package model;

import junit.framework.TestCase;

public class UserManagerTest extends TestCase {

	private Remote remote = new Remote();
	private UserManager userManager = new UserManager();
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testUpdateFrequency() {
		userManager.addRemote(remote);
		userManager.updateFrequency(200);
		assertEquals(200, remote.getFrequency());
	}

}
