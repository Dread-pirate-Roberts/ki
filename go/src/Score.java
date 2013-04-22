/**
 *@author: David Buebe
 *@date:Feb 6, 2013
 */


import java.util.ArrayList;

import go.Intersection.piece;
/**
 */
@SuppressWarnings("unused")
public class Score {
	
	protected Board board;
	
	
	public Score(){}
	
	public Score(Board rhs)
	{
		this.board = new Board(rhs);
		this.set_influence();
	}
	
	public piece get_team(int i, int j)
	{
		return this.board.source[i][j].team;
	}

	public boolean is_white(int i, int j)
	{
		return this.board.is_white(i,j);
	}
	
	public boolean is_black(int i, int j)
	{
		return this.board.is_black(i,j);
	}
	
	public boolean is_free(int i, int j)
	{
		return this.board.is_free(i,j);
	}
	
	public boolean is_edge(int i, int j)
	{
		return this.board.is_edge(i,j);
	}
	
	public boolean is_visited(int i, int j)
	{
		return this.board.source[i][j].get_visited();
	}
	
	public void set_visited(int i, int j)
	{
		this.board.source[i][j].visited();
	}
	
	public void set_unvisited(int i, int j)
	{
		this.board.source[i][j].unvisited();
	}
	
	public boolean in_range(int i, int j)
	{
		boolean ret = i >= 0;
		ret &= i < this.board.size;
		ret &= j >= 0;
		ret &= j < this.board.size;
		return ret;
	}
	
	public void board_unvisted()
	{
		for(Intersection j[] : this.board.source)
		{
			for( Intersection i: j)
				i.unvisited();
		}
	}
	
	protected void set_influence()
	{
		this.clear_influence();
		this.board_unvisted();
		
		for (int i = 0; i < this.board.size; i++)
		{
			for( int j = 0; j < this.board.size; j++)
			{
				this.set_piece_influence(i,j);
				this.board_unvisted();
			}
		}
	}
	
	protected void set_piece_influence(int i, int j)
	{
		piece p = this.get_team(i,j);
		
		if (p == piece.white)
		{
			this.set_white_influence(i,j);
		}
		else if ( p == piece.black)
		{
			this.set_black_influence(i,j);
		}
		
	}
	
	protected void set_white_influence_floodfill(int i, int j, int influ, int depth)
	{
		
		//this.update_white_influence_dist(i, j, influ);
		this.increase_white_influence(i, j);
		
		if(depth > 0)
		{
			
			if(this.is_free(i - 1, j))
				this.set_white_influence_floodfill(i - 1, j, influ + 1, depth - 1);
			if(this.is_free(i, j - 1))
				this.set_white_influence_floodfill(i, j - 1, influ + 1, depth - 1);
			if(this.is_free(i + 1, j))
				this.set_white_influence_floodfill(i + 1, j, influ + 1, depth - 1);
			if(this.is_free(i, j + 1))
				this.set_white_influence_floodfill(i, j + 1, influ + 1, depth - 1);
		}
	}
	
	
	protected void set_black_influence_floodfill(int i, int j, int influ, int depth)
	{
		//this.update_black_influence_dist(i, j, influ);
		this.increase_black_influence(i, j);
		
		if(depth > 0)
		{
		
		if(this.is_free(i - 1, j))
			this.set_black_influence_floodfill(i - 1, j, influ + 1,depth - 1);
		if(this.is_free(i, j - 1))
			this.set_black_influence_floodfill(i, j - 1, influ + 1, depth - 1);
		if(this.is_free(i + 1, j))
			this.set_black_influence_floodfill(i + 1, j, influ + 1, depth - 1);
		if(this.is_free(i, j + 1))
			this.set_black_influence_floodfill(i, j + 1, influ + 1, depth - 1);
		}
	}
	
	protected void set_white_influence(int i, int j)
	{
		this.set_white_influence_floodfill(i, j, 0, 5);
	
	}
	
	protected void set_black_influence(int i, int j)
	{
		
		this.set_black_influence_floodfill(i, j, 0, 5);

	}
	
	
	protected void update_white_influence_dist(int i, int j, int influence)
	{
		this.board.source[i][j].update_white_influence(influence);
	}
	
	protected void update_black_influence_dist(int i, int j, int influence)
	{
		this.board.source[i][j].update_black_influence(influence);
	}
	
	protected void increase_black_influence(int i, int j)
	{
		this.board.source[i][j].inc_black_influence();
	}
	
	protected void increase_white_influence(int i, int j)
	{
		this.board.source[i][j].inc_white_influence();
	}
	
	
	protected double get_white_influence()
	{
		double ret = 0.0;
		
		for(Intersection i[] : this.board.source)
		{
			for(Intersection j: i)
			{
				if(j.team == piece.free)
				{
					
					/*if( j.white_influence == j.black_influence)
						ret += 0.5;
					else if(j.white_more_influence())
						ret++;*/
					
					if(j.white_influence > j.black_influence)
						ret++;
					else if(j.white_influence == j.black_influence)
						ret += 0.5;
				}
			}
		}
		
		return ret;
	}
	
	
	protected double get_black_influence()
	{
		double ret = 0.0;
		
		for(Intersection i[] : this.board.source)
		{
			for(Intersection j: i)
			{
				if(j.team == piece.free)
				{
					
					 /*if( j.black_influence == j.white_influence)
						ret += 0.5;
					else if(j.black_more_influence())
						ret++;*/
					
					if(j.black_influence > j.white_influence)
						ret++;
					else if(j.black_influence == j.white_influence)
						ret += 0.5;
				}
			}
		}
		
		return ret;
	}
	
	protected double get_black_territory()
	{
		double ret = get_black_influence();
		return ret - this.board.black_prisoners;
	}
	
	protected double get_white_territory()
	{
		double ret = get_white_influence();
		return ret - this.board.white_prisoners;
	}
	
	protected double get_white_area()
	{
		return get_white_influence() + this.board.white_stones;
	}
	
	protected double get_black_area()
	{
		return get_black_influence() + this.board.black_stones;
	}
	
	
	protected void clear_influence()
	{
		for (Intersection i[]: this.board.source)
		{
			for(Intersection j: i)
			{
				j.black_influence = 0;
				j.white_influence = 0;
			}
		}
	}
}
	


