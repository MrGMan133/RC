package model;

import junit.framework.TestCase;

public class GateTest extends TestCase {
	
	private Gate gate = new Gate();

	protected void setUp() throws Exception {
		super.setUp();
	}
	public void testGateHandleRequestTrue() {
		gate.setFrequency(100.00);
		assertEquals(true, gate.handleRequest(100.00, true));
	}
	public void testGateHandleRequesFalseFrequency() {
		gate.setFrequency(100.00);
		assertFalse(gate.handleRequest(200.00, true));
	}
	public void testGateHandleRequesFalseIsActive() {
		gate.setFrequency(100.00);
		assertFalse(gate.handleRequest(100.00, false));
	}

}
