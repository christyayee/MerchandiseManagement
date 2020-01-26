import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class ItemWindow extends JFrame
{
	private Item myItem;
	
	//constructor, requires known item 
	public ItemWindow(Item item) 
	{
		myItem = item;
		initializeGUI();
	}
	
	//post-condition: all GUI elements have been initialized, necessary action events of GUI elements have been specified
	private void initializeGUI()
	{
		getContentPane().setLayout(null);
		
		//item name label
		JLabel nameLbl = new JLabel(myItem.getName());
		nameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		nameLbl.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		nameLbl.setBounds(24, 11, 387, 41);
		getContentPane().add(nameLbl);
		
		//current stock label
		JLabel stockLbl = new JLabel("Current Stock: ");
		stockLbl.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		stockLbl.setBounds(24, 82, 120, 14);
		getContentPane().add(stockLbl);
		
		//notification minimum label
		JLabel minimumLbl = new JLabel("Notification \r\nMinimum:");
		minimumLbl.setHorizontalAlignment(SwingConstants.LEFT);
		minimumLbl.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		minimumLbl.setBounds(24, 107, 175, 26);
		getContentPane().add(minimumLbl);
		
		//item type label
		JLabel typeLbl = new JLabel("Item Type: ");
		typeLbl.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		typeLbl.setBounds(24, 145, 120, 14);
		getContentPane().add(typeLbl);
		
		//comments label
		JLabel commentsLbl = new JLabel("Comments: ");
		commentsLbl.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		commentsLbl.setBounds(24, 176, 120, 14);
		getContentPane().add(commentsLbl);
		
		//current stock answer label
		JLabel stockAnswerLbl = new JLabel("" + myItem.getCurrentStock());
		stockAnswerLbl.setBounds(209, 83, 126, 14);
		getContentPane().add(stockAnswerLbl);
		
		//notification minimum answer label
		JLabel minimumAnswerLbl = new JLabel("" + myItem.getMinimum());
		minimumAnswerLbl.setBounds(209, 114, 126, 14);
		getContentPane().add(minimumAnswerLbl);
		
		//comments answer label
		JLabel commentAnswerLbl = new JLabel(myItem.getComment());
		commentAnswerLbl.setVerticalAlignment(SwingConstants.TOP);
		commentAnswerLbl.setBounds(209, 177, 202, 68);
		getContentPane().add(commentAnswerLbl);
		
		//item type answer label
		JLabel typeAnswerLbl = new JLabel(myItem.getItemType());
		typeAnswerLbl.setBounds(209, 146, 126, 14);
		getContentPane().add(typeAnswerLbl);
		
		//menu button
		JButton menuBtn = new JButton("MENU");
		menuBtn.setForeground(Color.BLACK);
		menuBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		menuBtn.setBackground(SystemColor.activeCaption);
		menuBtn.setBounds(349, 249, 75, 38);
		getContentPane().add(menuBtn);
		menuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				MainMenu menu = new MainMenu();
				dispose();
			}
		});
		
		//edit button
		JButton editBtn = new JButton("EDIT");
		editBtn.setForeground(Color.BLACK);
		editBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		editBtn.setBackground(SystemColor.activeCaption);
		editBtn.setBounds(264, 249, 75, 38);
		getContentPane().add(editBtn);
		editBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				EditItemWindow edit = new EditItemWindow(myItem);
				dispose();
			}
		});
		
		//delete button
		JButton deleteBtn = new JButton("DELETE");
		deleteBtn.setForeground(Color.BLACK);
		deleteBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		deleteBtn.setBackground(SystemColor.activeCaption);
		deleteBtn.setBounds(167, 249, 87, 38);
		getContentPane().add(deleteBtn);
		deleteBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DeleteVerification pop = new DeleteVerification(myItem);
			}
		});
		
		setSize(450, 330);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}