/**
 *@author: David Buebe	
 *@date:Oct 17, 2012
 */
package go;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 */

@SuppressWarnings("unused")
public class Board extends JPanel{
	
	
		public enum piece{
			white,black,free,edge
		}
		
	

	protected int x,y;
	protected piece source[][];
	protected piece turn;
	
	protected Indexer checked_pieces;
	
	protected int white_score,black_score;
	
	protected Board previous;
	
	protected String error;
	
	//graphics info------------
	
	protected static int BOARDER = 30;
	protected static int SIZE = 40;
	protected static int FOOTER_HEIGHT = 50;
	protected static Color BACKGROUND = new Color(212,170,104,255);
	protected static Color FOOTER = new Color(156,155,162,190);
	//------
	
	
	public Board()
	{
		this.clear_scores();
	}
	
	public Board(int x, int y)
	{
		this.x = x;this.y = y;
		this.empty_board();
		this.clear_scores();
	}
	
	public Board(Board arg)
	{
		this.copy(arg);
	}
	
	private void empty_board()
	{
		this.source = new piece[this.x][];
		
		for(int i = 0; i < this.x; i++)
		{
			this.source[i] = new piece[this.y];
			for(int j = 0 ; j < this.y; j++ )
			{
				this.source[i][j] = piece.free;
			}
		}
		
		
	}
	
	public void setSize(int i)
	{
		assert(i >0 && i < 4);
		
		switch(i)
		{
		case 1:
			this.beginner();
			break;
		case 2:
			this.intermediate();
			break;
		case 3:
			this.advanced();
			break;
		}
	}
	
	public void beginner()
	{
		this.set_size(9, 9);	
		this.empty_board();
		this.clear_scores();
	}
	
	public void intermediate()
	{
		this.set_size(13, 13);
		this.empty_board();
		this.clear_scores();
	}
	
	public void advanced() 
	{
		this.set_size(19, 19);
		this.empty_board();
		this.clear_scores();
	}
	
	
	private void set_size(int x, int y)
	{
		this.x = x;this.y = y;
		this.source = new piece[x][y];
	}
	
	private void clear_scores()
	{
		this.white_score = 0;
		this.black_score = 0;
	}
	
	//--------------------------
	
	public void paintComponent(Graphics g)
	{
		Graphics2D arg = (Graphics2D)g;
		
		arg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(arg);
		this.setBackground(Board.BACKGROUND);
		
		this.drawLines(arg);
	
		
		
		arg.setColor(Board.FOOTER);
		arg.fillRect(0, (Board.SIZE * this.y) + this.BOARDER  + 12, (Board.SIZE * this.x)+ Board.BOARDER * 2 , Board.FOOTER_HEIGHT);
		
		this.drawPieces(arg);
		
	
		String white = "White: ";
		white += this.white_score;
		
		String black = "Black: ";
		black += this.black_score;
		
		arg.setColor(Color.BLACK);
		
		arg.drawString(white, 75,(Board.BOARDER + (Board.SIZE * this.y) + Board.SIZE )  );
		arg.drawString(black, 150, (Board.BOARDER + (Board.SIZE * this.y) + Board.SIZE ));
		
		
		}
	
	
	public void drawPieces(Graphics2D arg)
	{
		for(int i = 0; i < this.x; i++)
		{
			for(int j = 0; j < this.y; j++)
			{
				if(this.source[i][j] != piece.free)
					this.drawpiece(i, j, this.source[i][j], arg);
					
			}
		}
		
		if(this.turn == piece.white)
			arg.setColor(Color.WHITE);
		else
			arg.setColor(Color.BLACK);
		
		arg.fillOval(5, (Board.BOARDER + (Board.SIZE * this.y) + Board.SIZE / 2) - 3 , Board.SIZE, Board.SIZE);
		
	}
	
	private void drawLines(Graphics2D arg)
	{
		arg.setColor(Color.BLACK);
		
		int x = Board.BOARDER;
		int y_top = Board.BOARDER;
		
		int y_bot = Board.BOARDER + ((this.y - 1) * Board.SIZE);
		
		for(int i = 0; i < this.x; i++)
		{
			arg.drawLine(x, y_top, x, y_bot);
			x += Board.SIZE;
		}
		
		int y = Board.BOARDER;
		int x_left = Board.BOARDER;
		int x_right = Board.BOARDER + ((this.x - 1) * Board.SIZE);
		
		for(int j = 0; j < this.y; j++)
		{
			arg.drawLine( x_left, y, x_right,y);
			y += Board.SIZE;
		}
		
		this.drawHandicapMarkings(arg);
		
	}
	
	private void drawHandicapMarkings(Graphics2D arg)
	{
		int start, change;
		
		if(this.x == 9){
			start = 2;change = 2;
		}
		else
		{
			start = 3;
			if(this.x == 13)
				change = 3;
			else
				change = 6;
		}
		
		for(int x = start; x < start + change *3; x += change)
		{
			for(int y = start; y < start + change * 3; y += change)
			{
				arg.fillOval((Board.BOARDER + (Board.SIZE * x)) - 4,
						(Board.BOARDER + (Board.SIZE * y) ) - 4 
					
					, 9 , 9);
			}
		}
	}
	
	public void drawpiece(int x, int y, piece p, Graphics g)
	{		
		Color c = Color.WHITE;
		
		if(p == piece.white || p == piece.black){
			g.setColor(Color.BLACK);
			g.fillOval(Board.BOARDER + (Board.SIZE * x) - Board.SIZE / 2,
					Board.BOARDER + (Board.SIZE * y) - Board.SIZE / 2
				
				, Board.SIZE , Board.SIZE );
		
			if(p == piece.white)
			{
				g.setColor(c);
				
				g.fillOval((Board.BOARDER + (Board.SIZE * x) - Board.SIZE / 2) + 2,
						(Board.BOARDER + (Board.SIZE * y) - Board.SIZE / 2) + 2
					
					, Board.SIZE - 4 , Board.SIZE - 4);
			
			}
		}
		
	} 
	
	public void get_coordinates(int x, int y)
	{
		int ret_x,ret_y;
		x -= Board.BOARDER;
		int x_dec = x % Board.SIZE;	x = x / Board.SIZE;
		
		if (x_dec < Board.SIZE / 2)
			ret_x = x;
		else
			ret_x = x+1;
		
		y -= Board.BOARDER;
		int y_dec = y % Board.SIZE;y = y / Board.SIZE; 
		
		if(y_dec < Board.SIZE / 2)
			ret_y = y;
		else
			ret_y = y+1;
		
		
		this.move(ret_x, ret_y);
		this.repaint();
	}
	


	
	
	public void move(int x, int y)
	{
		Board temp = new Board(this);
		
		Boolean success = temp.play(x, y);
		int i  = temp.clear_dead_chains(temp.turn);
		
		boolean b = false;
		if(i == 0)
		{
			temp.checked_pieces = new Indexer(temp.x,temp.y);
			b = temp.find_dead_chain(x,y);
		}
		
		
		
		if(b)
			this.suicide(x,y);
			
		else
		{
			if(success)	
				temp.next_move();		
		
			if(temp.equals(this.previous))
			{
				this.error = "You cannot repeat a previous board position!";
				System.out.print(this.error);
			}
			else
			{
				this.previous = new Board(this);
				this.copy(temp);
				this.error = "";
			}
			}
	}
	
	private void suicide(int x, int y)
	{
		this.error = "Suicide is not allowed!";
	}
	
	private void next_move()
	{
		if(this.turn == piece.white)
		{
			this.white_score++;
			this.turn = piece.black;
		}
		
		else
		{
			this.black_score++;
			this.turn = piece.white;
		}
		
	}
	
	private boolean play(int x, int y)
	{
		
		Boolean ret = this.inRange(x, y);
		
		if(ret)
		{
			if(this.get_piece(x,y) == piece.free)
			{
				this.setPiece(x, y, this.turn);
			}
			else
				ret = false;
		}
		
		return ret;
	}
	
	public boolean inRange(int x, int y)
	{
		boolean ret = (x >= 0);
		ret &= x < this.x;
		ret &= y >= 0;
		return ret & y < this.y;
	}
	
	public piece get_piece(int x, int y)
	{
		if (x >= 0 && x < this.x)
		{
			if(y >= 0 && y < this.y)
				return this.source[x][y];
			else
				return piece.edge;
		}
		
		else return piece.edge;
	}
	
	private int clear_dead_chains(piece arg)
	{
		this.checked_pieces = new Indexer(this.x, this.y);
		ArrayList<Chain> dead_chains = new ArrayList<Chain>();
		
		for(int i = 0; i < this.x; i++)
		{
			for(int j = 0; j < this.y; j++)
			{
				if(this.opponent_piece(i, j) && ! this.piece_checked(i,j))
				{
					if(this.find_dead_chain(i,j))
							dead_chains.add(this.checked_pieces.current_chain);
				}
			}
		}
		
		int acc = 0;
		
		
		for(Chain c : dead_chains)
		{
			acc++;
			this.clear_chain(c);
		}
		
		return acc;
		
	}
	
	private void clear_chain(Chain c)
	{
		for(go.Chain.location l: c.list)
		{
			this.set_free(l.x, l.y);
			
			if(this.turn == piece.white)
				this.black_score--;
			else
				this.white_score--;
		}
	}
	
	private void set_free(int x, int y)
	{
		this.source[x][y] = piece.free;
	}
	
	private boolean opponent_piece(int x, int y)
	{
		boolean ret = false;
		
		if(this.turn == piece.white && this.get_piece(x, y) == piece.black)
			ret = true;
		else if(this.turn == piece.black && this.get_piece(x, y) == piece.white)
			ret = true;
		
		return ret;
	}
	
	private boolean piece_checked(int x, int y)
	{
		return this.checked_pieces.get(x,y);
	}
	
	
	private boolean find_dead_chain(int x, int y)
	{
		this.checked_pieces.current_chain = new Chain();
		
		piece p = this.get_piece(x, y);
		return this.add_to_chain(x,y,p);
		
	}
	
	private boolean add_to_chain(int x, int y, piece p)
	{
		if(this.piece_checked(x, y))
			return true;
		
		boolean ret = true;
		this.set_checked(x, y);
		this.checked_pieces.current_chain.add(x, y);
		
		piece team = this.get_piece(x - 1, y);
		
		if(team == piece.free)
			ret &= false;
		else if(same_teams(p,team))
			ret &=  this.add_to_chain(x - 1, y, p);
		
		team = this.get_piece(x, y - 1);
		
		if(team == piece.free)
			ret &= false;
		else if(same_teams(p,team))
			ret &= this.add_to_chain(x, y - 1, p);
		
		team = this.get_piece(x + 1, y);
		
		if(team == piece.free)
			ret &= false;
		else if(same_teams(p,team))
			ret &= this.add_to_chain(x + 1, y, p);
		
		team = this.get_piece(x, y + 1);
		
		if(team == piece.free)
			ret &= false;
		else if(same_teams(p,team))
			ret &= this.add_to_chain(x, y + 1, p);
		
		
		return ret;
		
		
		
	}
	
	private static boolean opposite_teams(piece a, piece b)
	{
		if(a == piece.white && b == piece.black)
			return true;
		else if(a == piece.black && b == piece.black)
			return true;
		else 
			return false;
	}
	
	private static boolean same_teams(piece a, piece b)
	{
		if(a == piece.white && b == piece.white)
			return true;
		else if(a == piece.black && b == piece.black)
			return true;
		else
			return false;
	}
	
	private void set_checked(int x, int y)
	{
		this.checked_pieces.set_true(x, y);
	}
	
	public boolean equals(Board rhs)
	{
		boolean ret = true;
		
		if(rhs == null )
			return false;
		
		for(int i = 0; i < this.x; i++)
		{
			for(int j = 0; j < this.y; j++)
			{
				ret &= (rhs.get_piece(i, j) == this.get_piece(i, j));
			}
		}
		
		return ret;
	}
	
	private void copy(Board arg)
	{
		this.source = new piece[arg.x][arg.y];
		this.x = arg.x;
		this.y = arg.y;
		for(int i = 0; i < this.x; i++ )
		{
			for(int j = 0; j < this.y; j++)
			this.source[i][j] = arg.source[i][j];
		}
		
		this.turn = arg.turn;
		this.white_score = arg.white_score;
		this.black_score = arg.black_score;
		this.error = arg.error;
	}
	
	
	protected void handicap(int i)
	{
		assert(i >= 0);
		assert(i < 10);
		
		this.black_score += i;
		int diff;int half = (this.x / 2) ;
		
		if(this.x == 9)
			diff = 2;
		else 
			diff = 3;
		
		if(i > 0)
			this.turn = piece.white;
		
		
		
		if(i > 0)
			this.setBlack(this.x - diff - 1,diff);i--;
		if(i > 0)
			this.setBlack(diff, this.y - 1 - diff);i--;
		if(i > 0)
			this.setBlack(diff, diff);i--;
		if(i > 0)
			this.setBlack(this.x - diff - 1, this.y - diff - 1);i--;
		if(i > 0)
			this.setBlack(half,half);i--;
		if(i > 0)
			this.setBlack(half, diff);i--;
		if(i > 0)
			this.setBlack(diff, half);i--;
		if(i > 0)
			this.setBlack(half,this.y - diff - 1);i--;
		if(i > 0)
			this.setBlack(this.x - diff - 1, half);i--;
			
		
	}
	
	private void setWhite(int x, int y)
	{
		this.setPiece(x,y, piece.white);
	}
	
	private void setBlack(int x, int y)
	{
		this.setPiece(x, y, piece.black);
	}
	
	private void setPiece(int x, int y, piece p)
	{
		assert(this.inRange(x, y));
		
		this.source[x][y] = p;
	}
	
}
