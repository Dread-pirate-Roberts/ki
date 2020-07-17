/**
 *@author: David Buebe
 *@date:Jan 24, 2013
 */


import java.util.ArrayList;

import go.Intersection.piece;


/**
 */
public class Play {
	
	protected Location loc;
	protected Intersection inter;  
	protected ArrayList<Chain> chains;
	
	public Play(){
		this.chains = new ArrayList<Chain>();
		this.loc = new Location();
	}
	
	public Play(Play p)
	{
		this.loc = new Location(p.loc);
		this.inter = p.inter;
		this.chains = new ArrayList<Chain>(p.chains);
		
	}
	
	public Play(int x, int y, Intersection i, ArrayList<Chain> c)
	{
		this.loc = new Location(x,y);
		this.inter = new Intersection(i);
		this.chains = c;
	}
	
	public void setLocation(Location l)
	{
		this.loc = new Location(l.x,l.y);
	}
	
	public void setLocation(int x, int y)
	{
		this.loc = new Location(x,y);
	}
	
	
	public void addChain(Chain c)
	{
		this.chains.add(c);
	}

}
