import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

//extends parent EditItemWindow because shares many required methods
public class AddItemWindow extends EditItemWindow
{
	private JTextField stockTF;
	private JTextField minimumTF;
	private JTextField commentsTF;
	private JRadioButton apparelRBtn;
	private JRadioButton foodDrinkRBtn;
	private JRadioButton miscRBtn;
	private JRadioButton souvenirRBtn;
	private ButtonGroup buttonGroup;
	private Item myItem;
	private JTextField nameTF;
	private JLabel digitErrorLbl;
	private JLabel emptyErrorLbl;
	private JLabel titleLbl;
	private JLabel existsErrorLbl;
	
	//constructor
	public AddItemWindow()
	{
		super();
		myItem = null;
		initializeGUI();
	}
	
	//override parent's initializeGUI()
	//post-condition: all GUI elements have been initialized, necessary action events of GUI elements have been specified
	public void initializeGUI() 
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
		souvenirRBtn.setBounds(209, 234, 210, 23);
		getContentPane().add(souvenirRBtn);

		//combine all radio buttons into one ButtonGroup
		buttonGroup = new ButtonGroup();
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

		setSize(450, 460);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	//override parent's save()
	//post-condition: if user input is invalid, errors will display
	//				  if user input is valid, new item is added to appropriate file
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
				displayExistingError(existsErrorLbl, nameTF.getText(), type);
				
				//if all user input is valid
				if (!digitErrorLbl.isVisible() && !emptyErrorLbl.isVisible() && !existsErrorLbl.isVisible())
				{
					myItem = new Item(name, stock, min, type, comments);
					addItem(myItem, file);
					success= true;
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
	
	//Overrides with child's privates, not parent's
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
}
