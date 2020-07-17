/**
 *@author: David Buebe
 *@date:Oct 18, 2012
 */



import go.Intersection.piece;

import java.io.Serializable;
import java.util.ArrayList;

/**
 */
public class Chain implements Serializable{
	


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7867335357759649093L;
	protected ArrayList<Location> list;
	
	
	public Chain()
	{
		this.list = new ArrayList<Location>();	
	}
	
	public Chain(Chain c)
	{
		this.list = new ArrayList<Location>(c.list);
	}
	
	public void add(Location l)
	{
		this.list.add(l);
	}
	
	public void add(int x, int y)
	{
		Location l = new Location();
		l.x = x;
		l.y = y;
		
		this.list.add(l);
	}
	
	
	public boolean contains(int x, int y)
	{
		Location l = new Location(x,y);
		return this.contains(l);
	}
	
	public boolean contains(Location arg)
	{
		boolean ret = false;
		
		for(Location l: this.list)
		{
			if(l.equals(arg))
				ret = true;
		}
		
		return ret;
	}
	
}
