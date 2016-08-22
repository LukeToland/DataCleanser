package emergingtech.lyit.ie;

import java.awt.event.*;
import java.awt.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class DataCleanserGUI {
	private JTextField txtbxCustomerID;
	private JTextField jtxtFirstName;
	private JTextField txtLastName;
	private JTextField txtAddress;	
	private JProgressBar progressBar = new JProgressBar(0,99);
	private JButton btnAddToDb = new JButton("Add to DB");
	private JTable table;

	//reference to Database
	DBConnection dbConn = new DBConnection();

	public DataCleanserGUI() {
		showGUI();//Generate GUI
	}

	//Method to generate & execute GUI and operations
	public void showGUI()
	{
		JFrame dc = new JFrame("Data Cleanser by Luke Toland");
		dc.setTitle("Data Cleanser ");
		dc.getContentPane().setLayout(null);		

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 648, 392);
		dc.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setBounds(20, 81, 235, 124);
		panel.setBounds(20, 81, 235, 124);
		tabbedPane.add(panel, "DC");
		panel.setLayout(null);

		JPanel panel2 = new JPanel();
		panel.setBounds(20, 81, 235, 124);
		panel.setBounds(20, 81, 235, 124);

		JPanel addPanel = new JPanel();
		tabbedPane.add(addPanel, "Add");
		addPanel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(54, 36, 258, 134);
		addPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(4, 2, 4, 4));

		JLabel lblNewLabel = new JLabel("Customer ID:");
		panel_1.add(lblNewLabel);

		txtbxCustomerID = new JTextField();
		panel_1.add(txtbxCustomerID);
		txtbxCustomerID.setColumns(10);

		JLabel lblFirstName = new JLabel("First Name:");
		panel_1.add(lblFirstName);

		jtxtFirstName = new JTextField();
		panel_1.add(jtxtFirstName);
		jtxtFirstName.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name:");
		panel_1.add(lblLastName);

		txtLastName = new JTextField();
		panel_1.add(txtLastName);
		txtLastName.setColumns(10);

		JLabel lblAddress = new JLabel("Address");
		panel_1.add(lblAddress);

		txtAddress = new JTextField();
		panel_1.add(txtAddress);
		txtAddress.setColumns(10);

		btnAddToDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					if(txtbxCustomerID.getText().equals("") || jtxtFirstName.getText().equals("") || txtLastName.getText().equals("") || txtAddress.getText().equals("")){
						JOptionPane.showMessageDialog (null, "Please fill all details", "DC", JOptionPane.INFORMATION_MESSAGE);
					}else{
						dbConn.insertIntoDB(Integer.parseInt(txtbxCustomerID.getText()), jtxtFirstName.getText(), txtLastName.getText(), txtAddress.getText());
					}
				}catch(IllegalArgumentException ex) {
					ex.printStackTrace();
				}catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		});
		btnAddToDb.setBounds(103, 237, 177, 43);
		addPanel.add(btnAddToDb);
		tabbedPane.add(panel2, "Database");
		panel2.setLayout(null);

		final DataLoadWorker d = new DataLoadWorker(table);
		try {
			table = new JTable(d.doInBackground());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		table.setBounds(10, 11, 619, 289);
		JScrollPane jsc = new JScrollPane(table);
		jsc.setBounds(10, 11, 619, 289);
		panel2.add(jsc);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					refresh();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		btnRefresh.setBounds(251, 331, 140, 23);
		panel2.add(btnRefresh);
		panel.setLayout(null);

		JButton btnStartProcess = new JButton("Start Process");
		btnStartProcess.setBounds(200, 256, 218, 43);
		panel.add(btnStartProcess);

		progressBar.setBounds(132, 193, 339, 23);
		panel.add(progressBar);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(132, 11, 339, 160);
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));

		final JCheckBox chckbxRemoveDuplicates = new JCheckBox("Remove duplicates");
		panel_2.add(chckbxRemoveDuplicates);

		final JCheckBox chckbxRemoveSymbols = new JCheckBox("Remove Symbols");
		panel_2.add(chckbxRemoveSymbols);

		final JCheckBox chckbxRemoveSpaces = new JCheckBox("Remove Spaces");
		panel_2.add(chckbxRemoveSpaces);

		final JCheckBox chckbxAlterStrings = new JCheckBox("Alter strings");
		panel_2.add(chckbxAlterStrings);
		btnStartProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long start = System.currentTimeMillis();
				//Alter
				if(chckbxAlterStrings.isSelected())
				{
					try {
						dbConn.alterStrings();
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
				//Spaces
				if(chckbxRemoveSpaces.isSelected())
				{
					try {
						dbConn.removeSpaces();
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}

				//Symbols
				if(chckbxRemoveSymbols.isSelected())
				{
					try {
						dbConn.removeSymbols();
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}

				//Dupicates
				if(chckbxRemoveDuplicates.isSelected())
				{
					try {
						dbConn.removeDuplicates();
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog (null, "Process took "+ ((System.currentTimeMillis()-start))+" milliseconds", "DC", JOptionPane.INFORMATION_MESSAGE);
				progressBar.setValue(100);
			}

		});


		dc.setVisible(true);
		dc.setSize(680,448);
		dc.setLocationRelativeTo(null);

	}

	//Main method
	public static void main(String [] args)
	{
		String className = getLookAndFeelClassName("Nimbus");
		try {
			UIManager.setLookAndFeel(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		new DataCleanserGUI();
	}

	//JTable
	public void refresh()
	{
		DataLoadWorker d = new DataLoadWorker(table);

		try {
			table.setModel(d.doInBackground());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getLookAndFeelClassName(String nameSnippet) {
	    LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
	    for (LookAndFeelInfo info : plafs) {
	        if (info.getName().contains(nameSnippet)) {
	            return info.getClassName();
	        }
	    }
	    return null;
	}
}
