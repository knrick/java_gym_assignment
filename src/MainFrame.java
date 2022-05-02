import java.awt.BorderLayout;
import java.awt.EventQueue;

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
import javax.swing.JRadioButtonMenuItem;


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
		setBounds(100, 100, 1200, 672);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		CardLayout cl = new CardLayout(0, 0);
		contentPane.setLayout(cl);
		
		JPanel authPanel = new JPanel();
		contentPane.add(authPanel, "1");
		authPanel.setLayout(null);
		
		JLabel authLabel = new JLabel("Authentication");
		authLabel.setBounds(191, 25, 104, 23);
		authPanel.add(authLabel);
		
		DB db = new DB();
		try {
			Map<String, String> session = db.getSession(2);
			if (session != null)
				authLabel.setText(session.get("date"));
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
		feedbackPanelButton.setBounds(630, 364, 151, 23);
		mainMenuPanel.add(feedbackPanelButton);
		
		JButton authButton = new JButton("Sign in");
		authButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		authButton.setBounds(199, 238, 89, 23);
		authPanel.add(authButton);
		
		loginEdit = new JTextField();
		loginEdit.setBounds(199, 97, 96, 20);
		authPanel.add(loginEdit);
		loginEdit.setColumns(10);
		
		JLabel loginLabel = new JLabel("login");
		loginLabel.setBounds(203, 72, 49, 14);
		authPanel.add(loginLabel);
		
		JLabel passwordLabel = new JLabel("password");
		passwordLabel.setBounds(196, 133, 99, 14);
		authPanel.add(passwordLabel);
		
		passwordEdit = new JTextField();
		passwordEdit.setColumns(10);
		passwordEdit.setBounds(192, 158, 96, 20);
		authPanel.add(passwordEdit);
		
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
		regPhotoEdit.setBounds(695, 122, 96, 20);
		registerPanel.add(regPhotoEdit);
		regPhotoEdit.setColumns(10);
		
		JButton regButton = new JButton("register");
		regButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		regButton.setBounds(528, 495, 89, 23);
		registerPanel.add(regButton);
		
		JButton regBackButton = new JButton("Back");
		regBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		regBackButton.setBounds(241, 495, 89, 23);
		registerPanel.add(regBackButton);
		
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
	}
}
