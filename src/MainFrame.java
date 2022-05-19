import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JRadioButtonMenuItem;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;

import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import javax.swing.JPasswordField;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class MainFrame extends JFrame {
	enum UserType {
		CUSTOMER,
		MANAGER,
		TRAINER
	}
	private User user;
	private UserType userType;
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

	private JPanel contentPane;
	private JTextField loginEdit;
	private JTextField regNameEdit;
	private JTextField regPhoneEdit;
	private JTextField regEmailEdit;
	private JTextField regLoginEdit;
	private PanelTable manageRegTable;
	private PanelTable sessionTable;
	private PanelTable cust_trainSessTable;
	private PanelTable bookTable;
	private JTextField personalNameEdit;
	private JTextField personalEmailEdit;
	private JPasswordField passwordEdit;
	private JTextField manageRegNameEdit;
	private JTextField cust_trainSessFeedbackEdit;
	private JPasswordField regPasswordEdit;
	protected Window btnNewButton_7;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField_1;
	private JTextField textField_3;
	private JDateChooser sessionStartDateChooser;
	private JDateChooser sessionEndDateChooser;
	private JComboBox sessionPaidCombo;
	private JDateChooser cust_trainSessStartDateChooser;
	private JDateChooser cust_trainSessEndDateChooser;
	private JComboBox cust_trainSessPaidCombo;
	private ButtonGroup cust_trainSessRadioGroup;
	private JRadioButton feedbackRate1Radio;
	private JRadioButton feedbackRate2Radio;
	private JRadioButton feedbackRate3Radio;
	private JRadioButton feedbackRate4Radio;
	private JRadioButton feedbackRate5Radio;
	private JButton cust_trainSessPayButton;
	private JPasswordField personalOldPassEdit;
	private JPasswordField personalNewPassEdit;
	private JTextField personalPhoneEdit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void fillManageRegTable(String nameCheck, String userTypeCheck) {
		try {
			Map<String, String> filter = new HashMap<String, String>();
			filter.put("name", nameCheck);
			filter.put("userType", userTypeCheck);
			Map<String, User> users = new DB().getUsers(filter);
			Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
			for (String k: users.keySet()) {
				map.put(k, users.get(k).toMap());
			}
			manageRegTable.fillData(map, new String[]{"id", "name", "login", "email", "DOB", "phone"});
		} catch (IOException e) {
			// TODO show message "Could not get the users data from the database"
		}
	}
	
	private void fillManageRegTable() {
		fillManageRegTable("", "User Type");
	}
	
	private void fillSessionTable(
			PanelTable sessionTable,
			Date startDate,
			Date endDate,
			String status,
			String targetUser,
			int[] colsToHide
		) {
		if (user == null) return;
		Map<String, String> filter = new HashMap<String, String>();
		if (startDate != null)
			filter.put("fromDate", dateFormatter.format(startDate));
		if (endDate != null)
			filter.put("toDate", dateFormatter.format(endDate));
		status = status == "Paid" ? "true" : status == "Unpaid" ? "false" : null;
		if (status != null)
			filter.put("paid", status);
		filter.put(targetUser, user.id);
		try {
			DB db = new DB();
			Map<String, Session> sessions = db.getSessions(filter);
			Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
			for (String k: sessions.keySet()) {
				Map<String, String> session = sessions.get(k).toMap();
				session.put("trainer", db.getTrainer(session.get("trainer")).name);
				session.put("customer", db.getCustomer(session.get("customer")).name);
				map.put(k, session);
			}
			sessionTable.fillData(map, new String[]{"id", "trainer", "customer", "date", "time", "price", "paid", "rating", "feedback"}, colsToHide);
		} catch (IOException e) {
			// TODO show message "Could not get the sessions data from the database"
		}
	}
	
	private void fillBookTable(String trainerLogin) {
		try {
			DB db = new DB();
			Map<LocalDateTime, Boolean> bookDates = db.getTrainer(db.checkLogin(trainerLogin)).getBookingDates();
			LocalDate startDate = LocalDate.now();
			int nDays = 18+startDate.getDayOfWeek().getValue();
			String[][] tableVals = new String[nDays][5];
			for (int hour = 9; hour <= 17; hour += 2) {
				for (int i = 0; i < nDays; i++) {
					LocalDate thisDay = startDate.plusDays(i);
					DayOfWeek day = DayOfWeek.of(thisDay.get(ChronoField.DAY_OF_WEEK));
					String label;
					if (day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY)
						label = "Weekend";
					else {
						Boolean available = bookDates.get(LocalDateTime.of(thisDay, LocalTime.of(hour, 0)));
						label = thisDay.format(localDateFormatter);
						label += available != null && available == true ? " available" : " not available";
					}
					tableVals[i][(hour-9)/2] = label;
				}
			}
			Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
			bookTable.setModel(new DefaultTableModel(tableVals, new String[] {"09:00 AM", "11:00 AM", "01:00 PM", "03:00 PM", "05:00 PM"}));
		} catch (IOException e) {
			// TODO show message "Could not get the users data from the database"
		}
	}
	
	private void fillSessionTable() {
		fillSessionTable(
			sessionTable,
			sessionStartDateChooser.getDate(),
			sessionEndDateChooser.getDate(),
			String.valueOf(sessionPaidCombo.getSelectedItem()),
			"trainer",
			new int[] {0}
		);
	}
	
	private void fillCust_trainSessTable() {
		fillSessionTable(
			cust_trainSessTable,
			cust_trainSessStartDateChooser.getDate(),
			cust_trainSessEndDateChooser.getDate(),
			String.valueOf(cust_trainSessPaidCombo.getSelectedItem()),
			"customer",
			new int[] {0, 7, 8}
		);
		if (user != null) handleCust_trainSessTableSelection();
	}
	
	private void handleCust_trainSessTableSelection() {
		int row = cust_trainSessTable.getSelectedRow();
		if (row == -1) {
			cust_trainSessFeedbackEdit.setText("feedback comment");
			cust_trainSessRadioGroup.clearSelection();
			cust_trainSessPayButton.setEnabled(false);
			return;
		}
		switch (String.valueOf(cust_trainSessTable.getValueAt(row, 7))) {
		case "1":
			feedbackRate1Radio.doClick();
			break;
		case "2":
			feedbackRate2Radio.doClick();
			break;
		case "3":
			feedbackRate3Radio.doClick();
			break;
		case "4":
			feedbackRate4Radio.doClick();
			break;
		case "5":
			feedbackRate5Radio.doClick();
			break;
		default:
		}
		cust_trainSessFeedbackEdit.setText(String.valueOf(cust_trainSessTable.getValueAt(row, 8)));
		if (cust_trainSessTable.getValueAt(row, 6).equals("false"))
			cust_trainSessPayButton.setEnabled(true);
		else
			cust_trainSessPayButton.setEnabled(false);
	}

	/**
	 * Create the frame.
	 */
	public MainFrame(){
		setTitle("Main Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1188, 704);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		CardLayout cl = new CardLayout(0, 0);
		contentPane.setLayout(cl);
		
		JPanel authPanel = new JPanel();
		authPanel.setBackground(new Color(192, 192, 192));
		authPanel.setForeground(Color.CYAN);
		contentPane.add(authPanel, "1");
		authPanel.setLayout(null);
		
		JLabel authLabel = new JLabel("");
		Image image1 = new ImageIcon("APU logo.jpg").getImage();
		authLabel.setIcon(new ImageIcon(image1));
		authLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		authLabel.setBounds(588, -50, 470, 290);
		authPanel.add(authLabel);
		
		DB db = new DB();
		try {
			Session session = db.getSession("2");
			if (session != null)
				authLabel.setText(session.getDateString());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		JPanel mainMenuPanel = new JPanel();

		mainMenuPanel.setBackground(Color.CYAN);
		contentPane.add(mainMenuPanel, "2");
		mainMenuPanel.setLayout(null);
		
		JButton regPanelButton = new JButton("register");
		regPanelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		regPanelButton.setIcon(new ImageIcon("img\\new member.png"));
		regPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "3");
			}
		});
		regPanelButton.setBounds(13, 23, 244, 51);
		mainMenuPanel.add(regPanelButton);
		
		JButton manageRegPanelButton = new JButton("Manage registration");
		manageRegPanelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		manageRegPanelButton.setIcon(new ImageIcon("img\\list of members.png"));
		manageRegPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "4");
			}
		});
		manageRegPanelButton.setBounds(284, 23, 244, 51);
		mainMenuPanel.add(manageRegPanelButton);
		
		JButton bookPanelButton = new JButton("Book session");
		bookPanelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		bookPanelButton.setIcon(new ImageIcon("img\\session-idle-time-icon.png"));
		bookPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "5");
			}
		});
		bookPanelButton.setBounds(554, 23, 225, 51);
		mainMenuPanel.add(bookPanelButton);
		
		JButton sessionPanelButton = new JButton("check training session");
		sessionPanelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		sessionPanelButton.setIcon(new ImageIcon("img\\234.png"));
		sessionPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sessionStartDateChooser.setDate(null);
				sessionEndDateChooser.setDate(null);
				sessionPaidCombo.setSelectedIndex(0);
				cl.show(contentPane, "7");
			}
		});
		sessionPanelButton.setBounds(13, 23, 244, 51);
		mainMenuPanel.add(sessionPanelButton);
		
		JButton cust_trainSessPanelButton = new JButton("view own training session");
		cust_trainSessPanelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		cust_trainSessPanelButton.setIcon(new ImageIcon("img\\Actions-contact-date-icon.png"));
		cust_trainSessPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cust_trainSessStartDateChooser.setDate(null);
				cust_trainSessEndDateChooser.setDate(null);
				cust_trainSessPaidCombo.setSelectedIndex(0);
				cl.show(contentPane,"10");
			}
		});
		cust_trainSessPanelButton.setBounds(3, 23, 271, 51);
		mainMenuPanel.add(cust_trainSessPanelButton);
		
		JButton custPresonalBotton = new JButton("Presonal Details ");
		custPresonalBotton.setIcon(new ImageIcon("img\\Resume-icon.png"));
		custPresonalBotton.setFont(new Font("Tahoma", Font.BOLD, 14));
		custPresonalBotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane,"Presonal_Details");
			}
		});
		custPresonalBotton.setBounds(296, 23, 244, 51);
		mainMenuPanel.add(custPresonalBotton);
		
		JLabel lblNewLabel_27 = new JLabel("WELCOME");
		lblNewLabel_27.setIcon(new ImageIcon("img\\welcome-icon.png"));
		lblNewLabel_27.setFont(new Font("Algerian", Font.PLAIN, 52));
		lblNewLabel_27.setBounds(409, 314, 449, 191);
		mainMenuPanel.add(lblNewLabel_27);
		
		mainMenuPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				
				if (userType != UserType.MANAGER) {
					regPanelButton.setVisible(false);
					manageRegPanelButton.setVisible(false);
					bookPanelButton.setVisible(false);
				}

				if (userType != UserType.TRAINER) {
					sessionPanelButton.setVisible(false);
				//	btnNewButton_7.setVisible(false);
				}

				if (userType != UserType.CUSTOMER) {
					cust_trainSessPanelButton.setVisible(false);
					custPresonalBotton.setVisible(false);
				}
			}
		});
		
		JButton authButton = new JButton("Sign in");
		authButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		authButton.setIcon(new ImageIcon("img\\login.png"));
		authButton.setBounds(855, 444, 179, 47);
		authButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<String, String> filter = new HashMap<String, String>();
				filter.put("login", loginEdit.getText());
				filter.put("password", new String(passwordEdit.getPassword()));
				try {
					DB db = new DB();
					Map<String, User> allUsers = db.getUsers();
					Map<String, User> users = db.getUsers(filter);
					if (users.size() < 1) {
						JOptionPane.showMessageDialog(null, "Invalid login or password","warning",JOptionPane.INFORMATION_MESSAGE);
					} else {
						user = users.get(users.keySet().toArray()[0]);
						if (db.getManager(user.id) != null) {
							userType = UserType.MANAGER;
						} else if (db.getTrainer(user.id) != null) {
							userType = UserType.TRAINER;
						} else if (db.getCustomer(user.id) != null) {
							userType = UserType.CUSTOMER;
						} else {
							JOptionPane.showMessageDialog(null, "Invalid login or password","warning",JOptionPane.INFORMATION_MESSAGE);
						}
						if (user != null && userType != null)
							cl.show(contentPane, "2");
					}
				} catch (IOException exc) {
					
					JOptionPane.showMessageDialog(null, "We're sorry but the system's servers right are unavailable","warning",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		authPanel.add(authButton);
		
		loginEdit = new JTextField();
		loginEdit.setText("Jane Smith");
		loginEdit.setBounds(833, 288, 225, 33);
		authPanel.add(loginEdit);
		loginEdit.setColumns(10);
		
		JLabel loginLabel = new JLabel("Username");
		loginLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		loginLabel.setBounds(701, 296, 122, 25);
		authPanel.add(loginLabel);
		
		JLabel passwordLabel = new JLabel("password");
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		passwordLabel.setBounds(701, 358, 122, 33);
		authPanel.add(passwordLabel);
		Image image = new ImageIcon("img/gym_logo.png").getImage();
		
		passwordEdit = new JPasswordField();
		passwordEdit.setText("SuperJaney78");
		passwordEdit.setBounds(833, 361, 225, 35);
		authPanel.add(passwordEdit);
		
		JLabel lblNewLabel_28 = new JLabel("");
		lblNewLabel_28.setIcon(new ImageIcon("img\\APU logo (1).jpg"));
		lblNewLabel_28.setBounds(30, 201, 500, 290);
		authPanel.add(lblNewLabel_28);
		
		JLabel lblNewLabel_29 = new JLabel("");
		lblNewLabel_29.setIcon(new ImageIcon("img\\login background.PNG"));
		lblNewLabel_29.setBounds(10, 0, 1175, 657);
		authPanel.add(lblNewLabel_29);
		
		JPanel registerPanel = new JPanel();
		registerPanel.setBackground(Color.CYAN);
		contentPane.add(registerPanel, "3");
		registerPanel.setLayout(null);
		
		regNameEdit = new JTextField();
		regNameEdit.setFont(new Font("Tahoma", Font.BOLD, 10));
		regNameEdit.setBounds(221, 196, 96, 20);
		registerPanel.add(regNameEdit);
		regNameEdit.setColumns(10);
		
		regPhoneEdit = new JTextField();
		regPhoneEdit.setFont(new Font("Tahoma", Font.BOLD, 10));
		regPhoneEdit.setBounds(221, 288, 96, 20);
		registerPanel.add(regPhoneEdit);
		regPhoneEdit.setColumns(10);
		
		regEmailEdit = new JTextField();
		regEmailEdit.setFont(new Font("Tahoma", Font.BOLD, 10));
		regEmailEdit.setBounds(216, 354, 178, 20);
		registerPanel.add(regEmailEdit);
		regEmailEdit.setColumns(10);
		
		regLoginEdit = new JTextField();
		regLoginEdit.setFont(new Font("Tahoma", Font.BOLD, 10));
		regLoginEdit.setBounds(476, 122, 130, 20);
		registerPanel.add(regLoginEdit);
		regLoginEdit.setColumns(10);
	
		JComboBox userTypeComboBox = new JComboBox();
		userTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"Manager ", "Trainer", "Customer"}));
		userTypeComboBox.setFont(new Font("Tahoma", Font.BOLD, 10));
		userTypeComboBox.setBounds(137, 133, 86, 21);
		registerPanel.add(userTypeComboBox);
		
		JLabel lblNewLabel_6 = new JLabel("name");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_6.setBounds(137, 197, 64, 13);
		registerPanel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("DOB");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_7.setBounds(137, 236, 66, 18);
		registerPanel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("phone");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_8.setBounds(139, 289, 64, 19);
		registerPanel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("email");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_9.setBounds(139, 355, 72, 13);
		registerPanel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("gender");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_10.setBounds(139, 415, 72, 19);
		registerPanel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("login");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_11.setBounds(390, 126, 76, 31);
		registerPanel.add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("password");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_12.setBounds(390, 187, 76, 29);
		registerPanel.add(lblNewLabel_12);
		
		JLabel lblNewLabel_14 = new JLabel("New Member");
		lblNewLabel_14.setForeground(Color.BLUE);
		lblNewLabel_14.setFont(new Font("Aldhabi", Font.BOLD, 48));
		lblNewLabel_14.setIcon(new ImageIcon("img\\new member.png"));
		lblNewLabel_14.setBounds(323, 28, 298, 62);
		registerPanel.add(lblNewLabel_14);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"M", "F"}));
		comboBox_2.setBounds(233, 416, 45, 21);
		registerPanel.add(comboBox_2);
		
		JDateChooser regDateChooser = new JDateChooser();
		regDateChooser.setBounds(221, 239, 96, 20);
		registerPanel.add(regDateChooser);
		
		regPasswordEdit = new JPasswordField();
		regPasswordEdit.setBounds(476, 196, 145, 19);
		registerPanel.add(regPasswordEdit);
		
		JButton regButton = new JButton("ADD");
		regButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		regButton.setIcon(new ImageIcon("img\\new member.png"));
		regButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				UserType userType;
				switch (String.valueOf(userTypeComboBox.getSelectedItem())) {
					case "trainer":
						userType = UserType.TRAINER;
						break;
					case "manager":
						userType = UserType.MANAGER;
						break;
					default:
						userType = UserType.CUSTOMER;
						break;
				}
				DB db = new DB();
				try {
					String id = db.getNextId(DB.Table.USERS);
					String name = regNameEdit.getText();
					String login = regLoginEdit.getText();
					if (db.checkLogin(login) != null) {
						// TODO show message "The user with such name already exists"
						return;
					}
					String password = new String(regPasswordEdit.getPassword());
					String email = regEmailEdit.getText();
					if (db.checkEmail(email) != null) {
						// TODO show message "The user with such email already exists"
						return;
					}
					String dob = dateFormatter.format(regDateChooser.getDate());
					String phone = regPhoneEdit.getText();
					if (db.checkPhone(phone) != null) {
						// TODO show message "The user with such phone already exists"
						return;
					}
					user = new User(new String[] {id, name, login, password, email, dob, phone});
					boolean regStatus = false;
					switch (userType) {
						case TRAINER:
							regStatus = db.setTrainer(new Trainer(user));
							break;
						case MANAGER:
							regStatus = db.setManager(new Manager(user));
							break;
						case CUSTOMER:
							regStatus = db.setCustomer(new Customer(user));
							break;
						default:
							break;
					}
					if (regStatus) {
						JOptionPane.showMessageDialog(null, "member added successfully","message",JOptionPane.INFORMATION_MESSAGE);
					} else {
						// TODO change this message to "We're sorry but the system's servers right now are unavailable"
						JOptionPane.showMessageDialog(null, "ERROR!","message",JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (IOException exc) {
					// TODO show message "We're sorry but the system's servers right now are unavailable"
					return;
				}
			}
		});
		regButton.setBounds(393, 522, 165, 62);
		registerPanel.add(regButton);
		
		JButton regBackButton = new JButton("Back");
		regBackButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		regBackButton.setIcon(new ImageIcon("img\\Go-back-icon.png"));
		regBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		regBackButton.setBounds(64, 522, 165, 57);
		registerPanel.add(regBackButton);
		
		JPanel manageRegPanel = new JPanel();
		manageRegPanel.setBackground(Color.CYAN);
		contentPane.add(manageRegPanel, "4");
		manageRegPanel.setLayout(null);
		
		manageRegTable = new PanelTable();
		manageRegTable.setBounds(171, 112, 807, 403);
		manageRegPanel.add(manageRegTable);
		
		JTableHeader manageRegTableHeader = manageRegTable.getTableHeader();
		manageRegTableHeader.setBounds(171, 92, 807, 20);
		manageRegPanel.add(manageRegTableHeader);
		
		JButton manageRegSaveButton = new JButton("Save");
		manageRegSaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DB db = new DB();
				for (int i = 0; i < manageRegTable.getRowCount(); i++) {
					try {
						User user = db.getUser(String.valueOf(manageRegTable.getValueAt(i, 0)));
						user.name = String.valueOf(manageRegTable.getValueAt(i, 1));
						user.login = String.valueOf(manageRegTable.getValueAt(i, 2));
						String checkId = db.checkLogin(user.login);
						if (checkId != null && !checkId.equals(user.id)) {
							// TODO show message "There is already a user with login " + login
							return;
						}
						user.email = String.valueOf(manageRegTable.getValueAt(i, 3));
						checkId = db.checkEmail(user.email);
						if (checkId != null && !checkId.equals(user.id)) {
							// TODO show message "There is already a user with email " + email
							return;
						}
						user.dob = LocalDate.parse(String.valueOf(manageRegTable.getValueAt(i, 4)), localDateFormatter);
						user.phone = String.valueOf(manageRegTable.getValueAt(i, 5));
						checkId = db.checkPhone(user.phone);
						if (checkId != null && !checkId.equals(user.id)) {
							// TODO show message "There is already a user with phone " + phone
							return;
						}
						if (!db.setUser(user)) {
							JOptionPane.showMessageDialog(null, "Error trying update the user " + user.login, "message",JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (IOException exc) {
						// TODO show message "We're sorry but the system's servers right now are unavailable"
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "users updated successfully","message",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		manageRegSaveButton.setIcon(new ImageIcon("img\\save.png"));
		manageRegSaveButton.setBounds(694, 540, 117, 63);
		manageRegPanel.add(manageRegSaveButton);
		
		JButton manageRegBackButton = new JButton("Back");
		manageRegBackButton.setIcon(new ImageIcon("img\\Go-back-icon.png"));
		manageRegBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		manageRegBackButton.setBounds(193, 540, 161, 63);
		manageRegPanel.add(manageRegBackButton);
		
		JLabel search_label_1 = new JLabel("Enter name :");
		search_label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		search_label_1.setBounds(22, 112, 103, 30);
		manageRegPanel.add(search_label_1);
		
		manageRegNameEdit = new JTextField();
		manageRegNameEdit.setColumns(10);
		manageRegNameEdit.setBounds(15, 141, 103, 30);
		manageRegPanel.add(manageRegNameEdit);
		
		JComboBox manageRegUserCombo = new JComboBox();
		manageRegUserCombo.setFont(new Font("Tahoma", Font.BOLD, 10));
		manageRegUserCombo.setModel(new DefaultComboBoxModel(new String[] {"User Type", "Manager", "Trainer", "Customer"}));
		manageRegUserCombo.setSelectedIndex(0);
		manageRegUserCombo.setBounds(15, 191, 86, 21);
		manageRegPanel.add(manageRegUserCombo);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillManageRegTable(manageRegNameEdit.getText(), String.valueOf(manageRegUserCombo.getSelectedItem()));
			}
		});
		searchButton.setIcon(new ImageIcon("img\\search.png"));
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		searchButton.setBounds(15, 246, 126, 30);
		manageRegPanel.add(searchButton);
		
		manageRegPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				fillManageRegTable();
				manageRegUserCombo.setSelectedIndex(0);
				manageRegNameEdit.setText("");
			}
		});
		
		JPanel bookPanel = new JPanel();
		bookPanel.setBackground(Color.CYAN);
		contentPane.add(bookPanel, "5");
		bookPanel.setLayout(null);
		
		JButton bookBackButton = new JButton("back");
		bookBackButton.setIcon(new ImageIcon("img\\Go-back-icon.png"));
		bookBackButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		bookBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		bookBackButton.setBounds(121, 576, 105, 23);
		bookPanel.add(bookBackButton);
		
		JLabel bookCustLabel = new JLabel("customer");
		bookCustLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		bookCustLabel.setBounds(50, 184, 123, 20);
		bookPanel.add(bookCustLabel);
		
		JComboBox bookCustCombo = new JComboBox();
		bookCustCombo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bookCustCombo.setBounds(152, 184, 89, 21);
		bookPanel.add(bookCustCombo);
		
		JLabel lblNewLabel_17 = new JLabel("Book training Session");
		lblNewLabel_17.setFont(new Font("Algerian", Font.BOLD, 25));
		lblNewLabel_17.setIcon(new ImageIcon("img\\Schedule-icon.png"));
		lblNewLabel_17.setBounds(293, 36, 551, 96);
		bookPanel.add(lblNewLabel_17);
		
		JLabel bookPriceLabel = new JLabel("RM 200");
		bookPriceLabel.setFont(new Font("Dialog", Font.BOLD, 28));
		bookPriceLabel.setBounds(55, 418, 186, 80);
		bookPanel.add(bookPriceLabel);
		
		JLabel bookTrainerLabel = new JLabel("trainer");
		bookTrainerLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		bookTrainerLabel.setBounds(53, 234, 89, 23);
		bookPanel.add(bookTrainerLabel);
		
		JComboBox bookTrainerCombo = new JComboBox();
		bookTrainerCombo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bookTrainerCombo.setBounds(152, 235, 89, 21);
		bookPanel.add(bookTrainerCombo);
		
		JButton bookButton = new JButton("book");
		bookButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		bookButton.setIcon(new ImageIcon("img\\Sketch-Book-icon.png"));
		bookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				DB db = new DB();
				String id = db.getNextId(DB.Table.SESSIONS);
				String trainer = db.checkLogin(String.valueOf(bookTrainerCombo.getSelectedItem()));
				String customer = db.checkLogin(String.valueOf(bookCustCombo.getSelectedItem()));
				String date = String.valueOf(bookTable.getValueAt(bookTable.getSelectedRow(), bookTable.getSelectedColumn()));
				date = date.split(" available")[0];
				String time = String.valueOf(bookTable.getColumnModel().getColumn(bookTable.getSelectedColumn()).getHeaderValue());
				String price = bookPriceLabel.getText().split("RM ")[1];
				Session session = new Session(new String[] {id, trainer, customer, date, time, price, "false", "", ""});
				if (db.setSession(session)) {
					JOptionPane.showMessageDialog(null, "Session booked successfully for " + date + " " + time,"message",JOptionPane.INFORMATION_MESSAGE);
					fillBookTable(String.valueOf(bookTrainerCombo.getSelectedItem()));
					bookButton.setEnabled(false);
				}
				else
					JOptionPane.showMessageDialog(null, "Sorry, we could not book your session. Try again later","message",JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(null, "Unfortunately, our servers are not available at the moment","message",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		bookButton.setBounds(686, 576, 105, 23);
		bookPanel.add(bookButton);
		
		bookCustCombo.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		    	if (bookCustCombo.getSelectedIndex() > -1 && bookTrainerCombo.getSelectedIndex() > -1)
		    		fillBookTable(String.valueOf(bookTrainerCombo.getSelectedItem()));
		    }
		});
		bookTrainerCombo.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		    	if (bookCustCombo.getSelectedIndex() > -1 && bookTrainerCombo.getSelectedIndex() > -1)
		    		fillBookTable(String.valueOf(bookTrainerCombo.getSelectedItem()));
		    }
		});
		
		bookTable = new PanelTable() {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		bookTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String label = String.valueOf(bookTable.getValueAt(bookTable.getSelectedRow(), bookTable.getSelectedColumn()));
				if (label.contains(" available") && !label.contains(" not available"))
					bookButton.setEnabled(true);
				else
					bookButton.setEnabled(false);
			}
		});
		bookTable.setBounds(303, 156, 807, 403);
		bookPanel.add(bookTable);
		
		JTableHeader bookTableHeader = bookTable.getTableHeader();
		bookTableHeader.setBounds(303, 136, 807, 20);
		bookPanel.add(bookTableHeader);
		
		bookPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				DB db = new DB();
				try {
					Map<String, Customer> customers = db.getCustomers();
					String[] customer_logins = new String[customers.size()];
					for (int i = 0; i < customers.size(); i++) {
						customer_logins[i] = customers.get(customers.keySet().toArray()[i]).login;
					}
					bookCustCombo.setModel(new DefaultComboBoxModel(customer_logins));
					
					Map<String, Trainer> trainers = db.getTrainers();
					String[] trainer_logins = new String[trainers.size()];
					for (int i = 0; i < trainers.size(); i++) {
						trainer_logins[i] = trainers.get(trainers.keySet().toArray()[i]).login;
					}
					bookTrainerCombo.setModel(new DefaultComboBoxModel(trainer_logins));
					fillBookTable(String.valueOf(bookTrainerCombo.getSelectedItem()));
					bookButton.setEnabled(false);
				} catch (IOException exc) {
					
				}
			}
		});
		
		JPanel sessionPanel = new JPanel();
		sessionPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				fillSessionTable();
			}
		});
		sessionPanel.setBackground(Color.CYAN);
		sessionPanel.setLayout(null);
		contentPane.add(sessionPanel, "7");
		
		sessionTable = new PanelTable() {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		sessionTable.setBounds(318, 122, 807, 403);
		sessionPanel.add(sessionTable);
		
		JTableHeader sessionTableHeader = sessionTable.getTableHeader();
		sessionTableHeader.setBounds(318, 102, 807, 20);
		sessionPanel.add(sessionTableHeader);
		
		JButton sessionBackButton = new JButton("Back");
		sessionBackButton.setIcon(new ImageIcon("img\\Go-back-icon.png"));
		sessionBackButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		sessionBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		sessionBackButton.setBounds(202, 578, 122, 23);
		sessionPanel.add(sessionBackButton);
		
		JLabel lblNewLabel_21 = new JLabel("Training Sessions");
		lblNewLabel_21.setFont(new Font("Algerian", Font.PLAIN, 27));
		lblNewLabel_21.setIcon(new ImageIcon("img\\Actions-view-calendar-timeline-icon.png"));
		lblNewLabel_21.setBounds(290, 10, 443, 106);
		sessionPanel.add(lblNewLabel_21);
		
		JLabel lblNewLabel_22 = new JLabel("Start Date");
		lblNewLabel_22.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_22.setBounds(40, 150, 89, 23);
		sessionPanel.add(lblNewLabel_22);
		
		sessionStartDateChooser = new JDateChooser();
		sessionStartDateChooser.setBounds(139, 154, 70, 19);
		sessionPanel.add(sessionStartDateChooser);
		
		JLabel lblNewLabel_23 = new JLabel("End Date");
		lblNewLabel_23.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_23.setBounds(40, 218, 89, 23);
		sessionPanel.add(lblNewLabel_23);
		
		sessionEndDateChooser = new JDateChooser();
		sessionEndDateChooser.setBounds(139, 218, 70, 19);
		sessionPanel.add(sessionEndDateChooser);
		
		JLabel lblNewLabel_24 = new JLabel("Payment Status");
		lblNewLabel_24.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_24.setBounds(29, 291, 114, 23);
		sessionPanel.add(lblNewLabel_24);
		
		sessionPaidCombo = new JComboBox();
		sessionPaidCombo.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		    	if (sessionPaidCombo.getSelectedIndex() > -1)
		    		fillSessionTable();
		    }
		});
		sessionPaidCombo.setFont(new Font("Tahoma", Font.BOLD, 14));
		sessionPaidCombo.setModel(new DefaultComboBoxModel(new String[] {"Any", "Paid", "Unpaid"}));
		sessionPaidCombo.setBounds(167, 292, 79, 23);
		sessionPanel.add(sessionPaidCombo);
		
		sessionStartDateChooser.getDateEditor().addPropertyChangeListener(
			    new PropertyChangeListener() {
			        @Override
			        public void propertyChange(PropertyChangeEvent e) {
			        	fillSessionTable();
			        }
			    }
		    );
		sessionEndDateChooser.getDateEditor().addPropertyChangeListener(
		    new PropertyChangeListener() {
		        @Override
		        public void propertyChange(PropertyChangeEvent e) {
		        	fillSessionTable();
		        }
		    }
	    );
		
		JPanel cust_trainSessPanel = new JPanel();
		cust_trainSessPanel.setBackground(Color.CYAN);
		contentPane.add(cust_trainSessPanel, "10");
		cust_trainSessPanel.setLayout(null);
		

		JButton cust_trainSessFeedbackSubmitButton = new JButton("Submit feedback");
		cust_trainSessFeedbackSubmitButton.setEnabled(false);
		cust_trainSessFeedbackSubmitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DB db = new DB();
				try {
					Session session = db.getSession(
							String.valueOf(
									cust_trainSessTable.getValueAt(
											cust_trainSessTable.getSelectedRow(),
											0
									)
							)
					);
					session.rating = Integer.valueOf(cust_trainSessRadioGroup.getSelection().getActionCommand());
					session.feedback = cust_trainSessFeedbackEdit.getText();
					if (db.setSession(session))
						JOptionPane.showMessageDialog(null, "Thanks for giving us you feedback","message",JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "Could not update the feedback","message",JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(null, "Sorry, our servers are unavailable at the moment","message",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		cust_trainSessFeedbackSubmitButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		cust_trainSessFeedbackSubmitButton.setIcon(new ImageIcon("img/save.png"));
		cust_trainSessFeedbackSubmitButton.setBounds(884, 317, 174, 29);
		cust_trainSessPanel.add(cust_trainSessFeedbackSubmitButton);
		

		cust_trainSessPayButton = new JButton("Pay");
		cust_trainSessPayButton.setIcon(new ImageIcon("img\\payment.png"));
		cust_trainSessPayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "name_85984622353700");
			}
		});
		cust_trainSessPayButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cust_trainSessPayButton.setBounds(623, 495, 175, 39);
		cust_trainSessPanel.add(cust_trainSessPayButton);
		
		feedbackRate1Radio = new JRadioButton("1");
		feedbackRate1Radio.setActionCommand(feedbackRate1Radio.getText());
		feedbackRate1Radio.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
				if (cust_trainSessRadioGroup.getSelection() == null)
					cust_trainSessFeedbackSubmitButton.setEnabled(false);
				else
					cust_trainSessFeedbackSubmitButton.setEnabled(true);
					
			}
		});
		feedbackRate1Radio.setFont(new Font("Tahoma", Font.BOLD, 14));
		feedbackRate1Radio.setBounds(871, 145, 43, 23);
		cust_trainSessPanel.add(feedbackRate1Radio);
		
		feedbackRate2Radio = new JRadioButton("2");
		feedbackRate2Radio.setActionCommand(feedbackRate2Radio.getText());
		feedbackRate2Radio.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
				if (cust_trainSessRadioGroup.getSelection() == null)
					cust_trainSessFeedbackSubmitButton.setEnabled(false);
				else
					cust_trainSessFeedbackSubmitButton.setEnabled(true);
					
			}
		});
		feedbackRate2Radio.setFont(new Font("Tahoma", Font.BOLD, 14));
		feedbackRate2Radio.setBounds(916, 145, 43, 23);
		cust_trainSessPanel.add(feedbackRate2Radio);
		
		feedbackRate3Radio = new JRadioButton("3");
		feedbackRate3Radio.setActionCommand(feedbackRate3Radio.getText());
		feedbackRate3Radio.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
				if (cust_trainSessRadioGroup.getSelection() == null)
					cust_trainSessFeedbackSubmitButton.setEnabled(false);
				else
					cust_trainSessFeedbackSubmitButton.setEnabled(true);
					
			}
		});
		feedbackRate3Radio.setFont(new Font("Tahoma", Font.BOLD, 14));
		feedbackRate3Radio.setBounds(957, 145, 43, 23);
		cust_trainSessPanel.add(feedbackRate3Radio);
		
		feedbackRate4Radio = new JRadioButton("4");
		feedbackRate4Radio.setActionCommand(feedbackRate4Radio.getText());
		feedbackRate4Radio.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
				if (cust_trainSessRadioGroup.getSelection() == null)
					cust_trainSessFeedbackSubmitButton.setEnabled(false);
				else
					cust_trainSessFeedbackSubmitButton.setEnabled(true);
			}
		});
		feedbackRate4Radio.setFont(new Font("Tahoma", Font.BOLD, 14));
		feedbackRate4Radio.setBounds(1002, 145, 43, 23);
		cust_trainSessPanel.add(feedbackRate4Radio);
		
		feedbackRate5Radio = new JRadioButton("5");
		feedbackRate5Radio.setActionCommand(feedbackRate5Radio.getText());
		feedbackRate5Radio.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
				if (cust_trainSessRadioGroup.getSelection() == null)
					cust_trainSessFeedbackSubmitButton.setEnabled(false);
				else
					cust_trainSessFeedbackSubmitButton.setEnabled(true);
					
			}
		});
		feedbackRate5Radio.setFont(new Font("Tahoma", Font.BOLD, 14));
		feedbackRate5Radio.setBounds(1047, 145, 43, 23);
		cust_trainSessPanel.add(feedbackRate5Radio);
		
		cust_trainSessTable = new PanelTable() {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		cust_trainSessTable.setCellSelectionEnabled(true);
		cust_trainSessTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleCust_trainSessTableSelection();
			}
		});
		cust_trainSessTable.setBounds(121, 65, 674, 403);
		cust_trainSessPanel.add(cust_trainSessTable);
		
		
		JTableHeader cust_trainSessTableHeader = cust_trainSessTable.getTableHeader();
		cust_trainSessTableHeader.setBounds(121, 45, 674, 20);
		cust_trainSessPanel.add(cust_trainSessTableHeader);
		
		JLabel cust_trainSesslabel = new JLabel("My training sesseions :");
		cust_trainSesslabel.setFont(new Font("Algerian", Font.BOLD, 24));
		cust_trainSesslabel.setBounds(54, 10, 301, 46);
		cust_trainSessPanel.add(cust_trainSesslabel);
		
		JButton cust_trainSessBackbotton = new JButton("Back");
		cust_trainSessBackbotton.setIcon(new ImageIcon("img\\Go-back-icon.png"));
		cust_trainSessBackbotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		cust_trainSessBackbotton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cust_trainSessBackbotton.setBounds(99, 495, 140, 29);
		cust_trainSessPanel.add(cust_trainSessBackbotton);
		
		cust_trainSessRadioGroup = new ButtonGroup();
		cust_trainSessRadioGroup.add(feedbackRate1Radio);
		cust_trainSessRadioGroup.add(feedbackRate2Radio);
		cust_trainSessRadioGroup.add(feedbackRate3Radio);
		cust_trainSessRadioGroup.add(feedbackRate4Radio);
		cust_trainSessRadioGroup.add(feedbackRate5Radio);
		
		cust_trainSessFeedbackEdit = new JTextField();
		cust_trainSessFeedbackEdit.setHorizontalAlignment(SwingConstants.CENTER);
		cust_trainSessFeedbackEdit.setFont(new Font("Tahoma", Font.BOLD, 14));
		cust_trainSessFeedbackEdit.setText("feedback comment");
		cust_trainSessFeedbackEdit.setColumns(10);
		cust_trainSessFeedbackEdit.setBounds(849, 198, 241, 87);
		cust_trainSessPanel.add(cust_trainSessFeedbackEdit);
		
		JLabel lblNewLabel_26 = new JLabel("Start Date");
		lblNewLabel_26.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_26.setBounds(20, 112, 77, 23);
		cust_trainSessPanel.add(lblNewLabel_26);
		
		cust_trainSessStartDateChooser = new JDateChooser();
		cust_trainSessStartDateChooser.setBounds(20, 145, 70, 19);
		cust_trainSessPanel.add(cust_trainSessStartDateChooser);
		
		JLabel lblNewLabel_26_1 = new JLabel("END Date");
		lblNewLabel_26_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_26_1.setBounds(20, 184, 77, 23);
		cust_trainSessPanel.add(lblNewLabel_26_1);
		
		cust_trainSessEndDateChooser = new JDateChooser();
		cust_trainSessEndDateChooser.setBounds(20, 217, 70, 19);
		cust_trainSessPanel.add(cust_trainSessEndDateChooser);
		
		JLabel lblNewLabel = new JLabel("please select Session from the table\r\n and provide your feedback");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel.setBounds(805, 65, 349, 74);
		cust_trainSessPanel.add(lblNewLabel);
		
		cust_trainSessPaidCombo = new JComboBox();
		cust_trainSessPaidCombo.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		    	if (sessionPaidCombo.getSelectedIndex() > -1)
		    		fillCust_trainSessTable();
		    }
		});
		cust_trainSessPaidCombo.setModel(new DefaultComboBoxModel(new String[] {"Any", "Paid", "Unpaid"}));
		cust_trainSessPaidCombo.setSelectedIndex(0);
		cust_trainSessPaidCombo.setFont(new Font("Tahoma", Font.BOLD, 10));
		cust_trainSessPaidCombo.setBounds(18, 307, 72, 23);
		cust_trainSessPanel.add(cust_trainSessPaidCombo);
		
		JLabel lblNewLabel_24_1 = new JLabel("Payment Status");
		lblNewLabel_24_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_24_1.setBounds(10, 273, 114, 23);
		cust_trainSessPanel.add(lblNewLabel_24_1);
		Image image3 = new ImageIcon("img/payment-creditcard-visa-icon.png").getImage();
		
		cust_trainSessStartDateChooser.getDateEditor().addPropertyChangeListener(
		    new PropertyChangeListener() {
		        @Override
		        public void propertyChange(PropertyChangeEvent e) {
		        	fillCust_trainSessTable();
		        }
		    }
	    );
		cust_trainSessEndDateChooser.getDateEditor().addPropertyChangeListener(
		    new PropertyChangeListener() {
		        @Override
		        public void propertyChange(PropertyChangeEvent e) {
		        	fillCust_trainSessTable();
		        }
		    }
	    );
		
		JPanel custPersonalPanel = new JPanel();
		custPersonalPanel.setBackground(Color.CYAN);
		contentPane.add(custPersonalPanel, "Presonal_Details");
		custPersonalPanel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Presonal Details ");
		lblNewLabel_2.setIcon(new ImageIcon("img\\personal-information-icon.png"));
		lblNewLabel_2.setFont(new Font("Algerian", Font.PLAIN, 25));
		lblNewLabel_2.setBounds(247, 27, 297, 66);
		custPersonalPanel.add(lblNewLabel_2);
		
		personalNameEdit = new JTextField();
		personalNameEdit.setColumns(10);
		personalNameEdit.setBounds(219, 119, 96, 20);
		custPersonalPanel.add(personalNameEdit);
		
		personalEmailEdit = new JTextField();
		personalEmailEdit.setColumns(10);
		personalEmailEdit.setBounds(219, 371, 96, 20);
		custPersonalPanel.add(personalEmailEdit);
		
		JLabel lblNewLabel_3 = new JLabel("name");
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3.setBounds(10, 121, 45, 13);
		custPersonalPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("DOB");
		lblNewLabel_3_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3_1.setBounds(10, 316, 45, 13);
		custPersonalPanel.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("email");
		lblNewLabel_3_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3_2.setBounds(10, 369, 61, 20);
		custPersonalPanel.add(lblNewLabel_3_2);
		
		JButton btnNewButton_2_1 = new JButton("Back");
		btnNewButton_2_1.setIcon(new ImageIcon("img\\Go-back-icon.png"));
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		btnNewButton_2_1.setBounds(140, 531, 109, 36);
		custPersonalPanel.add(btnNewButton_2_1);
		
		JDateChooser personalDateChooser = new JDateChooser();
		personalDateChooser.setBounds(219, 304, 96, 19);
		custPersonalPanel.add(personalDateChooser);
		

		JButton btnNewButton_2 = new JButton("update");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DB db = new DB();
				try {
					if (!user.password.equals(String.valueOf(personalOldPassEdit.getPassword()))) {
						JOptionPane.showMessageDialog(null, "Your old password is incorrect","message",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (db.checkEmail(personalEmailEdit.getText()) != null && !user.id.equals(db.checkEmail(personalEmailEdit.getText()))) {
						JOptionPane.showMessageDialog(null, "The user with such email already exists","message",JOptionPane.INFORMATION_MESSAGE); 
						return;
					}
					if (db.checkPhone(personalPhoneEdit.getText()) != null && !user.id.equals(db.checkPhone(personalPhoneEdit.getText()))) {
						JOptionPane.showMessageDialog(null, "The user with such phone number already exists","message",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					String oldName = user.name;
					String oldPassword = user.password;
					LocalDate oldDob = user.dob;
					String oldEmail = user.email;
					String oldPhone = user.phone;
					user.name = personalNameEdit.getText();
					user.password = String.valueOf(personalNewPassEdit.getPassword());
					if (user.password.equals("")) user.password = oldPassword;
					user.dob = personalDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					user.email = personalEmailEdit.getText();
					user.phone = personalPhoneEdit.getText();
						
					if (db.setUser(user)) {
						JOptionPane.showMessageDialog(null, "The details were successfully updated","message",JOptionPane.INFORMATION_MESSAGE);
					} else {
						user.name = oldName;
						user.password = oldPassword;
						user.dob = oldDob;
						user.email = oldEmail;
						user.phone = oldPhone;
						JOptionPane.showMessageDialog(null, "We're sorry but the system's servers right now are unavailable","message",JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(null, "We're sorry but the system's servers right now are unavailable","message",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnNewButton_2.setIcon(new ImageIcon("img\\update & delete member.png"));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_2.setBounds(470, 529, 160, 36);
		custPersonalPanel.add(btnNewButton_2);
		
		JLabel lblNewLabel_3_3 = new JLabel("Old password");
		lblNewLabel_3_3.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3_3.setBounds(10, 192, 167, 13);
		custPersonalPanel.add(lblNewLabel_3_3);
		
		JLabel lblNewLabel_3_4 = new JLabel("New password");
		lblNewLabel_3_4.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3_4.setBounds(10, 251, 135, 13);
		custPersonalPanel.add(lblNewLabel_3_4);
		
		personalOldPassEdit = new JPasswordField();
		personalOldPassEdit.setBounds(219, 190, 96, 20);
		custPersonalPanel.add(personalOldPassEdit);
		
		personalNewPassEdit = new JPasswordField();
		personalNewPassEdit.setBounds(219, 249, 96, 20);
		custPersonalPanel.add(personalNewPassEdit);
		
		JLabel lblNewLabel_3_2_1 = new JLabel("phone");
		lblNewLabel_3_2_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3_2_1.setBounds(10, 438, 61, 20);
		custPersonalPanel.add(lblNewLabel_3_2_1);
		
		personalPhoneEdit = new JTextField();
		personalPhoneEdit.setColumns(10);
		personalPhoneEdit.setBounds(219, 440, 96, 20);
		custPersonalPanel.add(personalPhoneEdit);
		
		JPanel PayPanel = new JPanel();
		contentPane.add(PayPanel, "name_85984622353700");
		PayPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Payment Summary");
		lblNewLabel_1.setFont(new Font("Algerian", Font.BOLD, 37));
		lblNewLabel_1.setBounds(58, 26, 500, 117);
		PayPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_4 = new JLabel("Card type");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setBounds(30, 149, 83, 30);
		PayPanel.add(lblNewLabel_4);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"VISA", "MasterCard", "PayPal"}));
		comboBox.setBounds(156, 156, 83, 21);
		PayPanel.add(comboBox);
		
		JLabel lblNewLabel_15 = new JLabel("Card number");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_15.setBounds(30, 208, 111, 21);
		PayPanel.add(lblNewLabel_15);
		
		textField_1 = new JTextField();
		textField_1.setBounds(143, 211, 209, 19);
		PayPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_15_1 = new JLabel("Cardholder Name");
		lblNewLabel_15_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_15_1.setBounds(30, 361, 143, 21);
		PayPanel.add(lblNewLabel_15_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(199, 364, 153, 19);
		PayPanel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_15_1_1 = new JLabel("Expiration Date");
		lblNewLabel_15_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_15_1_1.setBounds(30, 263, 143, 21);
		PayPanel.add(lblNewLabel_15_1_1);
		
		JDateChooser dateChooser_5 = new JDateChooser();
		dateChooser_5.setBounds(181, 263, 103, 19);
		PayPanel.add(dateChooser_5);
		
		JLabel lblNewLabel_25 = new JLabel("Security Code");
		lblNewLabel_25.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_25.setBounds(30, 314, 100, 21);
		PayPanel.add(lblNewLabel_25);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordField_1.setBounds(154, 317, 103, 19);
		PayPanel.add(passwordField_1);
		
		JLabel lblNewLabel_30 = new JLabel("TOTAL:");
		lblNewLabel_30.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_30.setBounds(242, 424, 61, 39);
		PayPanel.add(lblNewLabel_30);
		
		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		textField_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		textField_3.setText("200");
		textField_3.setBounds(321, 424, 69, 39);
		PayPanel.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton = new JButton("PAY");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DB db = new DB();
					Session session = db.getSession(String.valueOf(cust_trainSessTable.getValueAt(cust_trainSessTable.getSelectedRow(), 0)));
					session.paid = true;
					if (db.setSession(session))
						JOptionPane.showMessageDialog(null, "payment successful","message",JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "Could not process the payment","message",JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(null, "Unfortunately our servers are not available","message",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		custPersonalPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				personalNameEdit.setText(user.name);
				personalDateChooser.setDate(Date.from(user.dob.atStartOfDay(ZoneId.systemDefault()).toInstant()));
				personalEmailEdit.setText(user.email);
				personalPhoneEdit.setText(user.phone);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setIcon(new ImageIcon("img\\payment.png"));
		btnNewButton.setBounds(358, 504, 153, 30);
		PayPanel.add(btnNewButton);
		
		JLabel lblNewLabel_31 = new JLabel("");
		lblNewLabel_31.setIcon(new ImageIcon("img\\payment-creditcard-visa-icon (1).png"));
		lblNewLabel_31.setBounds(505, 170, 228, 189);
		PayPanel.add(lblNewLabel_31);
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cust_trainSessStartDateChooser.setDate(null);
				cust_trainSessEndDateChooser.setDate(null);
				cust_trainSessPaidCombo.setSelectedIndex(0);
				cl.show(contentPane,"10");
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.setIcon(new ImageIcon("img\\Go-back-icon.png"));
		btnNewButton_1.setBounds(58, 511, 115, 30);
		PayPanel.add(btnNewButton_1);
	}
}
