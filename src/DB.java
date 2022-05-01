import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DB {
	private static String filename = "db.txt"; // this the db's txt file location
	
	// these are the lines that help us find the beginnings of the tables in the txt file
	private static String personsBeginLine = "##############PERSONS##############\r\n";
	private static String usersBeginLine = "###############USERS###############\r\n";
	private static String trainersBeginLine = "#############TRAINERS##############\r\n";
	private static String managersBeginLine = "#############MANAGERS##############\r\n";
	private static String sessionsBeginLine = "#############SESSIONS##############\r\n";
	
	// this line helps us find any table's end. Start searching only from the begin line
	private static String endLine = "\r\n###############END#################";
	
	// the column names for each table
	private static String[] personCols = {"id", "name", "DOB", "phone"};
	private static String[] userCols = {"id", "login", "password"};
	private static String[] trainerCols = {"id", "photo"};
	private static String[] managerCols = {"id"};
	private static String[] sessionCols = {"id", "trainer", "customer", "date", "time", "price", "paid"};
	
	// The DB class's constructor function. Not sure if we need to put anything inside it
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
	private static Map<String, Map<String, String>> getTable(String beginLine, String[] cols) throws IOException {
		String content = new String(Files.readAllBytes(Path.of(filename)));
		int beginIdx = content.indexOf(beginLine) + beginLine.length();
		int endIdx = beginIdx + content.substring(beginIdx).indexOf(endLine);
		content = content.substring(beginIdx, endIdx);
		String[] rows = content.split("\r\n");
		Map<String, Map<String, String>> table = new HashMap<String, Map<String, String>>();
		for (int i = 0; i < rows.length; i++) {
			Map<String, String> row = parseRow(rows[i], cols);
			table.put(row.get("id"), row);
		}
		return table;
	}
	
	// Pass a new table to rewrite the txt file
	// the function will return a boolean value indicating whether the output was successful
	private static boolean setTable(Map<String, Map<String, String>> table, String beginLine, String[] cols) {
		try {
			String[] rows = {};
			for (int i = 0; i < table.size(); i++) {
				String[] row = {};
				for (int j = 0; j < cols.length; j++) {
					row[j] = table.get(table.keySet().toArray()[i]).get(cols[j]);
				}
				rows[i] = String.join(";", row);
			}
			String content = String.join("\r\n", rows);
			String origContent = new String(Files.readAllBytes(Path.of(filename)));
			int beginIdx = content.indexOf(beginLine) + beginLine.length();
			int endIdx = beginIdx + content.substring(beginIdx).indexOf(endLine);
			content = origContent.substring(0, beginIdx) + content + origContent.substring(endIdx);
			Files.writeString(Paths.get(filename), content);
			return true;
		} catch(IOException e) {
			return false;
		}
	}
	
	// Reads the persons table from the txt file
	// Right now returns a nested Map object as described in the comments of getTable() and getRow() functions
	// Then it will be a Map object of Person class objects that we will implement later
	public Map<String, Map<String, String>> getPersons() throws IOException {
		Map<String, Map<String, String>> persons = getTable(personsBeginLine, personCols);
		// convert the table into a Person map
		return persons;
	}
	
	// Reads the persons table from the txt file and returns a specific person
	// Right now returns a Map object as described in the comments of getRow() function
	// Then it will be a Person class object that we will implement later
	public Map<String, String> getPerson(int idx) throws IOException {
		Map<String, Map<String, String>> persons = getPersons();
		Map<String, String> person = persons.get(Integer.toString(idx));
		return person;
	}
	
	// Adds or updates a person data in the txt file
	// Right now as the input accepts a Map object as described in the comments of getRow() function
	// Then it will be a Map object of Person class objects that we will implement later
	public boolean setPerson(Map<String, String> person) throws IOException {
		Map<String, Map<String, String>> persons = getPersons();
		persons.put(person.get("id"), person);
		// convert the Person map into a map full of strings
		return setTable(persons, personsBeginLine, personCols);
	}
	
	// Reads the users table from the txt file. Also adds values from the persons table
	// Right now returns a nested Map object as described in the comments of getTable() and getRow() functions
	// Then it will be a Map object of User class objects that we will implement later
	public Map<String, Map<String, String>> getUsers() throws IOException {
		Map<String, Map<String, String>> persons = getPersons();
		Map<String, Map<String, String>> users = getTable(usersBeginLine, userCols);
		String[] ids = users.keySet().toArray(new String[users.size()]);
		for (int i = 0; i < users.size(); i++) {
			Map<String, String> user = users.get(ids[i]);
			Map<String, String> person = persons.get(user.get("id"));
			for (int j = 1; j < personCols.length; j++) {
				user.put(personCols[j], person.get(personCols[j]));
			}
			users.put(user.get("id"), user);
		}
		// convert the table into a User map
		return users;
	}
	
	// Reads the users table from the txt file and returns a specific user. Also adds values from the persons table
	// Right now returns a Map object as described in the comments of getRow() function
	// Then it will be a User class object that we will implement later
	public Map<String, String> getUser(int idx) throws IOException {
		Map<String, Map<String, String>> users = getUsers();
		Map<String, String> user = users.get(Integer.toString(idx));
		return user;
	}
	
	// Adds or updates a user data in the txt file
	// Maybe should also update the person data
	// Right now as the input accepts a Map object as described in the comments of getRow() function
	// Then it will be a Map object of User class objects that we will implement later
	public boolean setUser(Map<String, String> user) throws IOException {
		Map<String, String> person = user; // in the future will make an actual conversion
		if (setPerson(person)) {
			Map<String, Map<String, String>> users = getUsers();
			users.put(user.get("id"), user);
			// convert the User map into a map full of strings
			return setTable(users, usersBeginLine, userCols);
		} else {
			return false;
		}
	}

	// Reads the trainers table from the txt file. Also adds values from the persons and users tables
	// Right now returns a nested Map object as described in the comments of getTable() and getRow() functions
	// Then it will be a Map object of Trainer class objects that we will implement later
	public Map<String, Map<String, String>> getTrainers() throws IOException {
		Map<String, Map<String, String>> users = getUsers();
		Map<String, Map<String, String>> trainers = getTable(trainersBeginLine, trainerCols);
		String[] ids = trainers.keySet().toArray(new String[trainers.size()]);
		for (int i = 0; i < trainers.size(); i++) {
			Map<String, String> trainer = trainers.get(ids[i]);
			Map<String, String> user = users.get(trainer.get("id"));
			for (int j = 1; j < personCols.length; j++) {
				trainer.put(personCols[j], user.get(personCols[j]));
			}
			for (int j = 1; j < userCols.length; j++) {
				trainer.put(userCols[j], user.get(userCols[j]));
			}
			trainers.put(trainer.get("id"), trainer);
		}
		// convert the table into a Trainer map
		return trainers;
	}
	
	// Reads the trainers table from the txt file and returns a specific trainer. Also adds values from the persons and users tables
	// Right now returns a Map object as described in the comments of getRow() function
	// Then it will be a Trainer class object that we will implement later
	public Map<String, String> getTrainer(int idx) throws IOException {
		Map<String, Map<String, String>> trainers = getTrainers();
		Map<String, String> trainer = trainers.get(Integer.toString(idx));
		return trainer;
	}
	
	// Adds or updates a trainer data in the txt file
	// Maybe should also update the person and user data
	// Right now as the input accepts a Map object as described in the comments of getRow() function
	// Then it will be a Map object of Trainer class objects that we will implement later
	public boolean setTrainer(Map<String, String> trainer) throws IOException {
		Map<String, String> user = trainer; // in the future will make an actual conversion
		if (setUser(user)) {
			Map<String, Map<String, String>> trainers = getTrainers();
			trainers.put(trainer.get("id"), trainer);
			// convert the User map into a map full of strings
			return setTable(trainers, trainersBeginLine, trainerCols);
		} else {
			return false;
		}
	}

	// Reads the managers table from the txt file. Also adds values from the persons and users tables
	// Right now returns a nested Map object as described in the comments of getTable() and getRow() functions
	// Then it will be a Map object of Manager class objects that we will implement later
	public Map<String, Map<String, String>> getManagers() throws IOException {
		Map<String, Map<String, String>> users = getUsers();
		Map<String, Map<String, String>> managers = getTable(managersBeginLine, managerCols);
		String[] ids = managers.keySet().toArray(new String[managers.size()]);
		for (int i = 0; i < managers.size(); i++) {
			Map<String, String> manager = managers.get(ids[i]);
			Map<String, String> user = users.get(manager.get("id"));
			for (int j = 1; j < personCols.length; j++) {
				manager.put(personCols[j], user.get(personCols[j]));
			}
			for (int j = 1; j < userCols.length; j++) {
				manager.put(userCols[j], user.get(userCols[j]));
			}
			managers.put(manager.get("id"), manager);
		}
		// convert the table into a Manager map
		return managers;
	}
	
	// Reads the managers table from the txt file and returns a specific manager. Also adds values from the persons and users tables
	// Right now returns a Map object as described in the comments of getRow() function
	// Then it will be a Manager class object that we will implement later
	public Map<String, String> getManager(int idx) throws IOException {
		Map<String, Map<String, String>> managers = getManagers();
		Map<String, String> manager = managers.get(Integer.toString(idx));
		return manager;
	}
	
	// Adds or updates a manager data in the txt file
	// Maybe should also update the person and user data
	// Right now as the input accepts a Map object as described in the comments of getRow() function
	// Then it will be a Map object of Manager class objects that we will implement later
	public boolean setManager(Map<String, String> manager) throws IOException {
		Map<String, String> user = manager; // in the future will make an actual conversion
		if (setUser(user)) {
			Map<String, Map<String, String>> managers = getManagers();
			managers.put(manager.get("id"), manager);
			// convert the User map into a map full of strings
			return setTable(managers, managersBeginLine, managerCols);
		} else {
			return false;
		}
	}

	// Reads the sessions table from the txt file
	// Right now returns a nested Map object as described in the comments of getTable() and getRow() functions
	// Then it will be a Map object of Session class objects that we will implement later
	public Map<String, Map<String, String>> getSessions() throws IOException {
		Map<String, Map<String, String>> sessions = getTable(sessionsBeginLine, sessionCols);
		// convert the table into a Session map
		return sessions;
	}
	
	// Reads the sessions table from the txt file and returns a specific session
	// Right now returns a Map object as described in the comments of getRow() function
	// Then it will be a Session class object that we will implement later
	public Map<String, String> getSession(int idx) throws IOException {
		Map<String, Map<String, String>> sessions = getSessions();
		Map<String, String> session = sessions.get(Integer.toString(idx));
		return session;
	}
	
	// Adds or updates a session data in the txt file
	// Right now as the input accepts a Map object as described in the comments of getRow() function
	// Then it will be a Map object of Session class objects that we will implement later
	public boolean setSession(Map<String, String> session) throws IOException {
		Map<String, Map<String, String>> sessions = getSessions();
		sessions.put(session.get("id"), session);
		// convert the Person map into a map full of strings
		return setTable(sessions, sessionsBeginLine, sessionCols);
	}
}
