import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DB {
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static String filename = "db.txt"; // this the db's txt file location
	
	// these are the lines that help us find the beginnings of the tables in the txt file
	private static String usersBeginLine = "##############USERS################\r\n";
	private static String trainersBeginLine = "#############TRAINERS##############\r\n";
	private static String managersBeginLine = "#############MANAGERS##############\r\n";
	private static String customersBeginLine = "#############CUSTOMERS#############\r\n";
	private static String sessionsBeginLine = "#############SESSIONS##############\r\n";
	
	// this line helps us find any table's end. Start searching only from the begin line
	private static String endLine = "\r\n###############END#################";
	
	// the column names for each table
	private static String[] userCols = {"id", "name", "login", "password", "email", "DOB", "phone"};
	private static String[] trainerCols = {"id"};
	private static String[] managerCols = {"id"};
	private static String[] customerCols = {"id"};
	private static String[] sessionCols = {"id", "trainer", "customer", "date", "time", "price", "paid", "rating", "feedback"};
	
	enum Table {
		USERS,
		TRAINERS,
		MANAGERS,
		CUSTOMERS,
		SESSIONS
	}
		
	// This function parses a row in a string format with each value separated by ;
	// Returns a Map variable. Maps in Java are like dictionaries in python
	// The returned Map object contains the column name as its key
	// and the column's corresponding value as the value
	private static Map<String, String> parseRow(String str, String[] cols) {
		String[] strings = str.split(";");
		Map<String, String> row = new HashMap<String, String>();
		for (int i = 0; i < Math.min(strings.length, cols.length); i++) {
			row.put(cols[i], strings[i]);
		}
		return row;
	}
	
	// Reads the txt file and returns Map object of the table's rows
	// The returned Map contains the row's index as its key
	// and a Map object with the values of that row as the value
	private static Map<String, Map<String, String>> getTable(
			String beginLine,
			String[] cols,
			Map<String, String> filter,
			Map<String, String> likeFilter
		) throws IOException {
		String content = new String(Files.readAllBytes(Path.of(filename)));
		int beginIdx = content.indexOf(beginLine) + beginLine.length();
		int endIdx = beginIdx + content.substring(beginIdx).indexOf(endLine);
		content = content.substring(beginIdx, endIdx);
		String[] rows = content.split("\r\n");
		Map<String, Map<String, String>> table = new HashMap<String, Map<String, String>>();
		for (int i = 0; i < rows.length; i++) {
			Map<String, String> row = parseRow(rows[i], cols);
			boolean match = true;
			for (String k: filter.keySet()) {
				if (!row.get(k).toLowerCase().equals(filter.get(k).toLowerCase())) {
					match = false;
					break;
				}
			}
			if (match)
				for (String k: likeFilter.keySet()) {
					String lower1 = row.get(k).toLowerCase();
					String lower2 = likeFilter.get(k).toLowerCase();
					if (!row.get(k).toLowerCase().contains(likeFilter.get(k).toLowerCase())) {
						match = false;
						break;
					}
				}
			if (match) table.put(row.get("id"), row);
		}
		return table;
	}
	
	private static Map<String, Map<String, String>> getTable(
			String beginLine,
			String[] cols,
			Map<String, String> filter
		) throws IOException {
		return getTable(beginLine, cols, filter, new HashMap<String, String>());
	}
	
	private static Map<String, Map<String, String>> getTable(
			String beginLine,
			String[] cols
		) throws IOException {
		return getTable(beginLine, cols, new HashMap<String, String>());
	}
	
	// Pass a new table to rewrite the txt file
	// the function will return a boolean value indicating whether the output was successful
	private static boolean setTable(Map<String, Map<String, String>> table, String beginLine, String[] cols) {
		try {
			String[] rows = new String[table.size()];
			String[] ids = table.keySet().toArray(new String[table.size()]);
			for (int i = 0; i < table.size(); i++) {
				Map<String, String> mapRow = table.get(ids[i]);
				String[] row = new String[mapRow.size()];
				for (int j = 0; j < cols.length; j++) {
					String col = cols[j];
					row[j] = mapRow.get(col);
				}
				rows[i] = String.join(";", row);
			}
			String content = String.join("\r\n", rows);
			String origContent = new String(Files.readAllBytes(Path.of(filename)));
			int beginIdx = origContent.indexOf(beginLine) + beginLine.length();
			int endIdx = beginIdx + origContent.substring(beginIdx).indexOf(endLine);
			content = origContent.substring(0, beginIdx) + content + origContent.substring(endIdx);
			Files.writeString(Paths.get(filename), content);
			return true;
		} catch(IOException e) {
			return false;
		}
	}
	
	public String getNextId(Table table) throws IOException {
		String beginLine = new String();
		String[] cols;
		switch (table) {
			case USERS:
				beginLine = usersBeginLine;
				cols = userCols;
				break;
			case TRAINERS:
				beginLine = trainersBeginLine;
				cols = trainerCols;
				break;
			case MANAGERS:
				beginLine = managersBeginLine;
				cols = managerCols;
				break;
			case CUSTOMERS:
				beginLine = customersBeginLine;
				cols = customerCols;
				break;
			case SESSIONS:
				beginLine = sessionsBeginLine;
				cols = sessionCols;
				break;
			default:
				cols = new String[]{};
		}
		Map<String, Map<String, String>> tableRows = getTable(beginLine, cols, new HashMap<String, String>());
		int max = -1;
		for (String k: tableRows.keySet()) {
			int id = Integer.parseInt(k);
			if (id > max) max = id;
		}
		return Integer.toString(max+1);
	}
	
	// Reads the users table from the txt file
	// Returns a Map object of User class objects
	// As the input accepts a Map object to filter the data
	// The keys of the filter map represent the column names
	// And values of the filter map represent the value for the appropriate column
	public Map<String, User> getUsers(Map<String, String> filter) throws IOException {
		Map<String, String> likeFilter = new HashMap<String, String>();
		String nameFilter = filter.get("name");
		if (nameFilter != null) {
			likeFilter.put("name", nameFilter);
			filter.remove("name");
		}
		String userTypeFilter = filter.remove("userType");
		Map<String, Map<String, String>> usersMap = getTable(usersBeginLine, userCols, filter, likeFilter);
		Map<String, User> users = new HashMap<String, User>();
		for (String k: usersMap.keySet()) {
			String id = usersMap.get(k).get("id");
			boolean add;
			if (userTypeFilter == null)
				add = true;
			else
				switch (userTypeFilter) {
					case "Trainer":
						add = getTrainer(id) != null;
						break;
					case "Manager":
						add = getManager(id) != null;
						break;
					case "Customer":
						add = getCustomer(id) != null;
						break;
					default:
						add = true;
				}
			if (add)
				users.put(k, new User(usersMap.get(k)));
		}
		return users;
	}
	
	// GetUsers with no filter
	public Map<String, User> getUsers() throws IOException {
		return getUsers(new HashMap<String, String>());
	}
	
	// Reads the users table from the txt file and returns a specific user
	// Returns a User class object
	public User getUser(String idx) throws IOException {
		Map<String, User> users = getUsers();
		User user = users.get(idx);
		return user;
	}
	
	// Adds or updates a user data in the txt file
	// As the input accepts a User class object
	public boolean setUser(User user) throws IOException {
		Map<String, User> users = getUsers();
		users.put(user.id, user);
		Map<String, Map<String, String>> usersMap = new HashMap<String, Map<String, String>>(); 
		for (String k: users.keySet()) {
			usersMap.put(k, users.get(k).toMap());
		}
		return setTable(usersMap, usersBeginLine, userCols);
	}
	
	

	// Reads the trainers table from the txt file
	// Returns a Map object of Trainer class objects
	// As the input accepts a Map object to filter the data
	// The keys of the filter map represent the column names
	// And values of the filter map represent the value for the appropriate column
	public Map<String, Trainer> getTrainers(Map<String, String> filter) throws IOException {
		Map<String, User> users = getUsers();
		Map<String, Map<String, String>> trainersMap = getTable(trainersBeginLine, trainerCols, filter);
		Map<String, Trainer> trainers= new HashMap<String, Trainer>();
		for (String k: trainersMap.keySet()) {
			Map<String, String> trainer = trainersMap.get(k);
			trainers.put(k, new Trainer(users.get(k)));
		}
		// convert the table into a Trainer map
		return trainers;
	}
	
	// GetTrainers with no filter
	public Map<String, Trainer> getTrainers() throws IOException {
		return getTrainers(new HashMap<String, String>());
	}
	
	// Adds or updates a trainer data in the txt file
	// As the input accepts a trainer class object
	public Trainer getTrainer(String idx) throws IOException {
		Map<String, Trainer> trainers = getTrainers();
		Trainer trainer = trainers.get(idx);
		return trainer;
	}
	
	// Adds or updates a trainer data in the txt file
	// As the input accepts a Trainer class object
	public boolean setTrainer(Trainer trainer) throws IOException {
		User user = (User)trainer; // in the future will make an actual conversion
		if (setUser(user)) {
			Map<String, Trainer> trainers = getTrainers();
			trainers.put(trainer.id, trainer);
			Map<String, Map<String, String>> trainersMap = new HashMap<String, Map<String, String>>(); 
			for (String k: trainers.keySet()) {
				trainersMap.put(k, trainers.get(k).toMap());
			}
			// convert the User map into a map full of strings
			return setTable(trainersMap, trainersBeginLine, trainerCols);
		} else {
			return false;
		}
	}

	// Reads the managers table from the txt file
	// Returns a Map object of Manager class objects
	// As the input accepts a Map object to filter the data
	// The keys of the filter map represent the column names
	// And values of the filter map represent the value for the appropriate column
	public Map<String, Manager> getManagers(Map<String, String> filter) throws IOException {
		Map<String, User> users = getUsers();
		Map<String, Map<String, String>> managersMap = getTable(managersBeginLine, managerCols, filter);
		Map<String, Manager> managers = new HashMap<String, Manager>(); 
		for (String k: managersMap.keySet()) {
			managers.put(k, new Manager(users.get(k)));
		}
		// convert the table into a Manager map
		return managers;
	}
	
	// GetManagers with no filter
	public Map<String, Manager> getManagers() throws IOException {
		return getManagers(new HashMap<String, String>());
	}
	
	// Adds or updates a manager data in the txt file
	// As the input accepts a manager class object
	public Manager getManager(String idx) throws IOException {
		Map<String, Manager> managers = getManagers();
		Manager manager = managers.get(idx);
		return manager;
	}
	
	// Adds or updates a manager data in the txt file
	// As the input accepts a Manager class object
	public boolean setManager(Manager manager) throws IOException {
		User user = (User)manager; // in the future will make an actual conversion
		if (setUser(user)) {
			Map<String, Manager> managers = getManagers();
			managers.put(manager.id, manager);
			Map<String, Map<String, String>> managersMap = new HashMap<String, Map<String, String>>(); 
			for (String k: managers.keySet()) {
				managersMap.put(k, managers.get(k).toMap());
			}
			return setTable(managersMap, managersBeginLine, managerCols);
		} else {
			return false;
		}
	}
	
	// Reads the cutomers table from the txt file
	// Returns a Map object of Customer class objects
	// As the input accepts a Map object to filter the data
	// The keys of the filter map represent the column names
	// And values of the filter map represent the value for the appropriate column
	public Map<String, Customer> getCustomers(Map<String, String> filter) throws IOException {
		Map<String, User> users = getUsers();
		Map<String, Map<String, String>> customersMap = getTable(customersBeginLine, customerCols, filter);
		Map<String, Customer> customers = new HashMap<String, Customer>(); 
		for (String k: customersMap.keySet()) {
			customers.put(k, new Customer(users.get(k)));
		}
		// convert the table into a Customer map
		return customers;
	}
	
	// GetCustomers with no filter
	public Map<String, Customer> getCustomers() throws IOException {
		return getCustomers(new HashMap<String, String>());
	}
	
	// Adds or updates a customer data in the txt file
	// As the input accepts a customer class object
	public Customer getCustomer(String idx) throws IOException {
		Map<String, Customer> customers = getCustomers();
		Customer customer = customers.get(idx);
		return customer;
	}
	
	// Adds or updates a customer data in the txt file
	// As the input accepts a Customer class object
	public boolean setCustomer(Customer customer) throws IOException {
		User user = (User)customer; // in the future will make an actual conversion
		if (setUser(user)) {
			Map<String, Customer> customers = getCustomers();
			customers.put(customer.id, customer);
			Map<String, Map<String, String>> customersMap = new HashMap<String, Map<String, String>>();
			for (String k: customers.keySet()) {
				customersMap.put(k, customers.get(k).toMap());
			}
			return setTable(customersMap, customersBeginLine, customerCols);
		} else {
			return false;
		}
	}

	// Reads the sessions table from the txt file
	// Returns a Map object of Session class objects
	// As the input accepts a Map object to filter the data
	// The keys of the filter map represent the column names
	// And values of the filter map represent the value for the appropriate column
	public Map<String, Session> getSessions(Map<String, String> filter) throws IOException {
		LocalDate from = null;
		LocalDate to = null;
		if (filter.get("fromDate") != null)
			from = LocalDate.parse(filter.remove("fromDate"), dateFormatter);
		if (filter.get("toDate") != null)
			to = LocalDate.parse(filter.remove("toDate"), dateFormatter);
		Map<String, Map<String, String>> sessionsMap = getTable(sessionsBeginLine, sessionCols, filter);
		Map<String, Session> sessions = new HashMap<String, Session>();
		for (String k: sessionsMap.keySet()) {
			Session session = new Session(sessionsMap.get(k));
			if (
				(from == null || session.date.isAfter(from.minusDays(1)))
				&& (to == null || session.date.isBefore(to.plusDays(1)))
			)
				sessions.put(k, session);
		}
		return sessions;
	}
	
	// GetSessions with no filter
	public Map<String, Session> getSessions() throws IOException {
		return getSessions(new HashMap<String, String>());
	}
	
	// Adds or updates a session data in the txt file
	// As the input accepts a session class object
	public Session getSession(String i) throws IOException {
		Map<String, Session> sessions = getSessions();
		Session session = sessions.get(i);
		return session;
	}
	
	// Adds or updates a session data in the txt file
	// As the input accepts a Session class object
	public boolean setSession(Session session) throws IOException {
		Map<String, Session> sessions = getSessions();
		sessions.put(session.id, session);
		Map<String, Map<String, String>> sessionsMap = new HashMap<String, Map<String, String>>();
		for (String k: sessions.keySet()) {
			sessionsMap.put(k, sessions.get(k).toMap());
		}
		return setTable(sessionsMap, sessionsBeginLine, sessionCols);
	}
	
	// Tries to find a user with the login passed into the function
	// And returns true if there is such user in the database
	public String checkLogin(String login) throws IOException {
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("login", login);
		Map<String, User> users = getUsers(filter);
		for (String k: users.keySet())
			return k;
		return null;
	}
	
	public String checkEmail(String email) throws IOException {
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("email", email);
		Map<String, User> users = getUsers(filter);
		for (String k: users.keySet())
			return k;
		return null;
	}
	
	public String checkPhone(String phone) throws IOException {
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("phone", phone);
		Map<String, User> users = getUsers(filter);
		for (String k: users.keySet())
			return k;
		return null;
	}
	
	// Checks whether the password of the specified user
	// matches the one passed into the function
	public boolean checkPassword(String idx, String password) throws IOException {
		User user = getUser(idx);
		return password.equals(user.password);
	}
}
