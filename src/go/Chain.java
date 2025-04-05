/**
 *@author: David Buebe
 *@date:Oct 18, 2012
 */

package go;

import java.io.Serializable;
import java.util.ArrayList;


public class Chain implements Serializable{
	
	private static final long serialVersionUID = -7867335357759649093L;
	protected ArrayList<Location> locationList;
	
	public Chain() {
		locationList = new ArrayList<Location>();
	}
	
	public Chain(Chain chain) {
		locationList = new ArrayList<Location>(chain.locationList);
	}
	
	public void add(Location location) {
		locationList.add(location);
	}
	
	public void add(int x, int y) {
		locationList.add(new Location(x,y));
	}
	
	public boolean contains(int x, int y) {
		return contains(new Location(x,y));
	}
	
	public boolean contains(Location arg) {
		return locationList.contains(arg);
	}
}
