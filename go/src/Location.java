/**
 *@author: David Buebe
 *@date:Jan 24, 2013
 */



/**
 */

	public class Location{
		protected int x,y;
		
		public Location(){}
		public Location(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public Location(Location l)
		{
			this.x = l.x;
			this.y = l.y;
		}
		
		public boolean equals(Location arg)
		{
			boolean ret = true;
			
			if(this.x != arg.x || this.y != arg.y)
				ret = false;
			
			return ret;
		}
	
	}

