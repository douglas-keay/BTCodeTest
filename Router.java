
public class Router {

	private String hostname;
	private String ip;
	private boolean patched;
	private double os;
	private String notes;

	/**
	 * Constructor Takes in a router String array and store data
	 * @param router
	 */
	public Router(String[] router) {
		hostname = router[0].toLowerCase();
		ip = router[1];
		//Stores yes and no as boolean for searching
		if (((router[2]).toLowerCase()).equals("yes"))
			patched = true;
		else
			patched = false;
		os = Double.parseDouble(router[3]);
		//Ensure note is not null if no note is entered
		if (router.length < 5)
			notes = "";
		else
			notes = router[4];
	}

	/**
	 * Getter for hostname
	 * 
	 * @return hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Getter for IP
	 * @return ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Getter for patched
	 * 
	 * @return patched
	 */
	public boolean isPatched() {
		return patched;
	}

	/**
	 * Getter for OS version
	 * 
	 * @return OS Version
	 */
	public double getOs() {
		return os;
	}

	/**
	 * Getter for notes
	 * 
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

}
