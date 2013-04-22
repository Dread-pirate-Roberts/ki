/**
 *@author: David Buebe
 *@date:Feb 21, 2013
 */


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import go.Intersection.piece;

/**
 */
public class GameTree {
	
	Board board;
	double score;
	ArrayList<GameTree> children;
	Location loc, next;
	piece team;
	
	GameTree(Board b, piece p)
	{
		this.board = new Board(b);
		this.team = p;
		this.children = new ArrayList<GameTree>();
		this.loc = new Location();
		this.next = new Location();
	}
	
	protected void set_score()
	{
		this.score = -999999;
		
		for(GameTree g: this.children)
		{
			g.set_score();
			
			double a = this.board.get_area_score();
			double t = this.board.get_territory_score();
			
			if(this.team == piece.black)
			{
				a = 0.0 - a;
				t = 0.0 - t;
			}
			
			double s = lesser(a,t);
			
			if(s > this.score)
			{
				this.score = s;
				this.next = new Location(g.loc);
			}
			
		}
	}
	
	public Location get_next_move()
	{
		Location ret = null;
		double high_score = -99999999;
		for(GameTree g: this.children)
		{
			 if(g.average_score() > high_score)
			 {
				 high_score = g.average_score();
				 ret = new Location(g.loc);
			 }
			 
		}
		
		return ret;
	}
	
	public double average_score()
	{
		double sum = 0;
		for(GameTree g : this.children)
			sum += g.score;
		
		return sum / this.children.size();
	}
	
	public static double lesser(double a, double b)
	{
		if(a < b)
			return a;
		else
			return b;
	}
	
	public static double greater(double a, double b)
	{
		if(a > b)
			return a;
		else 
			return b;
	}
	
	protected void build_tree(int depth)
	{
		if(depth != 0)
		{
			for(int i = 0;i < 3; i++ )
			{
				this.children.add(new GameTree(this.board, this.team));
			}
			
			for(GameTree g: this.children)
			{

				int x,y;
				Date d = new Date();
				Random rand = new Random(d.getTime());
				
				boolean success;
				do{
					x = Math.abs((rand.nextInt() % g.board.size));
					y = Math.abs((rand.nextInt() % g.board.size));
					success = g.board.Move(x, y);
					
				}while(!success);
				
				g.loc = new Location(x,y);
				
				g.build_tree(depth - 1);
			}
		}
	}

}
