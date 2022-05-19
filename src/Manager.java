import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Manager extends User {

	// the class constructor that accepts the parameters in a String array format
	public Manager(String[] args) {
		super(args);
	}
	
	// the class constructor that accepts a User object and imports its parameters
	public Manager(User user) {
		this(new String[]{
			user.id,
			user.name,
			user.login,
			user.password,
			user.email,
			user.dob.format(dateFormatter),
			user.phone,
		});
	}
		
	// the class constructor that accepts the parameters in a Map format
	public Manager(Map<String, String> map) {
		super(map);
	}
	
	// creates a new manager record in the database
	// returns -1 if the user with this login already exists
	// returns 0 if there it couldn't update the db
	// returns 1 if the update was a success
	public int registerManager(Manager manager) throws IOException {
		DB db = new DB();
		if (db.checkLogin(manager.login) == null) {
			return db.setManager(manager) ? 1 : 0;
		}
		return -1;
	}
	
	// creates a new trainer record in the database
	// returns -1 if the user with this login already exists
	// returns 0 if it couldn't update the db
	// returns 1 if the update was a success
	public int registerTrainer(Trainer trainer) throws IOException {
		DB db = new DB();
		if (db.checkLogin(trainer.login) == null) {
			return db.setTrainer(trainer) ? 1 : 0;
		}
		return -1;
	}
	
	// creates a new customer record in the database
	// returns -1 if the user with this login already exists
	// returns 0 if it couldn't update the db
	// returns 1 if the update was a success
	public int registerCustomer(Customer customer) throws IOException {
		DB db = new DB();
		if (db.checkLogin(customer.login) == null) {
			return db.setCustomer(customer) ? 1 : 0;
		}
		return -1;
	}
	
	// creates a new session record in the database
	// returns null if it couldn't update the db
	// returns a Session object of the newly created session if the update was a success
	public Session book(String[] args) throws IOException {
		Session session = new Session(args);
		DB db = new DB();
		return db.setSession(session) ? session : null;
	}

}
