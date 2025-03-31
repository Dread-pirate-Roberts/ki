/**
 *@author: David Buebe
 *@date:Feb 21, 2013
 */

package go;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import go.Intersection.piece;
import go.Location;

public class GameTree {
	
	Board board;
	double score;
	ArrayList<GameTree> children;
	Location loc, next;
	piece team;
	
	GameTree(Board b, piece p) {
		this.board = new Board(b);
		this.team = p;
		this.children = new ArrayList<GameTree>();
		this.loc = new Location();
		this.next = new Location();
	}
	
	protected void setScore() {
		this.score = -999999;
		
		for(GameTree gameTree: this.children) {
			gameTree.setScore();
			
			double areaScore = this.board.get_area_score();
			double territoryScore = this.board.get_territory_score();
			
			if(this.team == piece.black) {
				areaScore = 0.0 - areaScore;
				territoryScore = 0.0 - territoryScore;
			}
			
			double newScore = areaScore < territoryScore ? areaScore : territoryScore;
			
			if(newScore > this.score) {
				this.score = newScore;
				this.next = new Location(gameTree.loc);
			}
		}
	}
	
	public Location getNextMove() {
		Location ret = null;
		double high_score = -99999999;
		for(GameTree gameTree: this.children) {
			 if(gameTree.averageScore() > high_score) {
				 high_score = gameTree.averageScore();
				 ret = new Location(gameTree.loc);
			 }
		}
		
		return ret;
	}
	
	public double averageScore() {
		double sum = 0;
		for(GameTree gameTree : this.children) {
			sum += gameTree.score;
		}
		
		return sum / this.children.size();
	}
	
	protected void buildTree(int depth) {
		if(depth != 0) {
			for(int i = 0;i < 3; i++ ) {
				this.children.add(new GameTree(this.board, this.team));
			}
			
			for(GameTree gameTree: this.children) {
				int x,y;
				Random rand = new Random(new Date().getTime());
				
				boolean success;
				do {
					x = Math.abs((rand.nextInt() % gameTree.board.size));
					y = Math.abs((rand.nextInt() % gameTree.board.size));
					success = gameTree.board.Move(x, y);
					
				} while(!success);
				
				gameTree.loc = new Location(x,y);
				
				gameTree.buildTree(depth - 1);
			}
		}
	}
}
