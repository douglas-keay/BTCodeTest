
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RouterCheck {
	
	public static void main(String[] args) {
		if(args.length==1)
		new RouterCheck(args[0]);
		else
			System.out.println("Incorrect number of arguments");
	}

	// Stores are list of all the routers from the CSV file
	private ArrayList<Router> routers;
	
	/**
	 * Constructor Takes in filepath and runs the required methods
	 * 
	 * @param fileName
	 *            filepath entered by user
	 */
	public RouterCheck(String fileName) {
		routers = new ArrayList<Router>();
		open(fileName);
		check();
		print();

	}

	/**
	 * Open, reads and parses all the items in the CSV file. Stores the entry in
	 * the routers array list
	 * 
	 * @param f
	 *            file location
	 */
	public void open(String f) {
		String row = "";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(f));
			// Skips the first row which is the headers, assumed all files will
			// have this
			row = reader.readLine();
			// Loops through all rows in the CSV file
			while ((row = reader.readLine()) != null) {
				// Splits the row by the comma

				String[] data = row.split(",");
				if (valid(data))
					routers.add(new Router(data));
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("An input output error occured");
		}
	}

	/**
	 * 
	 * Check if data is of valid type
	 * 
	 * @param data
	 *            row from open
	 * @return true if valid false if not
	 */
	public boolean valid(String[] data) {
		// Blank line ignoring
		if (data.length==0)
			return false;
		// Splits hostname by . into into smaller components
		String[] h = data[0].split("\\.");
		// Assuming standard format of minimum abc.xyz for hostname
		if (h.length < 2) {
			System.out.println("hostname " + data[0] + " is of invalid type skiping entry");
			return false;
		}
		// Splits ip by . into into 4
		String[] ip = data[1].split("\\.");
		// Assuming ip 4 format of 4 parts ranging between 0 and 256
		if (ip.length != 4) {
			System.out.println("ip " + data[1] + " is of invalid type skiping entry");
			return false;
		}
		int[] ips = new int[4];
		//Check if ip is a number and if is in range
		for (int i = 0; i < ip.length; i++) {
			try {
				ips[i] = Integer.parseInt(ip[i]);
			} catch (NumberFormatException e) {
				System.out.println("ip " + data[1] + " is of invalid type skiping entry");
				return false;
			}
			if (ips[i] < 0 || ips[0] > 256) {
				System.out.println("ip " + data[1] + " is of invalid type skiping entry");
				return false;
			}
		}
		//Checks if patched is yes or no
		if (!(data[2]).toLowerCase().equals("yes") && !(data[2]).toLowerCase().equals("no")) {
			System.out.println("Patched " + data[2] + " is of invalid format skiping entry");
			return false;
		}

		//checks if os is a double
		try {
			Double.parseDouble(data[3]);
		} catch (NumberFormatException e) {
			System.out.println("OS " + data[3] + " is invalid format skiping entry");
			return false;
		}
		
		//Returns true as by getting here everything should be valid
		//Note not checked as it can be anything and optional
		return true;

	}

	/**
	 * Check the routers against the patch criteria
	 */
	public void check() {
		for (int i = 0; i < routers.size(); i++) {
			// Checks if the router has been patched
			if (routers.get(i).isPatched()) {
				routers.remove(i);
				// Returns to the correct location as an item has been removed
				i--;
			}
			// Checks if Router is correct OS version
			else if (routers.get(i).getOs() < 12) {
				routers.remove(i);
				// Returns to the correct location as an item has been removed
				i--;
			}
		}

		// Compares routers to check if there are duplicate hostname or ip
		for (int i = 0; i < routers.size(); i++) {
			for (int x = i + 1; x < routers.size(); x++) {
				if (routers.get(i).getIp().equals(routers.get(x).getIp())
						|| routers.get(i).getHostname().equals(routers.get(x).getHostname())) {
					routers.remove(i);
					routers.remove(x - 1);
					// Returns to the correct location as an item has been
					// removed
					i--;
				}
			}
		}
	}

	/**
	 * Prints routers in requested format
	 */
	public void print() {
		for (int i = 0; i < routers.size(); i++) {
			System.out.print(routers.get(i).getHostname() + " (" + routers.get(i).getIp() + ")," + " OS version "
					+ routers.get(i).getOs());
			// Prints note if stored
			String note;
			if ((note = routers.get(i).getNotes()) != "")
				System.out.println(" [" + note + "]");
			else
				System.out.println("");
		}
	}
} 
