import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Session {
	// helps in string to date conversion and vice versa
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	// helps in string to time conversion and vice versa
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	
	public String id;
	public String trainer;
	public String customer;
	public LocalDate date;
	public LocalTime time;
	public float price;
	public boolean paid;
	public Integer rating;
	public String feedback;
	
	// the class constructor that accepts the parameters in a String array format
	public Session(String[] args) {
		String test = "hh:mm a";
		id = args[0];
		trainer = args[1];
		customer = args[2];
		date = LocalDate.parse(args[3], dateFormatter);
		time = LocalTime.parse(args[4], timeFormatter);
		price = Float.parseFloat(args[5]);
		paid = args[6].equals("1") | args[6].toLowerCase().equals("true");
		rating = args[7] == null || args[7].equals("") ? null : Integer.parseInt(args[7]);
		feedback = args[8];
	}

	// the class constructor that accepts the parameters in a Map format
	public Session(Map<String, String> map) {
		this(new String[]{
				map.get("id"),
				map.get("trainer"),
				map.get("customer"),
				map.get("date"),
				map.get("time"),
				map.get("price"),
				map.get("paid"),
				map.get("rating"),
				map.get("feedback"),
		});
	}
	
	// converts the session's parameters into a Map object
	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("trainer", trainer);
		map.put("customer", customer);
		map.put("date", date.format(dateFormatter));
		map.put("time", time.format(timeFormatter));
		map.put("price", Float.toString(price));
		map.put("paid", paid ? "true" : "false");
		map.put("rating", rating == null ? "" : Integer.toString(rating));
		map.put("feedback", feedback);
		return map;
	}
	
	public String getDateString() {
		return date.format(dateFormatter);
	}

}
