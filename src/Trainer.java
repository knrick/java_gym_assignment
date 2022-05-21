import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

public class Trainer extends User{
	// helps in string to datetime conversion and vice versa
	protected static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	// helps in string to time conversion and vice versa
	protected static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	
	// the class constructor that accepts the parameters in a String array format
	public Trainer(String[] args) {
		super(args);
	}
	
	// the class constructor that accepts a User object and imports its parameters
	// for the trainer parameters additionally accepts a String array
	public Trainer(User user) {
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
	public Trainer(Map<String, String> map) {
		super(map);
	}
	
	// returns of datetime of the next monday
	// the estimation is done based on the datetime passed into the function
	private LocalDateTime getNextMonday(LocalDateTime dateTime) {
		return dateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
	}
	
	// returns of datetime of the next monday
	// the estimation is done based on the current datetime
	private LocalDateTime getNextMonday() {
		return getNextMonday(LocalDateTime.now());
	}
	
	// returns the next appropriate datetime for a session
	// the datetime will be determined based on the datetime passed into the function
	// the date can be only a weekday
	// the time can be only from 9am to 17pm
	// the time ranges in a 2-hour window i.e. 9am, 11am, 1pm, ..., 17pm
	private LocalDateTime getNextDateTime(LocalDateTime from) {
		if (from.toLocalTime().isAfter(LocalTime.of(16, 59, 59))) {
			from = from.with(LocalTime.of(9, 0));
			from = from.plusDays(1);
		} else {
			int hour = from.getHour();
			hour = hour < 8 ? 9 : hour + 1;
			hour -= hour % 2 - 1;
			from = from.with(LocalTime.of(hour, 0));
		}
		DayOfWeek day = DayOfWeek.of(from.get(ChronoField.DAY_OF_WEEK));
		if (day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY) from = getNextMonday(from);
		return from;
	}
	
	// returns the next appropriate datetime for a session
	// the datetime will be determined based on the current datetime
	// the date can be only a weekday
	// the time can be only from 9am to 17pm
	// the time ranges in a 2-hour window i.e. 9am, 11am, 1pm, ..., 17pm
	private LocalDateTime getNextDateTime() {
		return getNextDateTime(LocalDateTime.now());
	}
	
	// returns a Map object of available booking dates for the next 3 calendar weeks
	// the keys of the returned Map object will be the dates
	// and the values will be booleans indicating the availability of the datetime
	public Map<LocalDateTime, Boolean> getBookingDates() throws IOException {
		DB db = new DB();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("trainer", id);
		Map<String, Session> sessions = db.getSessions(filter);
		LocalDateTime[] bookedDateTimes = new LocalDateTime[sessions.size()];
		Map<LocalDateTime, Boolean> dateTimes = new HashMap<LocalDateTime, Boolean>();
		for (int i = 0; i < bookedDateTimes.length; i++) {
			String k = String.valueOf(sessions.keySet().toArray()[i]);
			bookedDateTimes[i] = LocalDateTime.of(sessions.get(k).date, sessions.get(k).time);
		}
		for (
				LocalDateTime dateTime = getNextDateTime();
				dateTime.isBefore(getNextMonday().plusDays(19));
				dateTime = getNextDateTime(dateTime)
		) {
			boolean isIn = false;
			for (LocalDateTime bookedDateTime: bookedDateTimes) {
				if (dateTime.equals(bookedDateTime)) {
					isIn = true;
					break;
				}
			}
			dateTimes.put(dateTime, !isIn);
		}
		return dateTimes;
	}

}
