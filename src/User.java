import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class User {
	// helps in string to date conversion and vice versa
	protected static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public String id;
	public String name;
	public String login;
	public String password;
	public LocalDate dob;
	public String phone;
	
	// the class constructor that accepts the parameters in a String array format
	public User(String[] args) {
		id = args[0];
		name = args[1];
		login = args[2];
		password = args[3];
		dob = LocalDate.parse(args[4], dateFormatter);
		phone = args[5];
	}
	
	// the class constructor that accepts the parameters in a Map format
	public User(Map<String, String> map) {
		this(new String[]{
				map.get("id"),
				map.get("name"),
				map.get("login"),
				map.get("password"),
				map.get("DOB"),
				map.get("phone"),
		});
	}
	
	// converts the user's parameters into a Map object
	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("name", name);
		map.put("login", login);
		map.put("password", password);
		map.put("DOB", dob.format(dateFormatter));
		map.put("phone", phone);
		return map;
	}
}
