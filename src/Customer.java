import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Customer extends User {

	// the class constructor that accepts the parameters in a String array format
	public Customer(String[] args) {
		super(args);
		// TODO Auto-generated constructor stub
	}
	
	// the class constructor that accepts a User object and imports its parameters
	public Customer(User user) {
		this(new String[]{
			user.id,
			user.name,
			user.login,
			user.password,
			user.email,
			user.dob.format(dateFormatter),
			user.phone,
			user.gender
		});
	}

	// the class constructor that accepts the parameters in a Map format
	public Customer(Map<String, String> map) {
		super(map);
	}
		
	// adds a feedback to the session
	// can overwrite the previous feedback
	// returns false if it couldn't update the db
	// returns true if the update was a success
	public boolean addFeedback(String sessionId, int rating, String comment) throws IOException {
		DB db = new DB();
		Session session = db.getSession(sessionId);
		session.rating = rating;
		session.feedback = comment;
		return db.setSession(session);
	}
	
	// makes a payment for a session
	// returns -1 if the session was already paid
	// returns 0 if it couldn't update the db
	// returns 1 if the update was a success
	public int pay(String sessionId, float amount) throws IOException {
		DB db = new DB();
		Session session = db.getSession(sessionId);
		if (!session.paid) {
			session.price = amount;
			session.paid = true;
			return db.setSession(session) ? 1 : 0;
		}
		return -1;
		
	}

}
