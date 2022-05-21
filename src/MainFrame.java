import java.awt.EventQueue;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;

import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import java.awt.Color;
import javax.swing.JPasswordField;
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

	private JPanel contentPane;
	private JTextField authLoginEdit;
	private JTextField regNameEdit;
	private JTextField regPhoneEdit;
	private JTextField regEmailEdit;
	private JTextField regLoginEdit;
	private PanelTable manageRegTable;
	private PanelTable sessionTable;
	private PanelTable custSessTable;
	private PanelTable bookTable;
	private JTextField personalNameEdit;
	private JTextField personalEmailEdit;
	private JPasswordField authPasswordEdit;
	private JTextField manageRegNameEdit;
	private JTextField custSessFeedbackEdit;
	private JPasswordField regPasswordEdit;
	protected Window btnNewButton_7;
	private JTextField payCardNumEdit;
	private JTextField payCardholderEdit;
	private JPasswordField paySecretEdit;
	private JTextField payTotalEdit;
	private JDateChooser sessionStartDateChooser;
	private JDateChooser sessionEndDateChooser;
	private JComboBox sessionPaidCombo;
	private JDateChooser custSessStartDateChooser;
	private JDateChooser custSessEndDateChooser;
	private JComboBox custSessPaidCombo;
	private ButtonGroup custSessRadioGroup;
	private JRadioButton custSessFeedbackRate1Radio;
	private JRadioButton custSessFeedbackRate2Radio;
	private JRadioButton custSessFeedbackRate3Radio;
	private JRadioButton custSessFeedbackRate4Radio;
	private JRadioButton custSessFeedbackRate5Radio;
	private JButton custSessPayButton;
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
			JOptionPane.showMessageDialog(null, "Could not get the users data from the database","warning",JOptionPane.INFORMATION_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "Could not get the sessions data from the database","warning",JOptionPane.INFORMATION_MESSAGE);
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
			bookTable.setModel(new DefaultTableModel(tableVals, new String[] {"09:00", "11:00", "01:00", "03:00", "05:00"}));
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
	
	private void fillcustSessTable() {
		fillSessionTable(
			custSessTable,
			custSessStartDateChooser.getDate(),
			custSessEndDateChooser.getDate(),
			String.valueOf(custSessPaidCombo.getSelectedItem()),
			"customer",
			new int[] {0, 7, 8}
		);
		if (user != null) handlecustSessTableSelection();
	}
	
	private void handlecustSessTableSelection() {
		int row = custSessTable.getSelectedRow();
		if (row == -1) {
			custSessFeedbackEdit.setText("feedback comment");
			custSessRadioGroup.clearSelection();
			custSessPayButton.setEnabled(false);
			return;
		}
		switch (String.valueOf(custSessTable.getValueAt(row, 7))) {
		case "1":
			custSessFeedbackRate1Radio.doClick();
			break;
		case "2":
			custSessFeedbackRate2Radio.doClick();
			break;
		case "3":
			custSessFeedbackRate3Radio.doClick();
			break;
		case "4":
			custSessFeedbackRate4Radio.doClick();
			break;
		case "5":
			custSessFeedbackRate5Radio.doClick();
			break;
		default:
		}
		custSessFeedbackEdit.setText(String.valueOf(custSessTable.getValueAt(row, 8)));
		if (custSessTable.getValueAt(row, 6).equals("false"))
			custSessPayButton.setEnabled(true);
		else
			custSessPayButton.setEnabled(false);
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
		Image image1 = new ImageIcon("APU logo.jpg").getImage();
		
		JPanel mainMenuPanel = new JPanel();

		mainMenuPanel.setBackground(Color.CYAN);
		contentPane.add(mainMenuPanel, "2");
		mainMenuPanel.setLayout(null);
		
		JButton regPanelButton = new JButton("Register");
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
		
		JButton sessionPanelButton = new JButton("Check training session");
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
		
		JButton custSessPanelButton = new JButton("View own training session");
		custSessPanelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		custSessPanelButton.setIcon(new ImageIcon("img\\Actions-contact-date-icon.png"));
		custSessPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				custSessStartDateChooser.setDate(null);
				custSessEndDateChooser.setDate(null);
				custSessPaidCombo.setSelectedIndex(0);
				cl.show(contentPane,"10");
			}
		});
		custSessPanelButton.setBounds(3, 23, 271, 51);
		mainMenuPanel.add(custSessPanelButton);
		
		JButton custPersonalButton = new JButton("Personal Details ");
		custPersonalButton.setIcon(new ImageIcon("img\\Resume-icon.png"));
		custPersonalButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		custPersonalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane,"Presonal_Details");
			}
		});
		custPersonalButton.setBounds(296, 23, 244, 51);
		mainMenuPanel.add(custPersonalButton);
		
		JLabel mainMenuLabel = new JLabel("WELCOME");
		mainMenuLabel.setIcon(new ImageIcon("img\\welcome-icon.png"));
		mainMenuLabel.setFont(new Font("Algerian", Font.PLAIN, 52));
		mainMenuLabel.setBounds(409, 314, 449, 191);
		mainMenuPanel.add(mainMenuLabel);
		
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
					custSessPanelButton.setVisible(false);
					custPersonalButton.setVisible(false);
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
				filter.put("login", authLoginEdit.getText());
				filter.put("password", new String(authPasswordEdit.getPassword()));
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
		
		authLoginEdit = new JTextField();
		authLoginEdit.setBounds(833, 288, 225, 33);
		authPanel.add(authLoginEdit);
		authLoginEdit.setColumns(10);
		
		JLabel authLoginLabel = new JLabel("Username");
		authLoginLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		authLoginLabel.setBounds(701, 296, 122, 25);
		authPanel.add(authLoginLabel);
		
		JLabel authPasswordLabel = new JLabel("password");
		authPasswordLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		authPasswordLabel.setBounds(701, 358, 122, 33);
		authPanel.add(authPasswordLabel);
		Image image = new ImageIcon("img/gym_logo.png").getImage();
		
		authPasswordEdit = new JPasswordField();
		authPasswordEdit.setBounds(833, 361, 225, 35);
		authPanel.add(authPasswordEdit);
		
		JLabel authImgLabel = new JLabel("");
		authImgLabel.setIcon(new ImageIcon("img\\APU logo (1).jpg"));
		authImgLabel.setBounds(30, 201, 500, 290);
		authPanel.add(authImgLabel);
		
		JLabel authBackgroundLabel = new JLabel("");
		authBackgroundLabel.setIcon(new ImageIcon("img\\login background.PNG"));
		authBackgroundLabel.setBounds(10, 0, 1175, 657);
		authPanel.add(authBackgroundLabel);
		
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
	
		JComboBox regUserTypeCombo = new JComboBox();
		regUserTypeCombo.setModel(new DefaultComboBoxModel(new String[] {"Manager", "Trainer", "Customer"}));
		regUserTypeCombo.setFont(new Font("Tahoma", Font.BOLD, 10));
		regUserTypeCombo.setBounds(137, 133, 86, 21);
		registerPanel.add(regUserTypeCombo);
		
		JLabel regNameLabel = new JLabel("name");
		regNameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		regNameLabel.setBounds(137, 197, 64, 13);
		registerPanel.add(regNameLabel);
		
		JLabel regDateLabel = new JLabel("DOB");
		regDateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		regDateLabel.setBounds(137, 236, 66, 18);
		registerPanel.add(regDateLabel);
		
		JLabel regPhoneLabel = new JLabel("phone");
		regPhoneLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		regPhoneLabel.setBounds(139, 289, 64, 19);
		registerPanel.add(regPhoneLabel);
		
		JLabel regEmailLabel = new JLabel("email");
		regEmailLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		regEmailLabel.setBounds(139, 355, 72, 13);
		registerPanel.add(regEmailLabel);
		
		JLabel regGenderLabel = new JLabel("gender");
		regGenderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		regGenderLabel.setBounds(139, 415, 72, 19);
		registerPanel.add(regGenderLabel);
		
		JLabel regLoginLabel = new JLabel("login");
		regLoginLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		regLoginLabel.setBounds(390, 126, 76, 31);
		registerPanel.add(regLoginLabel);
		
		JLabel regPasswordLabel = new JLabel("password");
		regPasswordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		regPasswordLabel.setBounds(390, 187, 76, 29);
		registerPanel.add(regPasswordLabel);
		
		JLabel registerLabel = new JLabel("New Member");
		registerLabel.setForeground(Color.BLUE);
		registerLabel.setFont(new Font("Aldhabi", Font.BOLD, 48));
		registerLabel.setIcon(new ImageIcon("img\\new member.png"));
		registerLabel.setBounds(323, 28, 298, 62);
		registerPanel.add(registerLabel);
		
		JComboBox regGenderCombo = new JComboBox();
		regGenderCombo.setFont(new Font("Tahoma", Font.BOLD, 10));
		regGenderCombo.setModel(new DefaultComboBoxModel(new String[] {"M", "F"}));
		regGenderCombo.setBounds(233, 416, 45, 21);
		registerPanel.add(regGenderCombo);
		
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
				String selected = String.valueOf(regUserTypeCombo.getSelectedItem());
				if (selected.equals("Trainer"))
					userType = UserType.TRAINER;
				else if (selected.equals("Manager"))
					userType = UserType.MANAGER;
				else
					userType = UserType.CUSTOMER;
				DB db = new DB();
				try {
					String id = db.getNextId(DB.Table.USERS);
					String name = regNameEdit.getText();
					String login = regLoginEdit.getText();
					if (db.checkLogin(login) != null) {
						JOptionPane.showMessageDialog(null, "The user with such name already exists","warning",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					String password = new String(regPasswordEdit.getPassword());
					String email = regEmailEdit.getText();
					if (db.checkEmail(email) != null) {
						JOptionPane.showMessageDialog(null, "The user with such email already exists","warning",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					String dob = dateFormatter.format(regDateChooser.getDate());
					String phone = regPhoneEdit.getText();
					if (db.checkPhone(phone) != null) {
						JOptionPane.showMessageDialog(null, "The user with such phone already exists","warning",JOptionPane.INFORMATION_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "We're sorry but the system's servers right now are unavailable","warning",JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(null, "We're sorry but the system's servers right now are unavailable","warning",JOptionPane.INFORMATION_MESSAGE);
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
							JOptionPane.showMessageDialog(null, "There is already a user with login " + user.login,"warning",JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						user.email = String.valueOf(manageRegTable.getValueAt(i, 3));
						checkId = db.checkEmail(user.email);
						if (checkId != null && !checkId.equals(user.id)) {
							JOptionPane.showMessageDialog(null, "There is already a user with email " + user.email,"warning",JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						user.dob = LocalDate.parse(String.valueOf(manageRegTable.getValueAt(i, 4)), localDateFormatter);
						user.phone = String.valueOf(manageRegTable.getValueAt(i, 5));
						checkId = db.checkPhone(user.phone);
						if (checkId != null && !checkId.equals(user.id)) {
							JOptionPane.showMessageDialog(null, "There is already a user with phone " + user.phone,"warning",JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (!db.setUser(user)) {
							JOptionPane.showMessageDialog(null, "Error trying update the user " + user.login, "warning",JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (IOException exc) {
						JOptionPane.showMessageDialog(null, "We're sorry but the system's servers right now are unavailable","warning",JOptionPane.INFORMATION_MESSAGE);
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
		
		JLabel manageRegNameLabel = new JLabel("Enter name :");
		manageRegNameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		manageRegNameLabel.setBounds(22, 112, 103, 30);
		manageRegPanel.add(manageRegNameLabel);
		
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
		
		JButton manageRegSearchButton = new JButton("Search");
		manageRegSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillManageRegTable(manageRegNameEdit.getText(), String.valueOf(manageRegUserCombo.getSelectedItem()));
			}
		});
		manageRegSearchButton.setIcon(new ImageIcon("img\\search.png"));
		manageRegSearchButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		manageRegSearchButton.setBounds(15, 246, 126, 30);
		manageRegPanel.add(manageRegSearchButton);
		
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
		
		JLabel bookLabel = new JLabel("Book training Session");
		bookLabel.setFont(new Font("Algerian", Font.BOLD, 25));
		bookLabel.setIcon(new ImageIcon("img\\Schedule-icon.png"));
		bookLabel.setBounds(293, 36, 551, 96);
		bookPanel.add(bookLabel);
		
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
					JOptionPane.showMessageDialog(null, "Sorry, we could not book your session. Try again later","warning",JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(null, "Unfortunately, our servers are not available at the moment","warning",JOptionPane.INFORMATION_MESSAGE);
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
		
		JLabel sessionLabel = new JLabel("Training Sessions");
		sessionLabel.setFont(new Font("Algerian", Font.PLAIN, 27));
		sessionLabel.setIcon(new ImageIcon("img\\Actions-view-calendar-timeline-icon.png"));
		sessionLabel.setBounds(290, 10, 443, 106);
		sessionPanel.add(sessionLabel);
		
		JLabel sessionStartDateLabel = new JLabel("Start Date");
		sessionStartDateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		sessionStartDateLabel.setBounds(40, 150, 89, 23);
		sessionPanel.add(sessionStartDateLabel);
		
		sessionStartDateChooser = new JDateChooser();
		sessionStartDateChooser.setBounds(139, 154, 70, 19);
		sessionPanel.add(sessionStartDateChooser);
		
		JLabel sessionEndDateLabel = new JLabel("End Date");
		sessionEndDateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		sessionEndDateLabel.setBounds(40, 218, 89, 23);
		sessionPanel.add(sessionEndDateLabel);
		
		sessionEndDateChooser = new JDateChooser();
		sessionEndDateChooser.setBounds(139, 218, 70, 19);
		sessionPanel.add(sessionEndDateChooser);
		
		JLabel sessionPaidLabel = new JLabel("Payment Status");
		sessionPaidLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		sessionPaidLabel.setBounds(29, 291, 114, 23);
		sessionPanel.add(sessionPaidLabel);
		
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
		
		JPanel custSessPanel = new JPanel();
		custSessPanel.setBackground(Color.CYAN);
		contentPane.add(custSessPanel, "10");
		custSessPanel.setLayout(null);
		

		JButton custSessFeedbackSubmitButton = new JButton("Submit feedback");
		custSessFeedbackSubmitButton.setEnabled(false);
		custSessFeedbackSubmitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DB db = new DB();
				try {
					Session session = db.getSession(
							String.valueOf(
									custSessTable.getValueAt(
											custSessTable.getSelectedRow(),
											0
									)
							)
					);
					session.rating = Integer.valueOf(custSessRadioGroup.getSelection().getActionCommand());
					session.feedback = custSessFeedbackEdit.getText();
					if (db.setSession(session))
						JOptionPane.showMessageDialog(null, "Thanks for giving us you feedback","message",JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "Could not update the feedback","warning",JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(null, "Sorry, our servers are unavailable at the moment","warning",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		custSessFeedbackSubmitButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		custSessFeedbackSubmitButton.setIcon(new ImageIcon("img/save.png"));
		custSessFeedbackSubmitButton.setBounds(884, 317, 174, 29);
		custSessPanel.add(custSessFeedbackSubmitButton);
		

		custSessPayButton = new JButton("Pay");
		custSessPayButton.setIcon(new ImageIcon("img\\payment.png"));
		custSessPayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "name_85984622353700");
			}
		});
		custSessPayButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		custSessPayButton.setBounds(623, 495, 175, 39);
		custSessPanel.add(custSessPayButton);
		
		custSessFeedbackRate1Radio = new JRadioButton("1");
		custSessFeedbackRate1Radio.setActionCommand(custSessFeedbackRate1Radio.getText());
		custSessFeedbackRate1Radio.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
				if (custSessRadioGroup.getSelection() == null)
					custSessFeedbackSubmitButton.setEnabled(false);
				else
					custSessFeedbackSubmitButton.setEnabled(true);
					
			}
		});
		custSessFeedbackRate1Radio.setFont(new Font("Tahoma", Font.BOLD, 14));
		custSessFeedbackRate1Radio.setBounds(871, 145, 43, 23);
		custSessPanel.add(custSessFeedbackRate1Radio);
		
		custSessFeedbackRate2Radio = new JRadioButton("2");
		custSessFeedbackRate2Radio.setActionCommand(custSessFeedbackRate2Radio.getText());
		custSessFeedbackRate2Radio.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
				if (custSessRadioGroup.getSelection() == null)
					custSessFeedbackSubmitButton.setEnabled(false);
				else
					custSessFeedbackSubmitButton.setEnabled(true);
					
			}
		});
		custSessFeedbackRate2Radio.setFont(new Font("Tahoma", Font.BOLD, 14));
		custSessFeedbackRate2Radio.setBounds(916, 145, 43, 23);
		custSessPanel.add(custSessFeedbackRate2Radio);
		
		custSessFeedbackRate3Radio = new JRadioButton("3");
		custSessFeedbackRate3Radio.setActionCommand(custSessFeedbackRate3Radio.getText());
		custSessFeedbackRate3Radio.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
				if (custSessRadioGroup.getSelection() == null)
					custSessFeedbackSubmitButton.setEnabled(false);
				else
					custSessFeedbackSubmitButton.setEnabled(true);
					
			}
		});
		custSessFeedbackRate3Radio.setFont(new Font("Tahoma", Font.BOLD, 14));
		custSessFeedbackRate3Radio.setBounds(957, 145, 43, 23);
		custSessPanel.add(custSessFeedbackRate3Radio);
		
		custSessFeedbackRate4Radio = new JRadioButton("4");
		custSessFeedbackRate4Radio.setActionCommand(custSessFeedbackRate4Radio.getText());
		custSessFeedbackRate4Radio.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
				if (custSessRadioGroup.getSelection() == null)
					custSessFeedbackSubmitButton.setEnabled(false);
				else
					custSessFeedbackSubmitButton.setEnabled(true);
			}
		});
		custSessFeedbackRate4Radio.setFont(new Font("Tahoma", Font.BOLD, 14));
		custSessFeedbackRate4Radio.setBounds(1002, 145, 43, 23);
		custSessPanel.add(custSessFeedbackRate4Radio);
		
		custSessFeedbackRate5Radio = new JRadioButton("5");
		custSessFeedbackRate5Radio.setActionCommand(custSessFeedbackRate5Radio.getText());
		custSessFeedbackRate5Radio.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
				if (custSessRadioGroup.getSelection() == null)
					custSessFeedbackSubmitButton.setEnabled(false);
				else
					custSessFeedbackSubmitButton.setEnabled(true);
					
			}
		});
		custSessFeedbackRate5Radio.setFont(new Font("Tahoma", Font.BOLD, 14));
		custSessFeedbackRate5Radio.setBounds(1047, 145, 43, 23);
		custSessPanel.add(custSessFeedbackRate5Radio);
		
		custSessTable = new PanelTable() {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		custSessTable.setCellSelectionEnabled(true);
		custSessTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handlecustSessTableSelection();
			}
		});
		custSessTable.setBounds(121, 65, 674, 403);
		custSessPanel.add(custSessTable);
		
		
		JTableHeader custSessTableHeader = custSessTable.getTableHeader();
		custSessTableHeader.setBounds(121, 45, 674, 20);
		custSessPanel.add(custSessTableHeader);
		
		JLabel custSessLabel = new JLabel("My training sessions :");
		custSessLabel.setFont(new Font("Algerian", Font.BOLD, 24));
		custSessLabel.setBounds(54, 10, 301, 46);
		custSessPanel.add(custSessLabel);
		
		JButton custSessBackbotton = new JButton("Back");
		custSessBackbotton.setIcon(new ImageIcon("img\\Go-back-icon.png"));
		custSessBackbotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		custSessBackbotton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		custSessBackbotton.setBounds(99, 495, 140, 29);
		custSessPanel.add(custSessBackbotton);
		
		custSessRadioGroup = new ButtonGroup();
		custSessRadioGroup.add(custSessFeedbackRate1Radio);
		custSessRadioGroup.add(custSessFeedbackRate2Radio);
		custSessRadioGroup.add(custSessFeedbackRate3Radio);
		custSessRadioGroup.add(custSessFeedbackRate4Radio);
		custSessRadioGroup.add(custSessFeedbackRate5Radio);
		
		custSessFeedbackEdit = new JTextField();
		custSessFeedbackEdit.setHorizontalAlignment(SwingConstants.CENTER);
		custSessFeedbackEdit.setFont(new Font("Tahoma", Font.BOLD, 14));
		custSessFeedbackEdit.setText("feedback comment");
		custSessFeedbackEdit.setColumns(10);
		custSessFeedbackEdit.setBounds(849, 198, 241, 87);
		custSessPanel.add(custSessFeedbackEdit);
		
		JLabel custSessStartDateLabel = new JLabel("Start Date");
		custSessStartDateLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		custSessStartDateLabel.setBounds(20, 112, 77, 23);
		custSessPanel.add(custSessStartDateLabel);
		
		custSessStartDateChooser = new JDateChooser();
		custSessStartDateChooser.setBounds(20, 145, 70, 19);
		custSessPanel.add(custSessStartDateChooser);
		
		JLabel custSessEndDateLabel = new JLabel("End Date");
		custSessEndDateLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		custSessEndDateLabel.setBounds(20, 184, 77, 23);
		custSessPanel.add(custSessEndDateLabel);
		
		custSessEndDateChooser = new JDateChooser();
		custSessEndDateChooser.setBounds(20, 217, 70, 19);
		custSessPanel.add(custSessEndDateChooser);
		
		JLabel custSessFeedbackLabel = new JLabel("please select Session from the table\r\n and provide your feedback");
		custSessFeedbackLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		custSessFeedbackLabel.setBounds(805, 65, 349, 74);
		custSessPanel.add(custSessFeedbackLabel);
		
		custSessPaidCombo = new JComboBox();
		custSessPaidCombo.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		    	if (sessionPaidCombo.getSelectedIndex() > -1)
		    		fillcustSessTable();
		    }
		});
		custSessPaidCombo.setModel(new DefaultComboBoxModel(new String[] {"Any", "Paid", "Unpaid"}));
		custSessPaidCombo.setSelectedIndex(0);
		custSessPaidCombo.setFont(new Font("Tahoma", Font.BOLD, 10));
		custSessPaidCombo.setBounds(18, 307, 72, 23);
		custSessPanel.add(custSessPaidCombo);
		
		JLabel custSessPaidLabel = new JLabel("Payment Status");
		custSessPaidLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		custSessPaidLabel.setBounds(10, 273, 114, 23);
		custSessPanel.add(custSessPaidLabel);
		Image image3 = new ImageIcon("img/payment-creditcard-visa-icon.png").getImage();
		
		custSessStartDateChooser.getDateEditor().addPropertyChangeListener(
		    new PropertyChangeListener() {
		        @Override
		        public void propertyChange(PropertyChangeEvent e) {
		        	fillcustSessTable();
		        }
		    }
	    );
		custSessEndDateChooser.getDateEditor().addPropertyChangeListener(
		    new PropertyChangeListener() {
		        @Override
		        public void propertyChange(PropertyChangeEvent e) {
		        	fillcustSessTable();
		        }
		    }
	    );
		
		JPanel personalPanel = new JPanel();
		personalPanel.setBackground(Color.CYAN);
		contentPane.add(personalPanel, "Presonal_Details");
		personalPanel.setLayout(null);
		
		JLabel personalLabel = new JLabel("Personal Details ");
		personalLabel.setIcon(new ImageIcon("img\\personal-information-icon.png"));
		personalLabel.setFont(new Font("Algerian", Font.PLAIN, 25));
		personalLabel.setBounds(247, 27, 297, 66);
		personalPanel.add(personalLabel);
		
		personalNameEdit = new JTextField();
		personalNameEdit.setColumns(10);
		personalNameEdit.setBounds(219, 119, 96, 20);
		personalPanel.add(personalNameEdit);
		
		personalEmailEdit = new JTextField();
		personalEmailEdit.setColumns(10);
		personalEmailEdit.setBounds(219, 371, 96, 20);
		personalPanel.add(personalEmailEdit);
		
		JLabel personalNameLabel = new JLabel("name");
		personalNameLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		personalNameLabel.setBounds(10, 121, 45, 13);
		personalPanel.add(personalNameLabel);
		
		JLabel personalDateLabel = new JLabel("DOB");
		personalDateLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		personalDateLabel.setBounds(10, 316, 45, 13);
		personalPanel.add(personalDateLabel);
		
		JLabel personalEmailLabel = new JLabel("email");
		personalEmailLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		personalEmailLabel.setBounds(10, 369, 61, 20);
		personalPanel.add(personalEmailLabel);
		
		JButton personalBackButton = new JButton("Back");
		personalBackButton.setIcon(new ImageIcon("img\\Go-back-icon.png"));
		personalBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		personalBackButton.setBounds(140, 531, 109, 36);
		personalPanel.add(personalBackButton);
		
		JDateChooser personalDateChooser = new JDateChooser();
		personalDateChooser.setBounds(219, 304, 96, 19);
		personalPanel.add(personalDateChooser);
		

		JButton personalUpdateButton = new JButton("update");
		personalUpdateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DB db = new DB();
				try {
					if (!user.password.equals(String.valueOf(personalOldPassEdit.getPassword()))) {
						JOptionPane.showMessageDialog(null, "Your old password is incorrect","warning",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (db.checkEmail(personalEmailEdit.getText()) != null && !user.id.equals(db.checkEmail(personalEmailEdit.getText()))) {
						JOptionPane.showMessageDialog(null, "The user with such email already exists","warning",JOptionPane.INFORMATION_MESSAGE); 
						return;
					}
					if (db.checkPhone(personalPhoneEdit.getText()) != null && !user.id.equals(db.checkPhone(personalPhoneEdit.getText()))) {
						JOptionPane.showMessageDialog(null, "The user with such phone number already exists","warning",JOptionPane.INFORMATION_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "We're sorry but the system's servers right now are unavailable","warning",JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(null, "We're sorry but the system's servers right now are unavailable","warning",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		personalUpdateButton.setIcon(new ImageIcon("img\\update & delete member.png"));
		personalUpdateButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		personalUpdateButton.setBounds(470, 529, 160, 36);
		personalPanel.add(personalUpdateButton);
		
		JLabel personalOldPassLabel = new JLabel("Old password");
		personalOldPassLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		personalOldPassLabel.setBounds(10, 192, 167, 13);
		personalPanel.add(personalOldPassLabel);
		
		JLabel personalNewPassLabel = new JLabel("New password");
		personalNewPassLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		personalNewPassLabel.setBounds(10, 251, 135, 13);
		personalPanel.add(personalNewPassLabel);
		
		personalOldPassEdit = new JPasswordField();
		personalOldPassEdit.setBounds(219, 190, 96, 20);
		personalPanel.add(personalOldPassEdit);
		
		personalNewPassEdit = new JPasswordField();
		personalNewPassEdit.setBounds(219, 249, 96, 20);
		personalPanel.add(personalNewPassEdit);
		
		JLabel personalPhoneLabel = new JLabel("phone");
		personalPhoneLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		personalPhoneLabel.setBounds(10, 438, 61, 20);
		personalPanel.add(personalPhoneLabel);
		
		personalPhoneEdit = new JTextField();
		personalPhoneEdit.setColumns(10);
		personalPhoneEdit.setBounds(219, 440, 96, 20);
		personalPanel.add(personalPhoneEdit);
		
		JPanel payPanel = new JPanel();
		contentPane.add(payPanel, "name_85984622353700");
		payPanel.setLayout(null);
		
		JLabel payLabel = new JLabel("Payment Summary");
		payLabel.setFont(new Font("Algerian", Font.BOLD, 37));
		payLabel.setBounds(58, 26, 500, 117);
		payPanel.add(payLabel);
		
		JLabel payCardTypeLabel = new JLabel("Card type");
		payCardTypeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		payCardTypeLabel.setBounds(30, 149, 83, 30);
		payPanel.add(payCardTypeLabel);
		
		JComboBox payCardTypeCombo = new JComboBox();
		payCardTypeCombo.setModel(new DefaultComboBoxModel(new String[] {"VISA", "MasterCard", "PayPal"}));
		payCardTypeCombo.setBounds(156, 156, 83, 21);
		payPanel.add(payCardTypeCombo);
		
		JLabel payCardNumLabel = new JLabel("Card number");
		payCardNumLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		payCardNumLabel.setBounds(30, 208, 111, 21);
		payPanel.add(payCardNumLabel);
		
		payCardNumEdit = new JTextField();
		payCardNumEdit.setBounds(143, 211, 209, 19);
		payPanel.add(payCardNumEdit);
		payCardNumEdit.setColumns(10);
		
		JLabel payCardholderLabel = new JLabel("Cardholder Name");
		payCardholderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		payCardholderLabel.setBounds(30, 361, 143, 21);
		payPanel.add(payCardholderLabel);
		
		payCardholderEdit = new JTextField();
		payCardholderEdit.setBounds(199, 364, 153, 19);
		payPanel.add(payCardholderEdit);
		payCardholderEdit.setColumns(10);
		
		JLabel payExpiryDateLabel = new JLabel("Expiration Date");
		payExpiryDateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		payExpiryDateLabel.setBounds(30, 263, 143, 21);
		payPanel.add(payExpiryDateLabel);
		
		JDateChooser payExpiryDateChooser = new JDateChooser();
		payExpiryDateChooser.setBounds(181, 263, 103, 19);
		payPanel.add(payExpiryDateChooser);
		
		JLabel paySecretLabel = new JLabel("Security Code");
		paySecretLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		paySecretLabel.setBounds(30, 314, 100, 21);
		payPanel.add(paySecretLabel);
		
		paySecretEdit = new JPasswordField();
		paySecretEdit.setFont(new Font("Tahoma", Font.BOLD, 14));
		paySecretEdit.setBounds(154, 317, 103, 19);
		payPanel.add(paySecretEdit);
		
		JLabel payTotalLabel = new JLabel("TOTAL:");
		payTotalLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		payTotalLabel.setBounds(242, 424, 61, 39);
		payPanel.add(payTotalLabel);
		
		payTotalEdit = new JTextField();
		payTotalEdit.setEnabled(false);
		payTotalEdit.setFont(new Font("Tahoma", Font.BOLD, 14));
		payTotalEdit.setText("200");
		payTotalEdit.setBounds(321, 424, 69, 39);
		payPanel.add(payTotalEdit);
		payTotalEdit.setColumns(10);
		
		JButton payButton = new JButton("PAY");
		payButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DB db = new DB();
					Session session = db.getSession(String.valueOf(custSessTable.getValueAt(custSessTable.getSelectedRow(), 0)));
					session.paid = true;
					if (db.setSession(session))
						JOptionPane.showMessageDialog(null, "payment successful","message",JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "Could not process the payment","warning",JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(null, "Unfortunately our servers are not available","warning",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		personalPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				personalNameEdit.setText(user.name);
				personalDateChooser.setDate(Date.from(user.dob.atStartOfDay(ZoneId.systemDefault()).toInstant()));
				personalEmailEdit.setText(user.email);
				personalPhoneEdit.setText(user.phone);
			}
		});
		payButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		payButton.setIcon(new ImageIcon("img\\payment.png"));
		payButton.setBounds(358, 504, 153, 30);
		payPanel.add(payButton);
		
		JLabel payImgLabel = new JLabel("");
		payImgLabel.setIcon(new ImageIcon("img\\payment-creditcard-visa-icon (1).png"));
		payImgLabel.setBounds(505, 170, 228, 189);
		payPanel.add(payImgLabel);
		
		JButton payBackButton = new JButton("Back");
		payBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				custSessStartDateChooser.setDate(null);
				custSessEndDateChooser.setDate(null);
				custSessPaidCombo.setSelectedIndex(0);
				cl.show(contentPane,"10");
			}
		});
		payBackButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		payBackButton.setIcon(new ImageIcon("img\\Go-back-icon.png"));
		payBackButton.setBounds(58, 511, 115, 30);
		payPanel.add(payBackButton);
	}
}
