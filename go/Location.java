/**
 *@author: David Buebe
 *@date:Jan 24, 2013
 */

	public class Location{
		protected int x,y;
		
		public Location(){}
		public Location(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public Location(Location l){
			this(l.x, l.y);
		}
		
		public boolean equals(Location arg) {
			if(this.x == arg.x && this.y == arg.y) {
				return true;
			}
			
			return false;
		}
	}