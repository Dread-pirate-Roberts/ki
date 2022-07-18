/**
 *@author: David Buebe
 *@date:Oct 18, 2012
 */



import go.Intersection.piece;

import java.io.Serializable;
import java.util.ArrayList;


public class Chain implements Serializable{
	
	private static final long serialVersionUID = -7867335357759649093L;
	protected ArrayList<Location> list;
	
	public Chain() {
		this.list = new ArrayList<Location>();	
	}
	
	public Chain(Chain c) {
		this.list = new ArrayList<Location>(c.list);
	}
	
	public void add(Location l) {
		this.list.add(l);
	}
	
	public void add(int x, int y) {
		this.list.add(new Location(x,y));
	}
	
	public boolean contains(int x, int y) {
		return this.contains(new Location(x,y));
	}
	
	public boolean contains(Location arg) {
		for(Location l: this.list) {
			if(l.equals(arg)) {
				return true;
			}
		}
		
		return false;
	}
}
