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
	public String email;
	public LocalDate dob;
	public String phone;
	public String gender;
	
	// the class constructor that accepts the parameters in a String array format
	public User(String[] args) {
		id = args[0];
		name = args[1];
		login = args[2];
		password = args[3];
		email = args[4];
		dob = LocalDate.parse(args[5], dateFormatter);
		phone = args[6];
		gender = args[7];
	}
	
	// the class constructor that accepts the parameters in a Map format
	public User(Map<String, String> map) {
		this(new String[]{
				map.get("id"),
				map.get("name"),
				map.get("login"),
				map.get("password"),
				map.get("email"),
				map.get("DOB"),
				map.get("phone"),
				map.get("gender"),
		});
	}
	
	// converts the user's parameters into a Map object
	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("name", name);
		map.put("login", login);
		map.put("password", password);
		map.put("email", email);
		map.put("DOB", dob.format(dateFormatter));
		map.put("phone", phone);
		map.put("gender", gender);
		return map;
	}
}
