import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditItemWindow extends JFrame {
	private JTextField stockTF;
	private JTextField minimumTF;
	private JTextField commentsTF;
	private JRadioButton apparelRBtn;
	private JRadioButton foodDrinkRBtn;
	private JRadioButton miscRBtn;
	private JRadioButton souvenirRBtn;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Item myItem;
	private JTextField nameTF;
	private JLabel digitErrorLbl;
	private JLabel emptyErrorLbl;
	private JLabel titleLbl;
	private JLabel existsErrorLbl;

	//default constructor
	public EditItemWindow() 
	{
	}
	
	//constructor to edit existing item
	public EditItemWindow(Item item) 
	{
		myItem = item;
		initializeGUI();
	}
	
	//post-condition: all GUI elements have been initialized, necessary action events of GUI elements have been specified
	private void initializeGUI() 
		{
			getContentPane().setLayout(null);
		
			//title label
			titleLbl = new JLabel("New Item");
			titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
			titleLbl.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
			titleLbl.setBounds(24, 11, 387, 41);
			getContentPane().add(titleLbl);

			//name text field label
			JLabel nameLbl = new JLabel("Item Name: *");
			nameLbl.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
			nameLbl.setBounds(24, 63, 144, 14);
			getContentPane().add(nameLbl);

			//stock text field label
			JLabel stockLbl = new JLabel("Current Stock: *");
			stockLbl.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
			stockLbl.setBounds(24, 96, 120, 14);
			getContentPane().add(stockLbl);

			//minimum text field label
			JLabel minimumLbl = new JLabel("Notification Minimum: *");
			minimumLbl.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
			minimumLbl.setBounds(24, 121, 186, 26);
			getContentPane().add(minimumLbl);

			//item type text field label
			JLabel typeLbl = new JLabel("Item Type: *");
			typeLbl.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
			typeLbl.setBounds(24, 159, 120, 14);
			getContentPane().add(typeLbl);

			//comments text field label
			JLabel commentsLbl = new JLabel("Comments:");
			commentsLbl.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
			commentsLbl.setBounds(24, 266, 120, 14);
			getContentPane().add(commentsLbl);
			
			//item name text field
			nameTF = new JTextField();
			nameTF.setColumns(10);
			nameTF.setBounds(209, 63, 202, 20);
			getContentPane().add(nameTF);

			//current stock text field
			stockTF = new JTextField();
			stockTF.setBounds(209, 94, 202, 20);
			getContentPane().add(stockTF);
			stockTF.setColumns(10);

			//notification minimum text field
			minimumTF = new JTextField();
			minimumTF.setColumns(10);
			minimumTF.setBounds(209, 125, 202, 20);
			getContentPane().add(minimumTF);

			//comments text field
			commentsTF = new JTextField();
			commentsTF.setColumns(10);
			commentsTF.setBounds(209, 266, 202, 77);
			getContentPane().add(commentsTF);

			//apparel type radio button
			apparelRBtn = new JRadioButton("apparel");
			apparelRBtn.setBounds(209, 156, 200, 23);
			getContentPane().add(apparelRBtn);

			//food and drink type radio button
			foodDrinkRBtn = new JRadioButton("food and drink");
			foodDrinkRBtn.setBounds(209, 182, 200, 23);
			getContentPane().add(foodDrinkRBtn);

			//miscellaneous type radio button
			miscRBtn = new JRadioButton("miscellaneous");
			miscRBtn.setBounds(209, 208, 200, 23);
			getContentPane().add(miscRBtn);

			//souvenir type radio button
			souvenirRBtn = new JRadioButton("souvenir");
			souvenirRBtn.setBounds(209, 234, 200, 23);
			getContentPane().add(souvenirRBtn);

			//combine all radio buttons into one ButtonGroup
			buttonGroup.add(apparelRBtn);
			buttonGroup.add(foodDrinkRBtn);
			buttonGroup.add(miscRBtn);
			buttonGroup.add(souvenirRBtn);

			//cancel button
			JButton cancelBtn = new JButton("CANCEL");
			cancelBtn.setForeground(Color.BLACK);
			cancelBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
			cancelBtn.setBackground(SystemColor.activeCaption);
			cancelBtn.setBounds(333, 385, 87, 38);
			getContentPane().add(cancelBtn);
			cancelBtn.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					dispose();
					MainMenu menu = new MainMenu();
				}
			});

			//save button
			JButton saveBtn = new JButton("SAVE");
			saveBtn.setForeground(Color.BLACK);
			saveBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
			saveBtn.setBackground(SystemColor.activeCaption);
			saveBtn.setBounds(241, 385, 87, 38);
			getContentPane().add(saveBtn);
			saveBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					save();
				}
			});

			//empty error label
			emptyErrorLbl = new JLabel("Please answer all required (starred) fields.");
			emptyErrorLbl.setForeground(Color.RED);
			emptyErrorLbl.setBounds(24, 343, 387, 14);
			getContentPane().add(emptyErrorLbl);
			emptyErrorLbl.setVisible(false);

			//digit error label
			digitErrorLbl = new JLabel("Please input a positive integer for stock and minimum.");
			digitErrorLbl.setForeground(Color.RED);
			digitErrorLbl.setBounds(24, 356, 387, 14);
			getContentPane().add(digitErrorLbl);
			digitErrorLbl.setVisible(false);
			
			//exists error label
			existsErrorLbl = new JLabel("An existing item with the same name already exists.");
			existsErrorLbl.setVisible(false);
			existsErrorLbl.setForeground(Color.RED);
			existsErrorLbl.setBounds(24, 369, 309, 14);
			getContentPane().add(existsErrorLbl);

			//set window title to be name of editing item
			titleLbl.setText(myItem.getName());

			//set item privates to be initially selected and displayed
			nameTF.setText(titleLbl.getText());
			stockTF.setText("" + myItem.getCurrentStock());
			minimumTF.setText("" + myItem.getMinimum());
			selectRBtn();
			
			if (!myItem.getComment().equals("no comment"))
				commentsTF.setText(myItem.getComment());		

			//delete button
			JButton deleteBtn = new JButton("DELETE");
			deleteBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					DeleteVerification pop = new DeleteVerification(myItem);
				}
			});
			deleteBtn.setForeground(Color.BLACK);
			deleteBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
			deleteBtn.setBackground(SystemColor.activeCaption);
			deleteBtn.setBounds(144, 385, 87, 38);
			getContentPane().add(deleteBtn);
			
			setSize(450, 460);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setResizable(false);
			setLocationRelativeTo(null);
			setVisible(true);
		}
	
	//post-condition: if user input is invalid, errors will display
	//				  if user input is valid, selected item's edits will be updated in its file
	public void save() 
	{
		boolean success = false;
		String tf = nameTF.getText();
		String name = "";
		if (tf.length() > 0)
			name = tf.substring(0, 1).toUpperCase() + tf.substring(1); 
		int stock = -1;
		try 
		{
			//gets stock value, checks that stock is positive integer value and displays error if not
			if (!stockTF.getText().equals(""))
				stock = Integer.parseInt(stockTF.getText());
			displayDigitError(digitErrorLbl, stock);
			
			int min = -1; 
			try 
			{
				//gets minimum value, checks that minimum is positive integer value and displays error if not
				if (!minimumTF.getText().equals(""))
					min = Integer.parseInt(minimumTF.getText());
				//if digitErrorLbl not already visible from invalid stock
				if (digitErrorLbl.isVisible() == false)
					displayDigitError(digitErrorLbl, min);
				
				String type = getRBtnType();
				
				//gets comment; if no comment is inputed, then default comment is "no comment"
				String comments;
				if (commentsTF.getText().trim().equals(""))
					comments = "no comment";
				else
					comments = commentsTF.getText();
				
				String file = type + ".txt";
				displayEmptyError(emptyErrorLbl, nameTF.getText(), stockTF.getText(), minimumTF.getText());
				//only if either item name or type is changed, is there a possibility of changing item to existing item
				if (!getRBtnType().equals(myItem.getItemType()) || name.equals(myItem.getItemType()))
						displayExistingError(existsErrorLbl, name, type);
				
				//if all user input is valid
				if (!digitErrorLbl.isVisible() && !emptyErrorLbl.isVisible() && !existsErrorLbl.isVisible())	
				{
					Item item = new Item(name, stock, min, type, comments);
					//name and type is the same (edit in same file in same location)
					if (getRBtnType().equals(myItem.getItemType()) && name.equals(myItem.getName()))
					{
						editItem(item, file);
						success = true;
						myItem = item;
					}
					//type is changed (delete from old type file, add to new type file)
					else if (!getRBtnType().equals(myItem.getItemType()))
					{
						addItem(item, file);
						deleteItem(myItem.getItemType()+ ".txt");
						success = true;
						myItem = item;
					}
					//name is changed (same file, add to different location)
					else if(!name.equals(myItem.getName()))
					{
						deleteItem(myItem.getItemType()+ ".txt");
						addItem(item, file);
						success = true;
						myItem = item;
					}
				}
				if (success)
				{
					dispose();
					MainMenu menu = new MainMenu();
				}
			} catch (NumberFormatException e) {
				digitErrorLbl.setVisible(true);
				displayEmptyError(emptyErrorLbl, nameTF.getText(), stockTF.getText(), minimumTF.getText());
			}
		} catch (NumberFormatException e) {
			digitErrorLbl.setVisible(true);
			displayEmptyError(emptyErrorLbl, nameTF.getText(), stockTF.getText(), minimumTF.getText());	
		}
	}

	//post-condition: new item data (based on correct user input) is added to correct type file; 
	//				  item data inserted to correct position so that items(and associated item data)
	//				  in file are sorted alphabetically by item name	
	public void addItem(Item item, String fileName)
	{
		try{
			//reads from specified type file
			File file = new File (fileName);
			try {
				if (!file.exists())
					file.createNewFile();
				}
			catch (IOException e){
				e.printStackTrace();
			}
			FileReader scanner = new FileReader(file);
			BufferedReader reader = new BufferedReader(scanner);
			ArrayList<String> list = new ArrayList<String>();
			try {
				//copies every line in file before new item to ArrayList<String> list
				String next = reader.readLine();
				while (next!=null && next.compareTo(item.getName())<0) 
				//next is alphabetically before name
				{
					list.add(next);//name
					list.add(reader.readLine()); //stock
					list.add(reader.readLine()); //min
					list.add(reader.readLine()); //comment
					list.add(reader.readLine()); //blank line
					next = reader.readLine();//next name
				}
				
				//adds information of new item to ArrayList<String> list
				list.add(item.getName());
				list.add(""+ item.getCurrentStock());
				list.add(""+ item.getMinimum());
				list.add(item.getComment());
				list.add(" "); //blank line
				
				//copies every following line in file to ArrayList<String> list if new item is not last
				if (next!=null)
				{
					list.add(next);
					while ((next = reader.readLine()) != null)
						list.add(next);
				}
				reader.close();
				
				//overwrites original file with every line of string saved in ArrayList<String> list
				PrintWriter writer = new PrintWriter(file);
				for (String line : list)
					writer.println(line);				
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}		
	}
	
	//post-condition: returns text of selected radio button or null if none is selected
	public String getRBtnType() 
	{
		if (apparelRBtn.isSelected())
			return apparelRBtn.getText();
		else if (foodDrinkRBtn.isSelected())
			return foodDrinkRBtn.getText();
		else if (miscRBtn.isSelected())
			return miscRBtn.getText();
		else if (souvenirRBtn.isSelected())
			return souvenirRBtn.getText();
		return null;
	}

	//post-condition: old item data is replaced with new item data (based on correct user input) in correct type file; 
	//				  inserted to correct position so that items(with following item data) are sorted alphabetically by item name
	private void editItem(Item item, String fileName)
	{//myItem = old Item to be changed
		try{
			//reads from specified type file
			File file = new File (fileName);
			FileReader scanner = new FileReader(file);
			BufferedReader reader = new BufferedReader(scanner);
			ArrayList<String> list = new ArrayList<String>();
			try {
				//copies every line in file before new data of item to ArrayList<String> list
				String next = reader.readLine();
				while (next!=null && next.compareTo(myItem.getName())<0) //next is alphabetically before name
				{
					list.add(next);//name
					list.add(reader.readLine()); //stock
					list.add(reader.readLine()); //min
					list.add(reader.readLine()); //comment
					list.add(reader.readLine()); //blank line
					next = reader.readLine();//next name
				}
				
				//adds new data of item to ArrayList<String> list and skips lines of old data of item in file
				list.add(item.getName());
				list.add(""+ item.getCurrentStock());
				list.add(""+ item.getMinimum());
				list.add(item.getComment());
				list.add(" ");//blank line]
				
				reader.readLine(); //skip old stock
				reader.readLine(); //skip old min
				reader.readLine(); //skip old comment
				reader.readLine(); //skip old blank line
				
				//copies every line in file after old data of item to ArrayList<String> list
				while ((next = reader.readLine()) != null)
				{
					list.add(next);
				}
				reader.close();
				
				//overwrites original file with every line of string saved in ArrayList<String> list
				PrintWriter writer = new PrintWriter(file);
				for (String line : list)
					writer.println(line);				
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	//post-condition: deletes item information from correct type file
	private void deleteItem(String fileName)
	{//myItem = oldItem
		try
		{	
			//reads from specified type file
			File file = new File (fileName);
			FileReader scanner = new FileReader(file);
			BufferedReader reader = new BufferedReader(scanner);
			ArrayList<String> list = new ArrayList<String>();
			try {
				//copies every line in file before editing item to ArrayList<String> list
				String next = reader.readLine();
				while (next!=null && next.compareTo(myItem.getName())<0) //next is alphabetically before name
				{
					list.add(next);//name
					list.add(reader.readLine()); //stock
					list.add(reader.readLine()); //minimum
					list.add(reader.readLine()); //comment
					list.add(reader.readLine()); //blank line
					next = reader.readLine();//next name
				}
				
				//skips lines of old item information in file
				reader.readLine(); //skip stock
				reader.readLine(); //skip minimum
				reader.readLine(); //skip comment
				reader.readLine(); //skip blank line
				
				//copies every line in file after old item information to ArrayList<String> list if old item was not last
				while ((next = reader.readLine()) != null)
				{
					list.add(next);
				}
				reader.close();
				
				//overwrites original file with every line of string saved in ArrayList<String> list
				PrintWriter writer = new PrintWriter(file);
				for (String line : list)
					writer.println(line);				
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	//post-condition: selects radio button associated with myItem's item type
	private void selectRBtn()
	{
		String type = myItem.getItemType();
		if (type.equals("apparel"))
			apparelRBtn.setSelected(true);
		else if (type.equals("food and drink"))
			foodDrinkRBtn.setSelected(true);
		else if (type.equals("miscellaneous"))
			miscRBtn.setSelected(true);
		else if (type.equals("souvenir"))
			souvenirRBtn.setSelected(true);
	}
	
	
	
	
	//post-condition: makes digitErrorLbl visible if specified num is  less than 0, 
	//				  or invisible if num is greater than or equal to 0
	public void displayDigitError(JLabel lbl, int num)
	{
		boolean hasInvalidDigit = false;
		if (num < 0)
			hasInvalidDigit = true;
		updateLbl(lbl, hasInvalidDigit);
	}
	
	//post-condition: makes emptyErrorLbl visible if text nameTF, stockTF or minimumTF text is an empty string or 
	//				  if no radio button is selected, or invisible if not 
	public void displayEmptyError(JLabel lbl, String name, String stock, String minimum)
	{
		boolean hasEmpty = false;
		if (name.equals("") ||stock.equals("") || minimum.equals("") || getRBtnType() == null)
			hasEmpty = true;
		updateLbl(lbl, hasEmpty);
	}
	
	//post-condition: returns true if item with same name and same type already exists; else returns false
	public void displayExistingError(JLabel lbl, String name, String type)
	{
		MainMenu main = new MainMenu();
		boolean isExisting = false;
		if (main.findItem(name, type)!=null)//exists
			isExisting = true;
		main.exitMain();
		updateLbl(lbl, isExisting);
	}
	
	public void updateLbl(JLabel lbl, boolean hasError)
	{
		if(hasError)
			lbl.setVisible(true);
		else
			lbl.setVisible(false);
	}
}

