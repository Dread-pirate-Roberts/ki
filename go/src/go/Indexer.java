/**
 *@author: David Buebe
 *@date:Oct 23, 2012
 */
package go;

/**
 */
public class Indexer {
	
	protected boolean source[][];
	protected Chain current_chain;
	
	public Indexer(){}
	
	public Indexer(int x, int y)
	{
		this.source = new boolean[x][y];
		this.all_false();
	}
	
	
	public void all_false()
	{
		for(boolean i[] : this.source)
		{
			for(boolean j : i)
				j = false;
		}
	}
	
	public boolean get(int x, int y)
	{
		return this.source[x][y];
	}
	
	public void set_true(int x, int y)
	{
		this.source[x][y] = true;
	}

}
