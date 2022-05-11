import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.*;

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
import javax.swing.SwingConstants;



public class MainFrame extends JFrame {
	enum UserType {
		CUSTOMER,
		MANAGER,
		TRAINER
	}
	private User user;
	private UserType userType;

	private JPanel contentPane;
	private JTextField loginEdit;
	private JTextField regNameEdit;
	private JTextField regPhoneEdit;
	private JTextField regEmailEdit;
	private JTextField regLoginEdit;
	private JTable manageRegTable;
	private JTable sessionTable;
	private JTable paymentTable;
	private JTable feedbackTable;
	private JTextField feedbackCommentEdit;
	private JTable reportTable;
	private JTextField reportFilterEdit;
	private JTextField textField;
	private JTable cust_trainSessTable;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_7;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTable paymentdetailTable;
	private JPasswordField passwordEdit;
	private JTextField textField_10;
	private JTextField textField_13;
	private JTable table;
	private JTable table_1;
	private JTextField textField_14;
	private JTextField textField_8;
	private JTextField textField_9;
	private JPasswordField passwordField;

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
		Image image1 = new ImageIcon("/img/APU logo (1).jpg").getImage();
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
		regPanelButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\new member.png"));
		regPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "3");
			}
		});
		regPanelButton.setBounds(13, 23, 244, 51);
		mainMenuPanel.add(regPanelButton);
		
		JButton manageRegPanelButton = new JButton("Manage registration");
		manageRegPanelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		manageRegPanelButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\list of members.png"));
		manageRegPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "4");
			}
		});
		manageRegPanelButton.setBounds(284, 23, 244, 51);
		mainMenuPanel.add(manageRegPanelButton);
		
		JButton bookPanelButton = new JButton("Book session");
		bookPanelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		bookPanelButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\session-idle-time-icon.png"));
		bookPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "5");
			}
		});
		bookPanelButton.setBounds(554, 23, 225, 51);
		mainMenuPanel.add(bookPanelButton);
		
		JButton sessionPanelButton = new JButton("check training session");
		sessionPanelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		sessionPanelButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\234.png"));
		sessionPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "7");
			}
		});
		sessionPanelButton.setBounds(13, 23, 244, 51);
		mainMenuPanel.add(sessionPanelButton);
		
		JButton cust_trainSessPanelButton = new JButton("view own training session");
		cust_trainSessPanelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		cust_trainSessPanelButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\Actions-contact-date-icon.png"));
		cust_trainSessPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane,"10");
			}
		});
		cust_trainSessPanelButton.setBounds(3, 23, 271, 51);
		mainMenuPanel.add(cust_trainSessPanelButton);
		
		JButton custPresonalBotton = new JButton("Presonal Details ");
		custPresonalBotton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\Resume-icon.png"));
		custPresonalBotton.setFont(new Font("Tahoma", Font.BOLD, 14));
		custPresonalBotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane,"Presonal_Details");
			}
		});
		custPresonalBotton.setBounds(296, 23, 244, 51);
		mainMenuPanel.add(custPresonalBotton);
		
		JButton btnNewButton_7 = new JButton("View feedback");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane,"name_1043485758744900");
			}
		});
		btnNewButton_7.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\rating-icon.png"));
		btnNewButton_7.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_7.setBounds(284, 23, 244, 51);
		mainMenuPanel.add(btnNewButton_7);
		
		JLabel lblNewLabel_27 = new JLabel("WELCOME");
		lblNewLabel_27.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\welcome-icon.png"));
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
					btnNewButton_7.setVisible(false);
				}

				if (userType != UserType.CUSTOMER) {
					cust_trainSessPanelButton.setVisible(false);
					custPresonalBotton.setVisible(false);
				}
			}
		});
		
		JButton authButton = new JButton("Sign in");
		authButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		authButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\login.png"));
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
							// TODO Show the same message as in the above
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
		passwordEdit.setBounds(833, 361, 225, 35);
		authPanel.add(passwordEdit);
		
		JLabel lblNewLabel_28 = new JLabel("");
		lblNewLabel_28.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\APU logo (1).jpg"));
		lblNewLabel_28.setBounds(30, 201, 500, 290);
		authPanel.add(lblNewLabel_28);
		
		JLabel lblNewLabel_29 = new JLabel("");
		lblNewLabel_29.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\login background.PNG"));
		lblNewLabel_29.setBounds(10, 0, 1175, 657);
		authPanel.add(lblNewLabel_29);
		
		JPanel registerPanel = new JPanel();
		registerPanel.setBackground(Color.CYAN);
		contentPane.add(registerPanel, "3");
		registerPanel.setLayout(null);
		
		ButtonGroup regRadioGroup = new ButtonGroup();
		
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
		
		JButton regButton = new JButton("ADD");
		regButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		regButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\new member.png"));
		regButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "member added successfully","message",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		regButton.setBounds(393, 522, 165, 62);
		registerPanel.add(regButton);
		
		JButton regBackButton = new JButton("Back");
		regBackButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		regBackButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\Go-back-icon.png"));
		regBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		regBackButton.setBounds(64, 522, 165, 57);
		registerPanel.add(regBackButton);
		
		JLabel lblNewLabel_5 = new JLabel("");
		Image image4 = new ImageIcon("img/profile-icon.png").getImage();
		lblNewLabel_5.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\profile-icon.png"));
		lblNewLabel_5.setBounds(471, 239, 135, 88);
		registerPanel.add(lblNewLabel_5);
		
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
		
		JLabel lblNewLabel_13 = new JLabel("Personal Photo");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_13.setBounds(461, 333, 111, 18);
		registerPanel.add(lblNewLabel_13);
		
		JLabel lblNewLabel_14 = new JLabel("New Member");
		lblNewLabel_14.setForeground(Color.BLUE);
		lblNewLabel_14.setFont(new Font("Aldhabi", Font.BOLD, 48));
		lblNewLabel_14.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\new member.png"));
		lblNewLabel_14.setBounds(323, 28, 298, 62);
		registerPanel.add(lblNewLabel_14);
		
		JLabel lblNewLabel_15 = new JLabel("ID");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_15.setBounds(131, 127, 45, 13);
		registerPanel.add(lblNewLabel_15);
		
		textField_10 = new JTextField();
		textField_10.setFont(new Font("Tahoma", Font.BOLD, 10));
		textField_10.setText("00");
		textField_10.setBounds(167, 126, 34, 19);
		registerPanel.add(textField_10);
		textField_10.setColumns(10);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"M", "F"}));
		comboBox_2.setBounds(233, 416, 45, 21);
		registerPanel.add(comboBox_2);
		
		JComboBox comboBox_6_1 = new JComboBox();
		comboBox_6_1.setModel(new DefaultComboBoxModel(new String[] {"Manager ", "Trainer", "Customer"}));
		comboBox_6_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		comboBox_6_1.setBounds(10, 133, 86, 21);
		registerPanel.add(comboBox_6_1);
		
		JDateChooser dateChooser_3 = new JDateChooser();
		dateChooser_3.setBounds(221, 239, 96, 20);
		registerPanel.add(dateChooser_3);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(476, 196, 145, 19);
		registerPanel.add(passwordField);
		
		JPanel manageRegPanel = new JPanel();
		manageRegPanel.setBackground(Color.CYAN);
		contentPane.add(manageRegPanel, "4");
		manageRegPanel.setLayout(null);
		
		manageRegTable = new JTable();
		manageRegTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"a", "b"},
				{"c", "d"},
			},
			new String[] {
				"col 1", "col 2"
			}
		));
		manageRegTable.setBounds(159, 28, 807, 403);
		manageRegPanel.add(manageRegTable);
		
		JButton manageRegSaveButton = new JButton("Save");
		manageRegSaveButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\save.png"));
		manageRegSaveButton.setBounds(693, 493, 117, 63);
		manageRegPanel.add(manageRegSaveButton);
		
		JButton manageRegBackButton = new JButton("Back");
		manageRegBackButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\Go-back-icon.png"));
		manageRegBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		manageRegBackButton.setBounds(275, 493, 161, 63);
		manageRegPanel.add(manageRegBackButton);
		
		JButton manageRegDeleteButton = new JButton("Delete");
		manageRegDeleteButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\delete.png"));
		manageRegDeleteButton.setBounds(489, 493, 142, 63);
		manageRegPanel.add(manageRegDeleteButton);
		
		JLabel search_label = new JLabel("Enter ID :");
		search_label.setFont(new Font("Tahoma", Font.BOLD, 15));
		search_label.setBounds(22, 41, 79, 30);
		manageRegPanel.add(search_label);
		
		textField = new JTextField();
		textField.setBounds(15, 72, 103, 30);
		manageRegPanel.add(textField);
		textField.setColumns(10);
		
		JButton searchBotton = new JButton("Search");
		searchBotton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\search.png"));
		searchBotton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		searchBotton.setBounds(15, 246, 126, 30);
		manageRegPanel.add(searchBotton);
		
		JLabel search_label_1 = new JLabel("Enter name :");
		search_label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		search_label_1.setBounds(22, 112, 103, 30);
		manageRegPanel.add(search_label_1);
		
		textField_13 = new JTextField();
		textField_13.setColumns(10);
		textField_13.setBounds(15, 141, 103, 30);
		manageRegPanel.add(textField_13);
		
		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setFont(new Font("Tahoma", Font.BOLD, 10));
		comboBox_6.setModel(new DefaultComboBoxModel(new String[] {"Manager", "Trainer", "Customer"}));
		comboBox_6.setBounds(15, 191, 86, 21);
		manageRegPanel.add(comboBox_6);
		
		JPanel bookPanel = new JPanel();
		bookPanel.setBackground(Color.CYAN);
		contentPane.add(bookPanel, "5");
		bookPanel.setLayout(null);
		
		JButton bookBackButton = new JButton("back");
		bookBackButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\Go-back-icon.png"));
		bookBackButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		bookBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		bookBackButton.setBounds(120, 557, 105, 23);
		bookPanel.add(bookBackButton);
		
		JButton bookButton = new JButton("book");
		bookButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		bookButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\Sketch-Book-icon.png"));
		bookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Session booked successfully","message",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		bookButton.setBounds(484, 557, 105, 23);
		bookPanel.add(bookButton);
		
		JLabel lblNewLabel_16 = new JLabel("customer");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_16.setBounds(50, 184, 123, 20);
		bookPanel.add(lblNewLabel_16);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox_3.setBounds(152, 184, 89, 21);
		bookPanel.add(comboBox_3);
		
		JLabel lblNewLabel_17 = new JLabel("Book training Session");
		lblNewLabel_17.setFont(new Font("Algerian", Font.BOLD, 25));
		lblNewLabel_17.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\Schedule-icon.png"));
		lblNewLabel_17.setBounds(293, 36, 551, 96);
		bookPanel.add(lblNewLabel_17);
		
		JLabel lblNewLabel_18 = new JLabel("trainer");
		lblNewLabel_18.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNewLabel_18.setBounds(53, 234, 89, 23);
		bookPanel.add(lblNewLabel_18);
		
		JComboBox comboBox_3_1 = new JComboBox();
		comboBox_3_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox_3_1.setBounds(152, 235, 89, 21);
		bookPanel.add(comboBox_3_1);
		
		JLabel lblNewLabel_19 = new JLabel("Date");
		lblNewLabel_19.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_19.setBounds(51, 311, 73, 29);
		bookPanel.add(lblNewLabel_19);
		
		JCalendar calendar = new JCalendar();
		calendar.setBounds(108, 316, 206, 152);
		bookPanel.add(calendar);
		
		JLabel lblNewLabel_20 = new JLabel("time");
		lblNewLabel_20.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_20.setBounds(359, 184, 89, 21);
		bookPanel.add(lblNewLabel_20);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setBounds(451, 186, 111, 21);
		bookPanel.add(comboBox_4);
		
		JPanel sessionPanel = new JPanel();
		sessionPanel.setBackground(Color.CYAN);
		sessionPanel.setLayout(null);
		contentPane.add(sessionPanel, "7");
		
		sessionTable = new JTable();
		sessionTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"Session list"},
			},
			new String[] {
				"New column"
			}
		));
		sessionTable.setBounds(318, 122, 807, 403);
		sessionPanel.add(sessionTable);
		
		JButton sessionBackButton = new JButton("Back");
		sessionBackButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\Go-back-icon.png"));
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
		lblNewLabel_21.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\Actions-view-calendar-timeline-icon.png"));
		lblNewLabel_21.setBounds(290, 10, 443, 106);
		sessionPanel.add(lblNewLabel_21);
		
		JLabel lblNewLabel_22 = new JLabel("Start Date");
		lblNewLabel_22.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_22.setBounds(40, 150, 89, 23);
		sessionPanel.add(lblNewLabel_22);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(139, 154, 70, 19);
		sessionPanel.add(dateChooser);
		
		JLabel lblNewLabel_23 = new JLabel("End Date");
		lblNewLabel_23.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_23.setBounds(40, 218, 89, 23);
		sessionPanel.add(lblNewLabel_23);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(139, 218, 70, 19);
		sessionPanel.add(dateChooser_1);
		
		JLabel lblNewLabel_24 = new JLabel("Payment Status");
		lblNewLabel_24.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_24.setBounds(29, 291, 114, 23);
		sessionPanel.add(lblNewLabel_24);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		comboBox_5.setModel(new DefaultComboBoxModel(new String[] {"Paid", "Unpaid"}));
		comboBox_5.setBounds(167, 292, 79, 23);
		sessionPanel.add(comboBox_5);
		
		JPanel paymentPanel = new JPanel();
		paymentPanel.setBackground(Color.CYAN);
		paymentPanel.setLayout(null);
		contentPane.add(paymentPanel, "8");
		
		paymentTable = new JTable();
		paymentTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"Choose session"},
			},
			new String[] {
				"New column"
			}
		));
		paymentTable.setBounds(159, 28, 807, 403);
		paymentPanel.add(paymentTable);
		
		JButton paymentSaveButton = new JButton("Save");
		paymentSaveButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\save.png"));
		paymentSaveButton.setBounds(701, 504, 105, 40);
		paymentPanel.add(paymentSaveButton);
		
		JButton paymentBackButton = new JButton("Back");
		paymentBackButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\Go-back-icon.png"));
		paymentBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		paymentBackButton.setBounds(276, 504, 129, 40);
		paymentPanel.add(paymentBackButton);
		
		JButton paymentCollectButton = new JButton("Collect payment");
		paymentCollectButton.setBounds(478, 466, 121, 23);
		paymentPanel.add(paymentCollectButton);
		
		JPanel feedbackPanel = new JPanel();
		feedbackPanel.setBackground(Color.CYAN);
		feedbackPanel.setLayout(null);
		contentPane.add(feedbackPanel, "9");
		
		feedbackTable = new JTable();
		feedbackTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"choose the session"},
			},
			new String[] {
				"New column"
			}
		));
		feedbackTable.setBounds(159, 28, 807, 403);
		feedbackPanel.add(feedbackTable);
		
		JButton feedbackSaveButton = new JButton("Save");
		feedbackSaveButton.setBounds(632, 568, 89, 23);
		feedbackPanel.add(feedbackSaveButton);
		
		JButton feedbackBackButton = new JButton("Back");
		feedbackBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		feedbackBackButton.setBounds(301, 568, 89, 23);
		feedbackPanel.add(feedbackBackButton);
		
		feedbackCommentEdit = new JTextField();
		feedbackCommentEdit.setText("feedback comment");
		feedbackCommentEdit.setBounds(426, 506, 134, 20);
		feedbackPanel.add(feedbackCommentEdit);
		feedbackCommentEdit.setColumns(10);
		
		JRadioButton feedbackRate1Radio = new JRadioButton("1");
		feedbackRate1Radio.setBounds(387, 453, 43, 23);
		feedbackPanel.add(feedbackRate1Radio);
		
		JRadioButton feedbackRate2Radio = new JRadioButton("2");
		feedbackRate2Radio.setBounds(432, 453, 43, 23);
		feedbackPanel.add(feedbackRate2Radio);
		
		JRadioButton feedbackRate3Radio = new JRadioButton("3");
		feedbackRate3Radio.setBounds(473, 453, 43, 23);
		feedbackPanel.add(feedbackRate3Radio);
		
		JRadioButton feedbackRate4Radio = new JRadioButton("4");
		feedbackRate4Radio.setBounds(518, 453, 43, 23);
		feedbackPanel.add(feedbackRate4Radio);
		
		JRadioButton feedbackRate5Radio = new JRadioButton("5");
		feedbackRate5Radio.setBounds(563, 453, 43, 23);
		feedbackPanel.add(feedbackRate5Radio);
		
		JPanel reportPanel = new JPanel();
		reportPanel.setBackground(Color.CYAN);
		reportPanel.setLayout(null);
		contentPane.add(reportPanel, "6");
		
		reportTable = new JTable();
		reportTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"a", "b"},
				{"c", "d"},
			},
			new String[] {
				"col1", "col2"
			}
		));
		reportTable.setBounds(181, 23, 807, 403);
		reportPanel.add(reportTable);
		
		JTableHeader reportTableHeader = reportTable.getTableHeader();
		reportTableHeader.setBounds(181, 0, 807, 20);
		reportPanel.add(reportTableHeader);
		
		JButton reportGenerateButton = new JButton("Generate");
		reportGenerateButton.setBounds(701, 504, 89, 23);
		reportPanel.add(reportGenerateButton);
		
		JButton reportBackButton = new JButton("Back");
		reportBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		reportBackButton.setBounds(316, 504, 89, 23);
		reportPanel.add(reportBackButton);
		
		JComboBox reportTableDropdown = new JComboBox();
		reportTableDropdown.setModel(new DefaultComboBoxModel(new String[] {"select table"}));
		reportTableDropdown.setBounds(299, 442, 129, 22);
		reportPanel.add(reportTableDropdown);
		
		reportFilterEdit = new JTextField();
		reportFilterEdit.setText("Filtering options");
		reportFilterEdit.setBounds(682, 443, 96, 20);
		reportPanel.add(reportFilterEdit);
		reportFilterEdit.setColumns(10);
		
		JPanel cust_trainSessPanel = new JPanel();
		cust_trainSessPanel.setBackground(Color.CYAN);
		contentPane.add(cust_trainSessPanel, "10");
		cust_trainSessPanel.setLayout(null);
		
		cust_trainSessTable = new JTable();
		cust_trainSessTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Trainer name", "Date", "Time"
			}
		));
		cust_trainSessTable.setBounds(121, 65, 674, 403);
		cust_trainSessPanel.add(cust_trainSessTable);
		
		JTableHeader cust_trainSessTableHeader = cust_trainSessTable.getTableHeader();
		cust_trainSessTableHeader.setBounds(181, 0, 807, 20);
		cust_trainSessPanel.add(cust_trainSessTableHeader);
		
		JLabel cust_trainSesslabel = new JLabel("My training sesseions :");
		cust_trainSesslabel.setFont(new Font("Algerian", Font.BOLD, 24));
		cust_trainSesslabel.setBounds(54, 10, 301, 46);
		cust_trainSessPanel.add(cust_trainSesslabel);
		
		JButton cust_trainSessBackbotton = new JButton("Back");
		cust_trainSessBackbotton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\Go-back-icon.png"));
		cust_trainSessBackbotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		cust_trainSessBackbotton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cust_trainSessBackbotton.setBounds(99, 495, 140, 29);
		cust_trainSessPanel.add(cust_trainSessBackbotton);
		
		JButton payBotton = new JButton("Pay");
		payBotton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\payment.png"));
		payBotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "name_527830163244599");
			}
		});
		payBotton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		payBotton.setBounds(623, 495, 175, 39);
		cust_trainSessPanel.add(payBotton);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"Choose session"},
			},
			new String[] {
				"New column"
			}
		));
		table_1.setBounds(848, 54, 280, 403);
		cust_trainSessPanel.add(table_1);
		
		JRadioButton feedbackRate1Radio_1 = new JRadioButton("1");
		feedbackRate1Radio_1.setBounds(882, 463, 43, 23);
		cust_trainSessPanel.add(feedbackRate1Radio_1);
		
		JRadioButton feedbackRate2Radio_1 = new JRadioButton("2");
		feedbackRate2Radio_1.setBounds(927, 463, 43, 23);
		cust_trainSessPanel.add(feedbackRate2Radio_1);
		
		JRadioButton feedbackRate3Radio_1 = new JRadioButton("3");
		feedbackRate3Radio_1.setBounds(968, 463, 43, 23);
		cust_trainSessPanel.add(feedbackRate3Radio_1);
		
		JRadioButton feedbackRate4Radio_1 = new JRadioButton("4");
		feedbackRate4Radio_1.setBounds(1013, 463, 43, 23);
		cust_trainSessPanel.add(feedbackRate4Radio_1);
		
		JRadioButton feedbackRate5Radio_1 = new JRadioButton("5");
		feedbackRate5Radio_1.setBounds(1058, 463, 43, 23);
		cust_trainSessPanel.add(feedbackRate5Radio_1);
		
		textField_14 = new JTextField();
		textField_14.setText("feedback comment");
		textField_14.setColumns(10);
		textField_14.setBounds(922, 504, 134, 20);
		cust_trainSessPanel.add(textField_14);
		
		JButton btnNewButton_8 = new JButton("Submit feedback");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Thanks for giving us you feedback","message",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton_8.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton_8.setIcon(new ImageIcon("img/save.png"));
		btnNewButton_8.setBounds(927, 556, 174, 29);
		cust_trainSessPanel.add(btnNewButton_8);
		
		JLabel lblNewLabel_26 = new JLabel("Start Date");
		lblNewLabel_26.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_26.setBounds(20, 112, 77, 23);
		cust_trainSessPanel.add(lblNewLabel_26);
		
		JDateChooser dateChooser_2 = new JDateChooser();
		dateChooser_2.setBounds(20, 145, 70, 19);
		cust_trainSessPanel.add(dateChooser_2);
		
		JLabel lblNewLabel_26_1 = new JLabel("END Date");
		lblNewLabel_26_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_26_1.setBounds(20, 184, 77, 23);
		cust_trainSessPanel.add(lblNewLabel_26_1);
		
		JDateChooser dateChooser_2_1 = new JDateChooser();
		dateChooser_2_1.setBounds(20, 217, 70, 19);
		cust_trainSessPanel.add(dateChooser_2_1);
		
		JPanel payPanel = new JPanel();
		payPanel.setBackground(Color.CYAN);
		contentPane.add(payPanel, "name_527830163244599");
		payPanel.setLayout(null);
		
		JLabel paymentSumLabel = new JLabel("paymet summary");
		paymentSumLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		paymentSumLabel.setBounds(145, 10, 167, 35);
		payPanel.add(paymentSumLabel);
		
		JLabel paymentMethodlabel = new JLabel("payment method");
		paymentMethodlabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		paymentMethodlabel.setBounds(69, 77, 125, 28);
		payPanel.add(paymentMethodlabel);
		
		JComboBox paymetTypecomboBox = new JComboBox();
		paymetTypecomboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		paymetTypecomboBox.setModel(new DefaultComboBoxModel(new String[] {"VISA", "MasteCard", "PayPal", "other"}));
		paymetTypecomboBox.setBounds(204, 69, 79, 35);
		payPanel.add(paymetTypecomboBox);
		
		JLabel lblNewLabel = new JLabel("Card Number:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(69, 115, 108, 28);
		payPanel.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_1.setBounds(204, 120, 138, 19);
		payPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblCardHolderName = new JLabel("card Holder Name:");
		lblCardHolderName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCardHolderName.setBounds(69, 153, 147, 28);
		payPanel.add(lblCardHolderName);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(204, 160, 138, 19);
		payPanel.add(textField_2);
		
		JLabel lblValidUntil = new JLabel("Valid Until ");
		lblValidUntil.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblValidUntil.setBounds(69, 203, 87, 28);
		payPanel.add(lblValidUntil);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		comboBox.setBounds(176, 209, 54, 21);
		payPanel.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"2022\t", "2023", "2024", "2025", "2026"}));
		comboBox_1.setBounds(254, 209, 58, 21);
		payPanel.add(comboBox_1);
		
		JLabel lblCardHolderName_1 = new JLabel("CCV");
		lblCardHolderName_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCardHolderName_1.setBounds(322, 203, 87, 28);
		payPanel.add(lblCardHolderName_1);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_3.setColumns(10);
		textField_3.setBounds(363, 210, 74, 19);
		payPanel.add(textField_3);
		
		JLabel lblNewLabel_1 = new JLabel("TOTAL:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(267, 286, 75, 28);
		payPanel.add(lblNewLabel_1);
		
		textField_4 = new JTextField();
		textField_4.setBounds(340, 286, 97, 26);
		payPanel.add(textField_4);
		textField_4.setColumns(10);
		
		JButton btnNewButton = new JButton("Pay Now");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "payment done successfully","message",JOptionPane.INFORMATION_MESSAGE);
				
				
			}
		});
		btnNewButton.setBackground(Color.GREEN);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBounds(301, 353, 136, 28);
		payPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "10");
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_1.setBounds(59, 361, 85, 21);
		payPanel.add(btnNewButton_1);
		
		JLabel visaImage = new JLabel("");
		Image image3 = new ImageIcon("img/payment-creditcard-visa-icon.png").getImage();
		visaImage.setIcon(new ImageIcon(image3));
		visaImage.setBounds(540, 57, 214, 100);
		payPanel.add(visaImage);
		
		JPanel custPersonalPanel = new JPanel();
		custPersonalPanel.setBackground(Color.CYAN);
		contentPane.add(custPersonalPanel, "Presonal_Details");
		custPersonalPanel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Presonal Details ");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\personal-information-icon.png"));
		lblNewLabel_2.setFont(new Font("Algerian", Font.PLAIN, 25));
		lblNewLabel_2.setBounds(247, 27, 297, 66);
		custPersonalPanel.add(lblNewLabel_2);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(81, 169, 96, 20);
		custPersonalPanel.add(textField_5);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(81, 299, 96, 20);
		custPersonalPanel.add(textField_7);
		
		JLabel lblNewLabel_3 = new JLabel("name");
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3.setBounds(10, 170, 45, 13);
		custPersonalPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("DOB");
		lblNewLabel_3_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3_1.setBounds(10, 243, 45, 13);
		custPersonalPanel.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("phone");
		lblNewLabel_3_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3_2.setBounds(10, 296, 61, 20);
		custPersonalPanel.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_4 = new JLabel("Gender");
		lblNewLabel_3_4.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3_4.setBounds(10, 360, 61, 13);
		custPersonalPanel.add(lblNewLabel_3_4);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("photo");
		lblNewLabel_3_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3_1_1.setBounds(420, 257, 61, 17);
		custPersonalPanel.add(lblNewLabel_3_1_1);
		
		JLabel lblNewLabel_3_4_1 = new JLabel("Highet");
		lblNewLabel_3_4_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3_4_1.setBounds(25, 443, 61, 19);
		custPersonalPanel.add(lblNewLabel_3_4_1);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(140, 445, 96, 20);
		custPersonalPanel.add(textField_11);
		
		JLabel lblNewLabel_3_4_1_1 = new JLabel("Wight");
		lblNewLabel_3_4_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3_4_1_1.setBounds(277, 443, 61, 19);
		custPersonalPanel.add(lblNewLabel_3_4_1_1);
		
		textField_12 = new JTextField();
		textField_12.setColumns(10);
		textField_12.setBounds(343, 445, 96, 20);
		custPersonalPanel.add(textField_12);
		
		JButton btnNewButton_2 = new JButton("update");
		btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\update & delete member.png"));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_2.setBounds(470, 529, 160, 36);
		custPersonalPanel.add(btnNewButton_2);
		
		JButton btnNewButton_2_1 = new JButton("Back");
		btnNewButton_2_1.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\Go-back-icon.png"));
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		btnNewButton_2_1.setBounds(140, 531, 109, 36);
		custPersonalPanel.add(btnNewButton_2_1);
		
		JLabel profile = new JLabel("");
		Image image2 = new ImageIcon("img/profile-icon.png").getImage();
		profile.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\profile-icon.png"));
		profile.setBounds(410, 167, 109, 80);
		custPersonalPanel.add(profile);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(81, 359, 96, 20);
		custPersonalPanel.add(textField_8);
		
		JLabel lblNewLabel_3_3 = new JLabel("ID");
		lblNewLabel_3_3.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3_3.setBounds(10, 120, 45, 13);
		custPersonalPanel.add(lblNewLabel_3_3);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(81, 119, 96, 20);
		custPersonalPanel.add(textField_9);
		
		JDateChooser dateChooser_4 = new JDateChooser();
		dateChooser_4.setBounds(81, 243, 70, 19);
		custPersonalPanel.add(dateChooser_4);
		
		JPanel paymentdetailsPanel = new JPanel();
		paymentdetailsPanel.setBackground(Color.CYAN);
		contentPane.add(paymentdetailsPanel, "paymentDetails");
		paymentdetailsPanel.setLayout(null);
		
		paymentdetailTable = new JTable();
		paymentdetailTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"choose payment"},
			},
			new String[] {
				"New column"
			}
		));
		paymentdetailTable.setBounds(34, 86, 807, 403);
		paymentdetailsPanel.add(paymentdetailTable);
		
		JButton btnNewButton_3 = new JButton("view details");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane,"receipt");
			}
		});
		btnNewButton_3.setBounds(616, 516, 170, 33);
		paymentdetailsPanel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Back");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_4.setBounds(141, 516, 85, 21);
		paymentdetailsPanel.add(btnNewButton_4);
		
		JPanel ReciptPanel = new JPanel();
		ReciptPanel.setBackground(Color.CYAN);
		contentPane.add(ReciptPanel, "receipt");
		ReciptPanel.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Payment receipt");
		lblNewLabel_4.setBounds(472, 25, 164, 38);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ReciptPanel.add(lblNewLabel_4);
		
		JButton btnNewButton_5 = new JButton("Back");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "paymentDetails");
			}
		});
		btnNewButton_5.setBounds(376, 399, 85, 21);
		ReciptPanel.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("print");
		btnNewButton_6.setBounds(673, 399, 85, 21);
		ReciptPanel.add(btnNewButton_6);
		
		JPanel Ratings = new JPanel();
		Ratings.setBackground(Color.CYAN);
		contentPane.add(Ratings, "name_1043485758744900");
		Ratings.setLayout(null);
		
		JLabel lblNewLabel_25 = new JLabel("Feedback and Rating");
		lblNewLabel_25.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\feedvack.png"));
		lblNewLabel_25.setBounds(424, 40, 392, 90);
		lblNewLabel_25.setFont(new Font("Algerian", Font.BOLD, 24));
		Ratings.add(lblNewLabel_25);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Customer name", "Session", "Rating"
			}
		));
		table.setBounds(183, 140, 885, 414);
		Ratings.add(table);
		
		JButton sessionBackButton_1 = new JButton("Back");
		sessionBackButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane,"2");
			}
		});
		sessionBackButton_1.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Gym Icon Jframe (1)\\Icon Gym Jframe\\Go-back-icon.png"));
		sessionBackButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		sessionBackButton_1.setBounds(128, 588, 122, 23);
		Ratings.add(sessionBackButton_1);
	}
}
