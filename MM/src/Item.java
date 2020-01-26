public class Item 
{
	private String myName;
	private int myCurrentStock;
	private int myMinimum;
	private String myItemType;
	private String myComment;
	
	//constructor, requires all privates to be specified
	public Item(String name, int currentStock, int minimum,  String itemType, String comment)
	{
		myName = name;
		myCurrentStock = currentStock;
		myMinimum = minimum;
		myItemType = itemType;
		myComment = comment;
	}
	
	//returns myName
	public String getName()
	{
		return myName;
	}
	
	//returns myCurrentStock
	public int getCurrentStock()
	{
		return myCurrentStock;
	}
	
	//returns myMinimum
	public int getMinimum()
	{
		return myMinimum;
	}
	
	//returns myItemType
	public String getItemType()
	{
		return myItemType;
	}
	
	//returns myComment
	public String getComment()
	{
		return myComment;
	}
}



