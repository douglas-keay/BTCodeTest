import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import RPC.RouterCheck;
import RPC.Router;

public class RouterCheckTests {
	
	//String values for entry
	private String hostname;
	private String ip;
	private String patchedS;
	private String osS;
	private String notes;
	//Boolean values for comparison against returned
	private boolean patched;
	private double os;
	private String[] data;
	private Router r;
	private RouterCheck rc;

	/**
	 * Setup for testing
	 */
	@Before
	public void setup() {
		data= new String[5];
		hostname = "t.test.com";
		ip = "86.164.115.87";
		patchedS="no";
		osS = "13";
		notes = "Test case";
		patched = false;
		os = 13;
		data[0]=hostname;
		data[1]=ip;
		data[2]=patchedS;
		data[3]=osS;
		data[4]=notes;
		r=new Router(data);
		rc = new RouterCheck("test.csv");
	}
	
	/**
	 * Checking hostname is correctly stored
	 */
	@Test
	public void getHostname() {
		assertEquals(r.getHostname(),hostname);
	}
	
	/**
	 * Checking ip is correctly stored
	 */
	@Test
	public void getIP() {
		assertEquals(r.getIp(),ip);
	}
	
	/**
	 * Checking patched is correctly stored
	 */
	@Test
	public void getPatched() {
		assertEquals(r.isPatched(),patched);
	}

	/**
	 * Checking os is correctly stored
	 */
	@Test
	public void geOS() {
		assertEquals(r.getOs(),os,0);
	}
	
	/**
	 * Checking notes is correctly stored
	 */
	@Test
	public void getNotes() {
		assertEquals(r.getNotes(),notes);
	}
	
	/**
	 * Checking valid data returns true
	 */
	@Test
	public void valid() {
		assertTrue(rc.valid(data));
	}
	
	/**
	 * Testing empty data returns false
	 */
	@Test
	public void invalidEmptyData() {
		String[] test=new String[0];
		assertFalse(rc.valid(test));
	}
		
	/**
	 * Testing an invalid hostname returns false
	 */
	@Test
	public void invalidHostname() {
		data[0]="testing";
		assertFalse(rc.valid(data));
	}
	
	/**
	 * Testing an ip missing an item returns false
	 */
	@Test
	public void invalidIPShort() {
		data[1]="1.1.1";
		assertFalse(rc.valid(data));
	}
	
	/**
	 * Testing an ip which is not a number returns false
	 */
	@Test
	public void invalidIPNotNumber() {
		data[1]="one.one.one.one";
		assertFalse(rc.valid(data));
	}
	
	/**
	 * Testing an ip out of the ip range of 0 - 256 returns false
	 */
	@Test
	public void invalidIPOutOfRange() {
		data[1]="290.1.1.1";
		assertFalse(rc.valid(data));
	}
	
	/**
	 * Testing an ip out of the ip range of 0 - 256 returns false
	 */
	@Test
	public void invalidOutOfRange2() {
		data[1]="-290.1.1.1";
		assertFalse(rc.valid(data));
	}
	
	/**
	 * Testing a patched value which is not valid returns false
	 */
	@Test
	public void invalidPatched() {
		data[2]="h";
		assertFalse(rc.valid(data));
	}
	
	/**
	 * Testing an os value that is not a number false
	 */
	@Test
	public void invalidNoNumberOS() {
		data[3]="h";
		assertFalse(rc.valid(data));
	}

}
