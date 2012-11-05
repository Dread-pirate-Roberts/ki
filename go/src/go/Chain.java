/**
 *@author: David Buebe
 *@date:Oct 18, 2012
 */
package go;


import java.util.ArrayList;

/**
 */
public class Chain {

	public class location{
		protected int x,y;
		
		public location(){}
		public location(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public boolean equals(location arg)
		{
			boolean ret = true;
			
			if(this.x != arg.x || this.y != arg.y)
				ret = false;
			
			return ret;
		}
	
	}
	
	protected ArrayList<location> list;
	
	
	public Chain()
	{
		this.list = new ArrayList<location>();	
	}
	
	public void add(location l)
	{
		this.list.add(l);
	}
	
	public void add(int x, int y)
	{
		location l = new location();
		l.x = x;
		l.y = y;
		
		this.list.add(l);
	}
	
	
	public boolean contains(int x, int y)
	{
		location l = new location(x,y);
		return this.contains(l);
	}
	
	public boolean contains(location arg)
	{
		boolean ret = false;
		
		for(location l: this.list)
		{
			if(l.equals(arg))
				ret = true;
		}
		
		return ret;
	}
	
}
