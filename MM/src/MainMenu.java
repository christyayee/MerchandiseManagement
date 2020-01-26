import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;


public class MainMenu extends JFrame
{
	private JTextField searchTF;
	private JTable notificationJTable;
	private JTable mainJTable;
	private DefaultListModel<Item> notificationList;
	private DefaultListModel<Item> masterList;
	
	//constructor
	public MainMenu() 
	{
		masterList = loadMasterList();
		notificationList = loadNotificationsList();
		initializeGUI();
	}
	
		//post-condition: all GUI elements have been initialized, necessary action events of GUI elements have been specified
		private void initializeGUI()
		{
			getContentPane().setLayout(null);
			
			//scroll pane for notificationsJTable
			JScrollPane notificationsScroll = new JScrollPane();
			notificationsScroll.setBounds(125, 39, 325, 120);
			
			//create data array for notificationsJTable
			String[][] data2 = new String[notificationList.size()][2];
			for (int i = 0; i < notificationList.size(); i++)
			{
				data2[i][0] = notificationList.get(i).getName();
				data2[i][1] = notificationList.get(i).getItemType();
			}
			
			//notification JTable
			notificationJTable = new JTable();
			notificationJTable.setForeground(Color.RED);
			notificationJTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
			notificationJTable.setModel(new DefaultTableModel(data2, new String[] {"Item Name", "Item Type"})
			{
				boolean[] columnEditables = new boolean[] {
					false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			notificationJTable.setOpaque(false);
			notificationJTable.setBounds(125, 39, 325, 68);
			notificationJTable.setShowVerticalLines(false);
			notificationJTable.setShowGrid(false);
			notificationJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			notificationsScroll.setViewportView(notificationJTable);
			getContentPane().add(notificationsScroll);
			notificationJTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked (MouseEvent event) {
					if (event.getClickCount() == 1 && mainJTable.getSelectedRow() != -1)//mainJTable has selected
								mainJTable.clearSelection();
					else if (event.getClickCount() > 1) //double-click detected
					{
						int index = notificationJTable.getSelectedRow();
						String name = (String)notificationJTable.getValueAt(index, 0);
						String type = (String)notificationJTable.getValueAt(index, 1);
						Item item = findItem(name, type);
						ItemWindow entry = new ItemWindow(item);
						dispose();
					}
				} 
			});
			
			//scroll pane for mainJTable
			JScrollPane mainScroll = new JScrollPane();
			mainScroll.setBounds(125, 196, 325, 255);
			getContentPane().add(mainScroll);
			
			//create data array for mainJTable
			String[][] data = new String[masterList.size()][2];
			for (int i = 0; i < masterList.size(); i++)
			{
				data[i][0] = masterList.get(i).getName();
				data[i][1] = masterList.get(i).getItemType();
			}
			
			//main JTable
			mainJTable = new JTable();
			mainJTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
			mainJTable.setModel(new DefaultTableModel(data, new String[] {"Item Name", "Item Type"}) {
				boolean[] columnEditables = new boolean[] {
					false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			mainJTable.setOpaque(false);
			mainJTable.setShowGrid(false);
			mainJTable.setShowVerticalLines(false);
			mainJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			mainScroll.setViewportView(mainJTable);
			mainJTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked (MouseEvent event) {
					if (event.getClickCount() == 1 && notificationJTable.getSelectedRow() != -1)//notificationJTable has selected
								notificationJTable.clearSelection();
					else if (event.getClickCount() > 1) //double-click detected
					{
						int index = mainJTable.getSelectedRow();
						String name = (String)mainJTable.getValueAt(index, 0);
						String type = (String)mainJTable.getValueAt(index, 1);
						Item item = findItem(name, type);
						ItemWindow entry = new ItemWindow(item);
						dispose();
					}
				} 
			});
			
			//error label if delete or edit button is clicked but no item is selected
			JLabel selectErrorLbl = new JLabel("Please select an item to edit or delete.");
			selectErrorLbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
			selectErrorLbl.setVisible(false);
			selectErrorLbl.setForeground(SystemColor.textHighlight);
			selectErrorLbl.setBounds(125, 451, 277, 20);
			getContentPane().add(selectErrorLbl);
			
			//notification list label
			JLabel lblNotificationsList = new JLabel("Notifications List");
			lblNotificationsList.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
			lblNotificationsList.setBounds(126, 15, 252, 25);
			getContentPane().add(lblNotificationsList); 
			
			//search bar label
			JLabel lblSearchBar = new JLabel("Search Item:");
			lblSearchBar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
			lblSearchBar.setBounds(20, 169, 109, 25);
			getContentPane().add(lblSearchBar);
			
			//search text field
			searchTF = new JTextField();
			searchTF.setBounds(125, 169, 253, 20);
			searchTF.setColumns(10);
			getContentPane().add(searchTF);
			
			//delete button
			JButton deleteBtn = new JButton("DELETE");
			deleteBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
			deleteBtn.setForeground(SystemColor.controlText);
			deleteBtn.setBackground(SystemColor.activeCaption);
			deleteBtn.setBounds(20, 294, 79, 38);
			getContentPane().add(deleteBtn);
			deleteBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					selectErrorLbl.setVisible(false);
					int index = mainJTable.getSelectedRow();
					if (index != -1)
					{
						//row from mainJTable is selected
						String name = (String)mainJTable.getValueAt(index, 0);
						String type = (String)mainJTable.getValueAt(index, 1);
						Item deleteItem = findItem(name, type);
						DeleteVerification pop = new DeleteVerification(deleteItem);
						dispose();
					}
					else if (notificationJTable.getSelectedRow() != -1) 
					{
						//row from notificationJTable is selected
						index = notificationJTable.getSelectedRow();
						String name = (String)notificationJTable.getValueAt(index, 0);
						String type = (String)notificationJTable.getValueAt(index, 1);					
						Item deleteItem = findItem(name, type);
						DeleteVerification pop = new DeleteVerification(deleteItem);
						dispose();
					}
					else //nothing is selected
					{
						selectErrorLbl.setVisible(true);
					}
				}
			});
			
			//add button			
			JButton addBtn = new JButton("ADD");
			addBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
			addBtn.setForeground(Color.BLACK);
			addBtn.setBackground(SystemColor.activeCaption);
			addBtn.setBounds(20, 196, 79, 38);
			getContentPane().add(addBtn);
			addBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) 
				{
					AddItemWindow add = new AddItemWindow();
					dispose();
				}
			});
			
			//edit button
			JButton editBtn = new JButton("EDIT");
			editBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
			editBtn.setForeground(Color.BLACK);
			editBtn.setBackground(SystemColor.activeCaption);
			editBtn.setBounds(20, 245, 79, 38);
			getContentPane().add(editBtn);
			editBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e)
				{
					selectErrorLbl.setVisible(false);
					int index = mainJTable.getSelectedRow();
					if (index != -1)
					{
						//row from mainJTable is selected
						String name = (String)mainJTable.getValueAt(index, 0);					
						String type = (String)mainJTable.getValueAt(index, 1);	
						Item editItem = findItem(name, type);
						EditItemWindow edit = new EditItemWindow(editItem);
						dispose();
					}
					else if (notificationJTable.getSelectedRow() != -1)
					{
						//row from notificationJTable is selected
						index = notificationJTable.getSelectedRow();
						String name = (String)notificationJTable.getValueAt(index, 0);					
						String type = (String)notificationJTable.getValueAt(index, 1);	
						Item editItem = findItem(name, type);
						EditItemWindow edit = new EditItemWindow(editItem);
						dispose();
					}
					else //nothing is selected
					{
						selectErrorLbl.setVisible(true);
					}
				}
			});
			
			//exit button
			JButton exitBtn = new JButton("EXIT");
			exitBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
			exitBtn.setForeground(Color.BLACK);
			exitBtn.setBackground(SystemColor.activeCaption);
			exitBtn.setBounds(20, 20, 79, 38);
			getContentPane().add(exitBtn);
			exitBtn.addMouseListener(new MouseAdapter() {
				@Override
				 public void mouseClicked(MouseEvent e) 
				{
					dispose();
				}
			});
			
			//search button
			JButton searchBtn = new JButton("SEARCH");
			searchBtn.setForeground(Color.BLACK);
			searchBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 8));
			searchBtn.setBackground(SystemColor.activeCaption);
			searchBtn.setBounds(381, 169, 69, 20);
			getContentPane().add(searchBtn);
			searchBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					search();
				}
			});
			
			setSize(480, 500);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setResizable(false);
			setLocationRelativeTo(null);
			setVisible(true);
		}
		
		//post-condition: returns DefaultListModel representation of all items saved in all files
		private DefaultListModel<Item> loadMasterList()
		{
			DefaultListModel<Item> apparel = load("apparel");
			DefaultListModel<Item> foodDrink = load("food and drink");
			DefaultListModel<Item> miscellaneous = load("miscellaneous");
			DefaultListModel<Item> souvenir = load("souvenir");
			
			DefaultListModel<Item> masterList = new DefaultListModel<Item>();
			for (int i = 0; i < apparel.size(); i++)
				masterList.addElement(apparel.get(i));
			
			for (int i = 0; i < foodDrink.size(); i++)
				masterList.addElement(foodDrink.get(i));

			for (int i = 0; i < miscellaneous.size(); i++)
				masterList.addElement(miscellaneous.get(i));

			for (int i = 0; i < souvenir.size(); i++)
				masterList.addElement(souvenir.get(i));
			return masterList;
		}
		
		//post-condition: returns DefaultListModel representation of all items saved in specified item type file
		private DefaultListModel<Item> load(String type)
		{
			DefaultListModel<Item> list = new DefaultListModel<Item>(); 
			File file = new File(type+".txt");//file is named after item type
			try {
				if (!file.exists())
					file.createNewFile();
				}
			catch (IOException e){
				e.printStackTrace();
			}
			try 
			{
				Scanner scanner = new Scanner(file);
				while (scanner.hasNextLine()) 
				{
					String name = scanner.nextLine();
					int currentStock  = Integer.parseInt(scanner.nextLine());
					int minimum = Integer.parseInt(scanner.nextLine());
					String comment = scanner.nextLine();
					list.addElement(new Item(name, currentStock, minimum, type, comment));
					if (scanner.hasNextLine())
						scanner.nextLine();			
				}
				scanner.close();
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			return list;
		}

		//post-condition: returns DefaultListModel representation of all items (from masterList) with a 
		//specified stock equal to or less than specified notification minimum
		private DefaultListModel<Item> loadNotificationsList()
		{
			DefaultListModel<Item> notificationsList = new DefaultListModel<Item>();
			for (int i = 0; i < masterList.size(); i++)
			{
				Item item = masterList.get(i);
				if (item.getCurrentStock() <= item.getMinimum())
				notificationsList.addElement(item);
			}
			return notificationsList;
		}
		
		//post-condition: updates mainJTable to display only items (from masterList) whose name contains the String 
		//typed in search text field (case insensitive)
		private void search()
		{
			String search = searchTF.getText();
			DefaultListModel<Item> searchList = new DefaultListModel<Item>();
			for (int i = 0; i < masterList.size(); i++)
			{
				String name = masterList.get(i).getName().toLowerCase();
				if (name.contains(search.toLowerCase()))
					searchList.addElement(masterList.get(i));
			}
			String[][] searchData = new String[searchList.size()][2];
			for (int i = 0; i < searchList.size(); i++)
			{
				searchData[i][0] = searchList.get(i).getName();
				searchData[i][1] = searchList.get(i).getItemType();
			}
			String[] columns = new String[2];
			columns[0] = "Item Name";
			columns[1] = "Item Type";
			mainJTable.setModel(new DefaultTableModel(searchData, new String[] {
					"Item Name", "Item Type"}) {
				boolean[] columnEditables = new boolean[] {
					false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		}
		
		//post-condition: returns Item with the same name and same type as String input or null if no such Item exists
		public Item findItem(String name, String type)
		{
			for (int i = 0; i < masterList.size(); i++)
			{
				if (name.equals(masterList.get(i).getName()) && type.equals(masterList.get(i).getItemType()))
					return masterList.get(i);
			}
			return null;
		}
		
		//post-condition: closes MainMenu window
		public void exitMain()
		{
			dispose();
		}
}


