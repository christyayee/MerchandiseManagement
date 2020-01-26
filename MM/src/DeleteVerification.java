import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.util.*;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteVerification extends JFrame 
{
	private Item myItem;

	public DeleteVerification(Item item) 
	{
		myItem = item;
		initializeGUI();
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		
	//post-condition: all GUI elements have been initialized, necessary action events of GUI elements have been specified
	private void initializeGUI()
	{
		getContentPane().setLayout(null);
		
		//question label
		JLabel questionLbl = new JLabel("Are you sure you want to delete the selected item?");
		questionLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		questionLbl.setBounds(18, 34, 316, 38);
		getContentPane().add(questionLbl);
		
		//yes button
		JButton yesBtn = new JButton("YES");
		yesBtn.setBackground(SystemColor.activeCaption);
		yesBtn.setBounds(89, 83, 75, 35);
		getContentPane().add(yesBtn);
		yesBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				String file = myItem.getItemType() + ".txt";
				deleteItem(file);
				dispose();
				MainMenu menu = new MainMenu();
			}
		});
		
		//no button
		JButton noBtn = new JButton("NO");
		noBtn.setBackground(SystemColor.activeCaption);
		noBtn.setBounds(201, 83, 75, 35);
		getContentPane().add(noBtn);
		noBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				dispose();
				MainMenu menu = new MainMenu();
			}
		});
		
		setSize(350, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
