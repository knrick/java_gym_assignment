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
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import java.awt.Color;



public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField loginEdit;
	private JTextField passwordEdit;
	private JTextField regNameEdit;
	private JTextField regDobEdit;
	private JTextField regPhoneEdit;
	private JTextField regEmailEdit;
	private JTextField regGenderEdit;
	private JTextField regLoginEdit;
	private JTextField regPasswordEdit;
	private JTextField regPhotoEdit;
	private JTable manageRegTable;
	private JTextField bookCustomerEdit;
	private JTextField bookTrainerEdit;
	private JTextField bookDateEdit;
	private JTextField bookTimeEdit;
	private JTable sessionTable;
	private JTable paymentTable;
	private JTable feedbackTable;
	private JTextField feedbackCommentEdit;
	private JTable reportTable;
	private JTextField reportFilterEdit;
	private JTextField manageRegFilterEdit;
	private JTextField textField;
	private JTable cust_trainSessTable;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTable paymentdetailTable;
	JLabel gym_iconLabel;

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
		Image image1 = new ImageIcon(this.getClass().getResource("APU logo (1).jpg")).getImage();
		authLabel.setIcon(new ImageIcon(image1));
		authLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		authLabel.setBounds(105, 10, 442, 290);
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
		contentPane.add(mainMenuPanel, "2");
		mainMenuPanel.setLayout(null);
		
		JButton regPanelButton = new JButton("register");
		regPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "3");
			}
		});
		regPanelButton.setBounds(180, 195, 89, 23);
		mainMenuPanel.add(regPanelButton);
		
		JButton manageRegPanelButton = new JButton("Manage registration");
		manageRegPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "4");
			}
		});
		manageRegPanelButton.setBounds(139, 260, 174, 23);
		mainMenuPanel.add(manageRegPanelButton);
		
		JButton bookPanelButton = new JButton("Book session");
		bookPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "5");
			}
		});
		bookPanelButton.setBounds(156, 320, 133, 23);
		mainMenuPanel.add(bookPanelButton);
		
		JButton reportPanelButton = new JButton("Generate report");
		reportPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "6");
			}
		});
		reportPanelButton.setBounds(156, 375, 157, 23);
		mainMenuPanel.add(reportPanelButton);
		
		JButton sessionPanelButton = new JButton("check training session");
		sessionPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "7");
			}
		});
		sessionPanelButton.setBounds(588, 195, 212, 23);
		mainMenuPanel.add(sessionPanelButton);
		
		JButton paymentPanelButton = new JButton("collect payments");
		paymentPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "8");
			}
		});
		paymentPanelButton.setBounds(616, 273, 184, 23);
		mainMenuPanel.add(paymentPanelButton);
		
		JButton feedbackPanelButton = new JButton("provide feedback");
		feedbackPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "9");
			}
		});
		feedbackPanelButton.setBounds(649, 385, 151, 23);
		mainMenuPanel.add(feedbackPanelButton);
		
		JButton cust_trainSessPanelButton = new JButton("view own training session");
		cust_trainSessPanelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane,"10");
			}
		});
		cust_trainSessPanelButton.setBounds(923, 196, 189, 22);
		mainMenuPanel.add(cust_trainSessPanelButton);
		
		JButton custPresonalBotton = new JButton("Presonal Details ");
		custPresonalBotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane,"Presonal_Details");
			}
		});
		custPresonalBotton.setBounds(944, 261, 151, 21);
		mainMenuPanel.add(custPresonalBotton);
		
		JButton feedbackPanelButton_1 = new JButton("provide feedback");
		feedbackPanelButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "9");
			}
		});
		feedbackPanelButton_1.setBounds(944, 321, 151, 23);
		mainMenuPanel.add(feedbackPanelButton_1);
		
		JButton viewpaymentBotton = new JButton("View payments");
		viewpaymentBotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane,"paymentDetails");
			}
		});
		viewpaymentBotton.setBounds(636, 320, 151, 38);
		mainMenuPanel.add(viewpaymentBotton);
		
		JButton authButton = new JButton("Sign in");
		authButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		authButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\Ok-icon.png"));
		authButton.setBounds(200, 531, 179, 47);
		authButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		authPanel.add(authButton);
		
		loginEdit = new JTextField();
		loginEdit.setBounds(275, 351, 171, 33);
		authPanel.add(loginEdit);
		loginEdit.setColumns(10);
		
		JLabel loginLabel = new JLabel("Username");
		loginLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		loginLabel.setBounds(105, 351, 122, 25);
		authPanel.add(loginLabel);
		
		JLabel passwordLabel = new JLabel("password");
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		passwordLabel.setBounds(104, 419, 122, 33);
		authPanel.add(passwordLabel);
		
		passwordEdit = new JTextField();
		passwordEdit.setBounds(275, 423, 171, 33);
		passwordEdit.setColumns(10);
		authPanel.add(passwordEdit);
		
		gym_iconLabel = new JLabel("");
		Image image = new ImageIcon(this.getClass().getResource("gym_logo.png")).getImage();
		gym_iconLabel.setIcon(new ImageIcon(image));
		gym_iconLabel.setBounds(627, 0, 537, 693);
		authPanel.add(gym_iconLabel);
		
		JPanel registerPanel = new JPanel();
		contentPane.add(registerPanel, "3");
		registerPanel.setLayout(null);
		
		JRadioButton regManagerRadio = new JRadioButton("Manager");
		regManagerRadio.setBounds(119, 121, 111, 23);
		registerPanel.add(regManagerRadio);
		
		JRadioButton regTrainerRadio = new JRadioButton("trainer");
		regTrainerRadio.setBounds(119, 166, 111, 23);
		registerPanel.add(regTrainerRadio);
		
		JRadioButton regCustomerButton = new JRadioButton("customer");
		regCustomerButton.setBounds(119, 214, 111, 23);
		registerPanel.add(regCustomerButton);
		
		regNameEdit = new JTextField();
		regNameEdit.setText("name");
		regNameEdit.setBounds(273, 121, 96, 20);
		registerPanel.add(regNameEdit);
		regNameEdit.setColumns(10);
		
		regDobEdit = new JTextField();
		regDobEdit.setText("DOB");
		regDobEdit.setBounds(273, 166, 96, 20);
		registerPanel.add(regDobEdit);
		regDobEdit.setColumns(10);
		
		regPhoneEdit = new JTextField();
		regPhoneEdit.setText("phone");
		regPhoneEdit.setBounds(273, 215, 96, 20);
		registerPanel.add(regPhoneEdit);
		regPhoneEdit.setColumns(10);
		
		regEmailEdit = new JTextField();
		regEmailEdit.setText("email");
		regEmailEdit.setBounds(273, 276, 96, 20);
		registerPanel.add(regEmailEdit);
		regEmailEdit.setColumns(10);
		
		regGenderEdit = new JTextField();
		regGenderEdit.setText("gender");
		regGenderEdit.setBounds(273, 333, 96, 20);
		registerPanel.add(regGenderEdit);
		regGenderEdit.setColumns(10);
		
		regLoginEdit = new JTextField();
		regLoginEdit.setText("login");
		regLoginEdit.setBounds(476, 122, 96, 20);
		registerPanel.add(regLoginEdit);
		regLoginEdit.setColumns(10);
		
		regPasswordEdit = new JTextField();
		regPasswordEdit.setText("password");
		regPasswordEdit.setBounds(476, 184, 96, 20);
		registerPanel.add(regPasswordEdit);
		regPasswordEdit.setColumns(10);
		
		regPhotoEdit = new JTextField();
		regPhotoEdit.setText("photo");
		regPhotoEdit.setBounds(669, 168, 96, 20);
		registerPanel.add(regPhotoEdit);
		regPhotoEdit.setColumns(10);
		
		JButton regButton = new JButton("ADD");
		regButton.setIcon(new ImageIcon("C:\\Users\\EQS\\Downloads\\add.png"));
		regButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		regButton.setBounds(576, 397, 167, 105);
		registerPanel.add(regButton);
		
		JButton regBackButton = new JButton("Back");
		regBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		regBackButton.setBounds(241, 495, 89, 23);
		registerPanel.add(regBackButton);
		
		JLabel lblNewLabel_5 = new JLabel("");
		Image image4 = new ImageIcon(this.getClass().getResource("profile-icon.png")).getImage();
		lblNewLabel_5.setIcon(new ImageIcon(image4));
		lblNewLabel_5.setBounds(669, 70, 135, 88);
		registerPanel.add(lblNewLabel_5);
		
		JPanel manageRegPanel = new JPanel();
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
		manageRegSaveButton.setBounds(701, 504, 89, 23);
		manageRegPanel.add(manageRegSaveButton);
		
		JButton manageRegBackButton = new JButton("Back");
		manageRegBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		manageRegBackButton.setBounds(316, 504, 89, 23);
		manageRegPanel.add(manageRegBackButton);
		
		manageRegFilterEdit = new JTextField();
		manageRegFilterEdit.setText("Filtering options");
		manageRegFilterEdit.setColumns(10);
		manageRegFilterEdit.setBounds(655, 457, 96, 20);
		manageRegPanel.add(manageRegFilterEdit);
		
		JButton manageRegDeleteButton = new JButton("Delete");
		manageRegDeleteButton.setBounds(503, 504, 89, 23);
		manageRegPanel.add(manageRegDeleteButton);
		
		JLabel search_label = new JLabel("Enter ID :");
		search_label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		search_label.setBounds(22, 41, 79, 30);
		manageRegPanel.add(search_label);
		
		textField = new JTextField();
		textField.setBounds(15, 72, 103, 30);
		manageRegPanel.add(textField);
		textField.setColumns(10);
		
		JRadioButton searchtrainer = new JRadioButton("trainer");
		searchtrainer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		searchtrainer.setBounds(15, 108, 103, 21);
		manageRegPanel.add(searchtrainer);
		
		JRadioButton searchtcust = new JRadioButton("customer");
		searchtcust.setFont(new Font("Tahoma", Font.PLAIN, 15));
		searchtcust.setBounds(15, 135, 103, 21);
		manageRegPanel.add(searchtcust);
		
		JButton searchBotton = new JButton("Search");
		searchBotton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		searchBotton.setBounds(15, 173, 126, 30);
		manageRegPanel.add(searchBotton);
		
		JPanel bookPanel = new JPanel();
		contentPane.add(bookPanel, "5");
		bookPanel.setLayout(null);
		
		JButton bookBackButton = new JButton("back");
		bookBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		bookBackButton.setBounds(179, 482, 89, 23);
		bookPanel.add(bookBackButton);
		
		JButton bookButton = new JButton("book");
		bookButton.setBounds(553, 482, 89, 23);
		bookPanel.add(bookButton);
		
		bookCustomerEdit = new JTextField();
		bookCustomerEdit.setText("customer");
		bookCustomerEdit.setBounds(179, 145, 96, 20);
		bookPanel.add(bookCustomerEdit);
		bookCustomerEdit.setColumns(10);
		
		bookTrainerEdit = new JTextField();
		bookTrainerEdit.setText("trainer");
		bookTrainerEdit.setBounds(179, 193, 96, 20);
		bookPanel.add(bookTrainerEdit);
		bookTrainerEdit.setColumns(10);
		
		bookDateEdit = new JTextField();
		bookDateEdit.setText("date");
		bookDateEdit.setBounds(179, 276, 96, 20);
		bookPanel.add(bookDateEdit);
		bookDateEdit.setColumns(10);
		
		bookTimeEdit = new JTextField();
		bookTimeEdit.setText("time");
		bookTimeEdit.setBounds(172, 351, 96, 20);
		bookPanel.add(bookTimeEdit);
		bookTimeEdit.setColumns(10);
		
		JPanel sessionPanel = new JPanel();
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
		sessionTable.setBounds(159, 28, 807, 403);
		sessionPanel.add(sessionTable);
		
		JButton sessionBackButton = new JButton("Back");
		sessionBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		sessionBackButton.setBounds(316, 504, 89, 23);
		sessionPanel.add(sessionBackButton);
		
		JPanel paymentPanel = new JPanel();
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
		paymentSaveButton.setBounds(701, 504, 89, 23);
		paymentPanel.add(paymentSaveButton);
		
		JButton paymentBackButton = new JButton("Back");
		paymentBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		paymentBackButton.setBounds(316, 504, 89, 23);
		paymentPanel.add(paymentBackButton);
		
		JButton paymentCollectButton = new JButton("Collect payment");
		paymentCollectButton.setBounds(478, 466, 121, 23);
		paymentPanel.add(paymentCollectButton);
		
		JPanel feedbackPanel = new JPanel();
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
		cust_trainSessTable.setBounds(80, 65, 807, 403);
		cust_trainSessPanel.add(cust_trainSessTable);
		
		JTableHeader cust_trainSessTableHeader = cust_trainSessTable.getTableHeader();
		cust_trainSessTableHeader.setBounds(181, 0, 807, 20);
		cust_trainSessPanel.add(cust_trainSessTableHeader);
		
		JLabel cust_trainSesslabel = new JLabel("Training sesseions :");
		cust_trainSesslabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cust_trainSesslabel.setBounds(54, 10, 262, 46);
		cust_trainSessPanel.add(cust_trainSesslabel);
		
		JButton cust_trainSessBackbotton = new JButton("Back");
		cust_trainSessBackbotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		cust_trainSessBackbotton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cust_trainSessBackbotton.setBounds(99, 495, 140, 29);
		cust_trainSessPanel.add(cust_trainSessBackbotton);
		
		JButton payBotton = new JButton("Pay");
		payBotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "name_527830163244599");
			}
		});
		payBotton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		payBotton.setBounds(690, 495, 108, 29);
		cust_trainSessPanel.add(payBotton);
		
		JPanel payPanel = new JPanel();
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
		Image image3 = new ImageIcon(this.getClass().getResource("payment-creditcard-visa-icon.png")).getImage();
		visaImage.setIcon(new ImageIcon(image3));
		visaImage.setBounds(540, 57, 214, 100);
		payPanel.add(visaImage);
		
		JPanel custPersonalPanel = new JPanel();
		contentPane.add(custPersonalPanel, "Presonal_Details");
		custPersonalPanel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Presonal Details ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(247, 27, 182, 25);
		custPersonalPanel.add(lblNewLabel_2);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(81, 93, 96, 20);
		custPersonalPanel.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(81, 152, 96, 20);
		custPersonalPanel.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(81, 205, 96, 20);
		custPersonalPanel.add(textField_7);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(81, 262, 96, 20);
		custPersonalPanel.add(textField_8);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(81, 330, 96, 20);
		custPersonalPanel.add(textField_9);
		
		JLabel lblNewLabel_3 = new JLabel("name");
		lblNewLabel_3.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(10, 96, 45, 13);
		custPersonalPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("DOB");
		lblNewLabel_3_1.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblNewLabel_3_1.setBounds(10, 153, 45, 13);
		custPersonalPanel.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("phone");
		lblNewLabel_3_2.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblNewLabel_3_2.setBounds(10, 206, 45, 19);
		custPersonalPanel.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_3 = new JLabel("email");
		lblNewLabel_3_3.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblNewLabel_3_3.setBounds(10, 263, 45, 13);
		custPersonalPanel.add(lblNewLabel_3_3);
		
		JLabel lblNewLabel_3_4 = new JLabel("Gender");
		lblNewLabel_3_4.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblNewLabel_3_4.setBounds(10, 331, 61, 13);
		custPersonalPanel.add(lblNewLabel_3_4);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("photo");
		lblNewLabel_3_1_1.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblNewLabel_3_1_1.setBounds(442, 168, 61, 17);
		custPersonalPanel.add(lblNewLabel_3_1_1);
		
		JLabel lblNewLabel_3_4_1 = new JLabel("Highet");
		lblNewLabel_3_4_1.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblNewLabel_3_4_1.setBounds(10, 388, 61, 19);
		custPersonalPanel.add(lblNewLabel_3_4_1);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(81, 387, 96, 20);
		custPersonalPanel.add(textField_11);
		
		JLabel lblNewLabel_3_4_1_1 = new JLabel("Wight");
		lblNewLabel_3_4_1_1.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblNewLabel_3_4_1_1.setBounds(235, 388, 61, 19);
		custPersonalPanel.add(lblNewLabel_3_4_1_1);
		
		textField_12 = new JTextField();
		textField_12.setColumns(10);
		textField_12.setBounds(285, 388, 96, 20);
		custPersonalPanel.add(textField_12);
		
		JButton btnNewButton_2 = new JButton("update");
		btnNewButton_2.setBounds(379, 484, 85, 21);
		custPersonalPanel.add(btnNewButton_2);
		
		JButton btnNewButton_2_1 = new JButton("Back");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		btnNewButton_2_1.setBounds(55, 484, 85, 21);
		custPersonalPanel.add(btnNewButton_2_1);
		
		JLabel profile = new JLabel("");
		Image image2 = new ImageIcon(this.getClass().getResource("profile-icon.png")).getImage();
		profile.setIcon(new ImageIcon(image2));
		profile.setBounds(432, 74, 109, 80);
		custPersonalPanel.add(profile);
		
		JPanel paymentdetailsPanel = new JPanel();
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
	}
}
