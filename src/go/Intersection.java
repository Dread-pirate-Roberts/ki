/**
 *@author: David Buebe
 *@date:Feb 20, 2013
 */

package go;

import java.io.Serializable;

public class Intersection implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5093527831145393912L;

	public enum piece{
		white,black,free,edge
	}
	
	protected piece team;
	protected int black_influence,white_influence;
	protected boolean visited;
	
	public Intersection()
	{
		this.team = piece.free;
		this.black_influence = 0;
		this.white_influence = 0;
		this.visited = false;
	}
	
	public Intersection(piece p)
	{
		this.team = p;
		this.black_influence = 0;
		this.white_influence = 0;
		this.visited = false;
		
	}
	
	public Intersection(Intersection rhs)
	{
		this.team = rhs.team;
		this.black_influence = rhs.black_influence;
		this.white_influence = rhs.white_influence;
		this.visited = rhs.visited;
		
	}
	
	public boolean is_free()
	{
		return this.team == piece.free;
	}
	
	public boolean is_black()
	{
		return this.team == piece.black;
	}
	
	public boolean is_white()
	{
		return this.team == piece.white;
	}
	
	public boolean is_edge()
	{
		return this.team == piece.edge;
	}
	
	public void visited()
	{
		this.visited = true;
	}
	
	public void unvisited()
	{
		this.visited = false;
	}
	
	public boolean get_visited()
	{
		return this.visited;
	}
	
	protected void update_black_influence()
	{
		this.black_influence += 1;
	}
	
	protected void update_black_influence(int i)
	{
		if(i < this.black_influence)
			this.black_influence = i;
		
	}
	
	protected void update_white_influence()
	{
		this.white_influence += 1;
	}
	protected void update_white_influence(int i )
	{
		if(i < this.white_influence)
			this.white_influence = i;
	}
	
	public boolean black_more_influence()
	{
		if(this.black_influence < this.white_influence)
			return true;
		else
			return false;
	}
	
	public boolean white_more_influence()
	{
		if(this.white_influence < this.black_influence)
			return true;
		else
			return false;
	}
	
	public void inc_white_influence()
	{
		this.white_influence++;
	}
	
	public void inc_black_influence()
	{
		this.black_influence++;
	}
	
	public boolean equal_influence()
	{
		if(this.black_influence == this.white_influence)
			return true;
		else
			return false;
	}
	
}