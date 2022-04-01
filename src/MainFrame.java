import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField txtName;
	private JTextField txtDob;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtGender;
	private JTextField txtLogin;
	private JTextField txtPassword;
	private JTextField txtPhoto;
	private JTable table;
	private JTextField txtCustomer;
	private JTextField txtTrainer;
	private JTextField txtDate;
	private JTextField txtTime;
	private JTable table_1;
	private JTable table_2;
	private JTable table_3;
	private JTextField txtFeedbackComment;
	private JTable table_4;
	private JTextField txtFilteringOptions;
	private JTextField textField_2;

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
	public MainFrame() {
		setTitle("Main Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 672);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		CardLayout cl = new CardLayout(0, 0);
		contentPane.setLayout(cl);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "1");
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Authentication");
		lblNewLabel.setBounds(199, 9, 122, 14);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, "2");
		panel_1.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("register");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "3");
			}
		});
		btnNewButton_1.setBounds(180, 195, 89, 23);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Manage registration");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "4");
			}
		});
		btnNewButton_2.setBounds(139, 260, 174, 23);
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Book session");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "5");
			}
		});
		btnNewButton_3.setBounds(156, 320, 133, 23);
		panel_1.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Generate report");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "6");
			}
		});
		btnNewButton_4.setBounds(156, 375, 157, 23);
		panel_1.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("check training session");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "7");
			}
		});
		btnNewButton_5.setBounds(588, 195, 212, 23);
		panel_1.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("collect payments");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "8");
			}
		});
		btnNewButton_6.setBounds(616, 273, 184, 23);
		panel_1.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("provide feedback");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "9");
			}
		});
		btnNewButton_7.setBounds(630, 364, 151, 23);
		panel_1.add(btnNewButton_7);
		
		JButton btnNewButton = new JButton("Sign in");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		btnNewButton.setBounds(199, 238, 89, 23);
		panel.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(199, 97, 96, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("login");
		lblNewLabel_2.setBounds(203, 72, 49, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("password");
		lblNewLabel_2_1.setBounds(196, 133, 99, 14);
		panel.add(lblNewLabel_2_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(192, 158, 96, 20);
		panel.add(textField_1);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, "3");
		panel_2.setLayout(null);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Manager");
		rdbtnNewRadioButton.setBounds(119, 121, 111, 23);
		panel_2.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("trainer");
		rdbtnNewRadioButton_1.setBounds(119, 166, 111, 23);
		panel_2.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("customer");
		rdbtnNewRadioButton_2.setBounds(119, 214, 111, 23);
		panel_2.add(rdbtnNewRadioButton_2);
		
		txtName = new JTextField();
		txtName.setText("name");
		txtName.setBounds(273, 121, 96, 20);
		panel_2.add(txtName);
		txtName.setColumns(10);
		
		txtDob = new JTextField();
		txtDob.setText("DOB");
		txtDob.setBounds(273, 166, 96, 20);
		panel_2.add(txtDob);
		txtDob.setColumns(10);
		
		txtPhone = new JTextField();
		txtPhone.setText("phone");
		txtPhone.setBounds(273, 215, 96, 20);
		panel_2.add(txtPhone);
		txtPhone.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setText("email");
		txtEmail.setBounds(273, 276, 96, 20);
		panel_2.add(txtEmail);
		txtEmail.setColumns(10);
		
		txtGender = new JTextField();
		txtGender.setText("gender");
		txtGender.setBounds(273, 333, 96, 20);
		panel_2.add(txtGender);
		txtGender.setColumns(10);
		
		txtLogin = new JTextField();
		txtLogin.setText("login");
		txtLogin.setBounds(476, 122, 96, 20);
		panel_2.add(txtLogin);
		txtLogin.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setText("password");
		txtPassword.setBounds(476, 184, 96, 20);
		panel_2.add(txtPassword);
		txtPassword.setColumns(10);
		
		txtPhoto = new JTextField();
		txtPhoto.setText("photo");
		txtPhoto.setBounds(695, 122, 96, 20);
		panel_2.add(txtPhoto);
		txtPhoto.setColumns(10);
		
		JButton btnNewButton_8 = new JButton("register");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		btnNewButton_8.setBounds(528, 495, 89, 23);
		panel_2.add(btnNewButton_8);
		
		JButton btnNewButton_14 = new JButton("Back");
		btnNewButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		btnNewButton_14.setBounds(241, 495, 89, 23);
		panel_2.add(btnNewButton_14);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, "4");
		panel_3.setLayout(null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"a", "b"},
				{"c", "d"},
			},
			new String[] {
				"col 1", "col 2"
			}
		));
		table.setBounds(159, 28, 807, 403);
		panel_3.add(table);
		
		JButton btnNewButton_9 = new JButton("Save");
		btnNewButton_9.setBounds(701, 504, 89, 23);
		panel_3.add(btnNewButton_9);
		
		JButton btnNewButton_10 = new JButton("Back");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		btnNewButton_10.setBounds(316, 504, 89, 23);
		panel_3.add(btnNewButton_10);
		
		textField_2 = new JTextField();
		textField_2.setText("Filtering options");
		textField_2.setColumns(10);
		textField_2.setBounds(655, 457, 96, 20);
		panel_3.add(textField_2);
		
		JButton btnNewButton_15 = new JButton("Delete");
		btnNewButton_15.setBounds(503, 504, 89, 23);
		panel_3.add(btnNewButton_15);
		
		JPanel panel_4 = new JPanel();
		contentPane.add(panel_4, "5");
		panel_4.setLayout(null);
		
		JButton btnNewButton_11 = new JButton("back");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		btnNewButton_11.setBounds(179, 482, 89, 23);
		panel_4.add(btnNewButton_11);
		
		JButton btnNewButton_12 = new JButton("book");
		btnNewButton_12.setBounds(553, 482, 89, 23);
		panel_4.add(btnNewButton_12);
		
		txtCustomer = new JTextField();
		txtCustomer.setText("customer");
		txtCustomer.setBounds(179, 145, 96, 20);
		panel_4.add(txtCustomer);
		txtCustomer.setColumns(10);
		
		txtTrainer = new JTextField();
		txtTrainer.setText("trainer");
		txtTrainer.setBounds(172, 193, 96, 20);
		panel_4.add(txtTrainer);
		txtTrainer.setColumns(10);
		
		txtDate = new JTextField();
		txtDate.setText("date");
		txtDate.setBounds(179, 276, 96, 20);
		panel_4.add(txtDate);
		txtDate.setColumns(10);
		
		txtTime = new JTextField();
		txtTime.setText("time");
		txtTime.setBounds(172, 351, 96, 20);
		panel_4.add(txtTime);
		txtTime.setColumns(10);
		
		JPanel panel_3_1 = new JPanel();
		panel_3_1.setLayout(null);
		contentPane.add(panel_3_1, "7");
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"Session list"},
			},
			new String[] {
				"New column"
			}
		));
		table_1.setBounds(159, 28, 807, 403);
		panel_3_1.add(table_1);
		
		JButton btnNewButton_10_1 = new JButton("Back");
		btnNewButton_10_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		btnNewButton_10_1.setBounds(316, 504, 89, 23);
		panel_3_1.add(btnNewButton_10_1);
		
		JPanel panel_3_2 = new JPanel();
		panel_3_2.setLayout(null);
		contentPane.add(panel_3_2, "8");
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
				{"Choose session"},
			},
			new String[] {
				"New column"
			}
		));
		table_2.setBounds(159, 28, 807, 403);
		panel_3_2.add(table_2);
		
		JButton btnNewButton_9_2 = new JButton("Save");
		btnNewButton_9_2.setBounds(701, 504, 89, 23);
		panel_3_2.add(btnNewButton_9_2);
		
		JButton btnNewButton_10_2 = new JButton("Back");
		btnNewButton_10_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		btnNewButton_10_2.setBounds(316, 504, 89, 23);
		panel_3_2.add(btnNewButton_10_2);
		
		JButton btnNewButton_13 = new JButton("Collect payment");
		btnNewButton_13.setBounds(478, 466, 121, 23);
		panel_3_2.add(btnNewButton_13);
		
		JPanel panel_3_3 = new JPanel();
		panel_3_3.setLayout(null);
		contentPane.add(panel_3_3, "9");
		
		table_3 = new JTable();
		table_3.setModel(new DefaultTableModel(
			new Object[][] {
				{"choose the session"},
			},
			new String[] {
				"New column"
			}
		));
		table_3.setBounds(159, 28, 807, 403);
		panel_3_3.add(table_3);
		
		JButton btnNewButton_9_3 = new JButton("Save");
		btnNewButton_9_3.setBounds(632, 568, 89, 23);
		panel_3_3.add(btnNewButton_9_3);
		
		JButton btnNewButton_10_3 = new JButton("Back");
		btnNewButton_10_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		btnNewButton_10_3.setBounds(301, 568, 89, 23);
		panel_3_3.add(btnNewButton_10_3);
		
		txtFeedbackComment = new JTextField();
		txtFeedbackComment.setText("feedback comment");
		txtFeedbackComment.setBounds(426, 506, 134, 20);
		panel_3_3.add(txtFeedbackComment);
		txtFeedbackComment.setColumns(10);
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("1");
		rdbtnNewRadioButton_3.setBounds(387, 453, 43, 23);
		panel_3_3.add(rdbtnNewRadioButton_3);
		
		JRadioButton rdbtnNewRadioButton_3_1 = new JRadioButton("2");
		rdbtnNewRadioButton_3_1.setBounds(432, 453, 43, 23);
		panel_3_3.add(rdbtnNewRadioButton_3_1);
		
		JRadioButton rdbtnNewRadioButton_3_2 = new JRadioButton("3");
		rdbtnNewRadioButton_3_2.setBounds(473, 453, 43, 23);
		panel_3_3.add(rdbtnNewRadioButton_3_2);
		
		JRadioButton rdbtnNewRadioButton_3_3 = new JRadioButton("4");
		rdbtnNewRadioButton_3_3.setBounds(518, 453, 43, 23);
		panel_3_3.add(rdbtnNewRadioButton_3_3);
		
		JRadioButton rdbtnNewRadioButton_3_4 = new JRadioButton("5");
		rdbtnNewRadioButton_3_4.setBounds(563, 453, 43, 23);
		panel_3_3.add(rdbtnNewRadioButton_3_4);
		
		JPanel panel_3_4 = new JPanel();
		panel_3_4.setLayout(null);
		contentPane.add(panel_3_4, "6");
		
		table_4 = new JTable();
		table_4.setModel(new DefaultTableModel(
			new Object[][] {
				{"a", "b"},
				{"c", "d"},
			},
			new String[] {
				"New column", "New column"
			}
		));
		table_4.setBounds(159, 28, 807, 403);
		panel_3_4.add(table_4);
		
		JButton btnNewButton_9_4 = new JButton("Generate");
		btnNewButton_9_4.setBounds(701, 504, 89, 23);
		panel_3_4.add(btnNewButton_9_4);
		
		JButton btnNewButton_10_4 = new JButton("Back");
		btnNewButton_10_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "2");
			}
		});
		btnNewButton_10_4.setBounds(316, 504, 89, 23);
		panel_3_4.add(btnNewButton_10_4);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"select table"}));
		comboBox.setBounds(299, 442, 129, 22);
		panel_3_4.add(comboBox);
		
		txtFilteringOptions = new JTextField();
		txtFilteringOptions.setText("Filtering options");
		txtFilteringOptions.setBounds(682, 443, 96, 20);
		panel_3_4.add(txtFilteringOptions);
		txtFilteringOptions.setColumns(10);
	}
}
