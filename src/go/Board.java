/**
 *@author: David Buebe	
 *@date:Oct 17, 2012
 */

package go;

import go.Game.Computer;
import go.Game.newGameMenu.GameInfo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import javax.swing.JComponent;
import javax.swing.JPanel;

import go.Intersection.piece;

@SuppressWarnings("unused")
public class Board extends JPanel implements java.io.Serializable {
	private static final long serialVersionUID = -7836050700722187314L;
	protected int size;
	protected Intersection source[][];
	protected piece turn;

	protected Indexer checked_pieces;

	protected int white_stones, black_stones;

	protected int white_prisoners, black_prisoners;

	protected Board previous;

	protected String error;

	protected Computer comp;

	// graphics info------------

	protected static int BOARDER = 50;
	protected static int SIZE = 40;
	protected static int FOOTER_HEIGHT = 50;
	protected static Color BACKGROUND = new Color(212, 170, 104, 255);
	protected static Color FOOTER = new Color(218, 218, 218, 255);
	// ------

	public void setComputer(Computer computer2) {
		this.comp = computer2;
	}

	public Board() {
		this.clear_scores();
	}

	public Board(int x, int y) {
		this.size = x;
		this.size = y;
		this.empty_board();
		this.clear_scores();
	}

	public Board(Board arg) {
		this.copy(arg);
	}

	public Board(GameInfo g) {
		this.size = g.getSize();
		this.size = g.getSize();
		this.empty_board();
		this.clear_scores();
		this.handicap(g.getHandicap());
		this.comp = g.computer();
	}

	public Board(int size, int handicap, Computer comp) {
		this.size = size;
		this.size = size;
		this.empty_board();
		this.clear_scores();
		this.handicap(handicap);
		this.comp = comp;
	}

	public Board(File f) {
		try {
			FileInputStream fstream = new FileInputStream(f.getPath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean is_white(int i, int j) {
		if (this.inRange(i, j)) {
			return this.source[i][j].is_white();
		}

		return false;
	}

	public boolean is_free(int i, int j) {
		if (this.inRange(i, j)) {
			return this.source[i][j].is_free();
		}

		return false;
	}

	public boolean is_black(int i, int j) {
		if (this.inRange(i, j)) {
			return this.source[i][j].is_black();
		}

		return false;
	}

	public boolean is_edge(int i, int j) {
		return !inRange(i, j);
	}

	private void empty_board() {
		this.source = new Intersection[this.size][this.size];

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.source[i][j] = new Intersection(piece.free);
			}
		}

		this.clear_scores();
	}

	public void setSize(int i) {
		assert (i == 9 || i == 13 || i == 19);

		switch (i) {
		case 9:
			this.beginner();
			break;
		case 13:
			this.intermediate();
			break;
		case 19:
			this.advanced();
			break;
		}
	}

	public void beginner() {
		this.set_size(9, 9);
	}

	public void intermediate() {
		this.set_size(13, 13);
	}

	public void advanced() {
		this.set_size(19, 19);
	}

	private void set_size(int x, int y) {
		this.size = x;
		this.size = y;
		this.empty_board();
	}

	private void clear_scores() {
		this.white_stones = 0;
		this.black_stones = 0;
		this.white_prisoners = 0;
		this.black_prisoners = 0;
	}

	// --------------------------

	public void paintComponent(Graphics g) {
		Graphics2D arg = (Graphics2D) g;

		arg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(arg);
		this.setBackground(Board.BACKGROUND);

		this.drawLines(arg);

		arg.setColor(Board.FOOTER);
		arg.fillRect(0, (Board.SIZE * (this.size - 1)) + (2 * this.BOARDER) + 10,
				(Board.SIZE * this.size) + Board.BOARDER * 2, Board.FOOTER_HEIGHT * 2);

		this.drawPieces(arg);

		this.drawGameInfo(arg);
	}

	private void drawGameInfo(Graphics arg) {

		/*
		 * String white = "White Stones: "; white += this.white_stones;
		 * 
		 * String black = "Black Stones: "; black += this.black_stones;
		 * 
		 * arg.setColor(Color.BLACK);
		 * 
		 * arg.drawString(white, 75,(Board.BOARDER + (Board.SIZE * this.size) +
		 * Board.SIZE ) ); arg.drawString(black, 200, (Board.BOARDER + (Board.SIZE *
		 * this.size) + Board.SIZE ));
		 * 
		 * white = new String("White Prisoners: "); white += this.white_prisoners; black
		 * = new String("Black Prisoners: "); black += this.black_prisoners;
		 * 
		 * 
		 * arg.drawString(white, 75,(Board.BOARDER + (Board.SIZE * this.size) +
		 * Board.SIZE ) + 15); arg.drawString(black, 200, (Board.BOARDER + (Board.SIZE *
		 * this.size) + Board.SIZE ) + 15);
		 * 
		 * if(this.turn == piece.white) arg.setColor(Color.WHITE); else
		 * arg.setColor(Color.BLACK);
		 * 
		 * arg.fillOval(5, (Board.BOARDER + (Board.SIZE * this.size) + Board.SIZE / 2) -
		 * 3 , Board.SIZE, Board.SIZE);
		 */

		String turn;

		if (this.turn == piece.white) {
			arg.setColor(Color.WHITE);
			turn = "White to move";
		} else {
			arg.setColor(Color.BLACK);
			turn = "Black to move";
		}
		arg.fillOval(2, 2, 20, 20);

		arg.setColor(Color.BLACK);
		arg.drawString(turn, 25, 15);
	}

	public void drawPieces(Graphics2D arg) {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (!this.source[i][j].is_free())
					this.drawpiece(i, j, this.source[i][j].team, arg);
			}
		}

		arg.setColor(Color.BLACK);
		arg.fillOval(53, (this.size - 1) * Board.SIZE + (2 * Board.BOARDER) + 24, 50, 50);
		arg.setColor(Color.WHITE);
		arg.fillOval(246, (this.size - 1) * Board.SIZE + (2 * Board.BOARDER) + 24, 50, 50);

	}

	private void drawLines(Graphics2D arg) {
		arg.setColor(Color.BLACK);

		int x = Board.BOARDER;
		int y_top = Board.BOARDER;

		int y_bot = Board.BOARDER + ((this.size - 1) * Board.SIZE);

		for (int i = 0; i < this.size; i++) {
			arg.drawLine(x, y_top, x, y_bot);
			x += Board.SIZE;
		}

		int y = Board.BOARDER;
		int x_left = Board.BOARDER;
		int x_right = Board.BOARDER + ((this.size - 1) * Board.SIZE);

		for (int j = 0; j < this.size; j++) {
			arg.drawLine(x_left, y, x_right, y);
			y += Board.SIZE;
		}

		this.drawHandicapMarkings(arg);
	}

	private void drawHandicapMarkings(Graphics2D arg) {
		int start, change;

		if (this.size == 9) {
			start = 2;
			change = 2;
		} else {
			start = 3;
			if (this.size == 13) {
				change = 3;
			} else {
				change = 6;
			}
		}

		for (int x = start; x < start + change * 3; x += change) {
			for (int y = start; y < start + change * 3; y += change) {
				arg.fillOval((Board.BOARDER + (Board.SIZE * x)) - 5, (Board.BOARDER + (Board.SIZE * y)) - 5

						, 11, 11);
			}
		}
	}

	public void drawpiece(int x, int y, piece p, Graphics g) {
		Color c = Color.WHITE;

		if (p == piece.white || p == piece.black) {
			g.setColor(Color.BLACK);
			g.fillOval(Board.BOARDER + (Board.SIZE * x) - Board.SIZE / 2,
					Board.BOARDER + (Board.SIZE * y) - Board.SIZE / 2

					, Board.SIZE, Board.SIZE);

			if (p == piece.white) {
				g.setColor(c);

				g.fillOval((Board.BOARDER + (Board.SIZE * x) - Board.SIZE / 2) + 2,
						(Board.BOARDER + (Board.SIZE * y) - Board.SIZE / 2) + 2

						, Board.SIZE - 4, Board.SIZE - 4);

			}
		}
	}

	public boolean get_coordinates(int x, int y) {
		boolean ret = false;
		int ret_x, ret_y;
		x -= Board.BOARDER;
		int x_dec = x % Board.SIZE;
		x = x / Board.SIZE;

		if (x_dec < Board.SIZE / 2) {
			ret_x = x;
		} else {
			ret_x = x + 1;
		}

		y -= Board.BOARDER;
		y -= 20;
		int y_dec = y % Board.SIZE;
		y = y / Board.SIZE;

		if (y_dec < Board.SIZE / 2) {
			ret_y = y;
		} else {
			ret_y = y + 1;
		}

		if (this.inRange(ret_x, ret_y)) {
			ret = this.Move(ret_x, ret_y);
			this.repaint();

			/*
			 * if(ret) {
			 * 
			 * if(this.comp != Computer.none) { this.computer_play(); this.repaint(); } }
			 */
		}
		return ret;
	}

	public boolean Move(int x, int y) {
		boolean ret = false;
		Board temp = new Board(this);
		
		Boolean success = temp.play(x, y);
		int i  = temp.clear_dead_chains(x,y);
		
		boolean b = false;
		if(i == 0) {
			temp.checked_pieces = new Indexer(temp.size,temp.size);
			b = temp.find_dead_chain(x,y);
		}
		
		if(b){
			this.suicide(x,y);
		} else {
			/*if(success)	{
				temp.next_move();
			}
			*/
			if(temp.equals(this.previous)){ //temp.repeat(this)) 
				this.error = "You cannot repeat a previous board position!";
				System.out.print(this.error);
			} else if(success) {
				temp.next_move();
				this.previous = new Board(this);
				this.copy(temp);
				this.error = "";
				ret = true;
			}
		}

	return ret;

	}

	/*
	 * protected boolean repeat(Board b) { boolean ret = false;
	 * if(!b.history.isEmpty()) { b.undo(); ret ^= this.equals(b); }
	 * 
	 * if(!b.history.isEmpty()) { b.undo(); ret ^= this.equals(b); }
	 * 
	 * if(!b.history.isEmpty()) { b.undo(); ret ^= this.equals(b); } return ret;
	 * 
	 * 
	 * }
	 */

	private void suicide(int x, int y) {
		this.error = "Suicide is not allowed!";
	}

	private void next_move() {
		if (this.turn == piece.white) {
			this.white_stones++;
			this.turn = piece.black;
		} else {
			this.black_stones++;
			this.turn = piece.white;
		}
	}

	private boolean play(int x, int y) {
		if (!this.inRange(x, y)) {
			return false;
		}

		if (this.is_free(x, y)) {
			this.setPiece(x, y, this.turn);
			return true;
		}

		return false;
	}

	public boolean inRange(int x, int y) {
		boolean ret = (x >= 0);

		ret &= x < this.size;
		ret &= y >= 0;

		return ret & y < this.size;
	}

	public piece get_piece(int x, int y) {
		if (this.inRange(x, y)) {
			return this.source[x][y].team;
		}

		return piece.edge;
	}

	private int clear_dead_chains(int x, int y) {
		this.checked_pieces = new Indexer(this.size, this.size);
		ArrayList<Chain> dead_chains = new ArrayList<Chain>();

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (this.opponent_piece(i, j) && !this.piece_checked(i, j)) {
					if (this.find_dead_chain(i, j)) {
						dead_chains.add(this.checked_pieces.current_chain);
					}
				}
			}
		}

		int acc = 0;

		for (Chain c : dead_chains) {
			acc++;
			if (c != null) {
				Chain temp = new Chain(c);
			}

			this.clear_chain(c);
		}

		return acc;
	}

	private void clear_chain(Chain c) {

		for (Location l : c.locationList) {
			this.set_free(l.x, l.y);

			if (this.turn == piece.white) {
				this.black_stones--;
				this.black_prisoners++;
			} else {
				this.white_stones--;
				this.white_prisoners++;
			}
		}
	}

	private void set_free(int x, int y) {
		this.source[x][y] = new Intersection(piece.free);
	}

	private boolean opponent_piece(int x, int y) {
		if (this.turn == piece.white && this.is_black(x, y)) {
			return true;
		}
		
		if(this.turn == piece.black && this.is_white(x, y)) {
			return true;
		}

		return false;
	}

	private boolean piece_checked(int x, int y) {
		return this.checked_pieces.get(x, y);
	}

	private boolean find_dead_chain(int x, int y) {
		this.checked_pieces.current_chain = new Chain();

		piece p = this.get_piece(x, y);
		return this.add_to_chain(x, y, p);
	}

	private boolean add_to_chain(int x, int y, piece p) {
		if (this.piece_checked(x, y)) {
			return true;
		}

		boolean ret = true;
		this.set_checked(x, y);
		this.checked_pieces.current_chain.add(x, y);

		piece team = this.get_piece(x - 1, y);

		if (team == piece.free) {
			ret &= false;
		} else if (same_teams(p, team)) {
			ret &= this.add_to_chain(x - 1, y, p);
		}

		team = this.get_piece(x, y - 1);

		if (team == piece.free) {
			ret &= false;
		} else if (same_teams(p, team)) {
			ret &= this.add_to_chain(x, y - 1, p);
		}

		team = this.get_piece(x + 1, y);

		if (team == piece.free) {
			ret &= false;
		} else if (same_teams(p, team)) {
			ret &= this.add_to_chain(x + 1, y, p);
		}

		team = this.get_piece(x, y + 1);

		if (team == piece.free) {
			ret &= false;
		} else if (same_teams(p, team)) {
			ret &= this.add_to_chain(x, y + 1, p);
		}

		return ret;
	}

	protected void benson() {
		
	}

	private static boolean opposite_teams(piece a, piece b) {
		if (a == piece.white && b == piece.black) {
			return true;
		} else if (a == piece.black && b == piece.black) {
			return true;
		}

		return false;
	}

	private static boolean same_teams(piece a, piece b) {
		if (a == piece.white && b == piece.white) {
			return true;
		} else if (a == piece.black && b == piece.black) {
			return true;
		}

		return false;
	}

	private void set_checked(int x, int y) {
		this.checked_pieces.set_true(x, y);
	}

	public boolean equals(Board rhs) {
		if(rhs == null ){
			return false;
		}
		
		for(int i = 0; i < this.size; i++) {
			for(int j = 0; j < this.size; j++) {
				if (rhs.get_piece(i, j) != this.get_piece(i, j)){
					return false;
				}
			}
		}
		
		return true;
	}

	private void copy(Board arg) {
		this.source = new Intersection[arg.size][arg.size];
		this.size = arg.size;
		this.size = arg.size;

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++)
				this.source[i][j] = arg.source[i][j];
		}

		this.turn = arg.turn;
		this.white_stones = arg.white_stones;
		this.black_stones = arg.black_stones;
		this.white_prisoners = arg.white_prisoners;
		this.black_prisoners = arg.black_prisoners;
		this.error = arg.error;

		this.comp = arg.comp;
	}

	protected void handicap(int i) {
		assert(i >= 0);
		assert(i < 10);
		
		this.black_stones += i;
		int diff;
		int half = (this.size / 2) ;
		
		if(this.size == 9){
			diff = 2;
		} else {
			diff = 3;
		}

		if(i>0){
			this.turn = piece.white;
		}
		
		if(i>0) {
			this.setBlack(this.size - diff - 1, diff);
			i--;
		}
		
		if(i>0) {
			this.setBlack(diff, this.size - 1 - diff);
			i--;
		}
		
		if(i>0) {
			this.setBlack(diff, diff);
			i--;
		}
		
		if(i>0) {
			this.setBlack(this.size - diff - 1, this.size - diff - 1);
			i--;
		}
		
		if(i>0) {
			this.setBlack(half, half);
			i--;
		}
		
		if(i>0) {
			this.setBlack(half, diff);
			i--;
		}
		
		if(i>0) {
			this.setBlack(diff, half);
			i--;
		}
		
		if(i>0) {
			this.setBlack(half, this.size - diff - 1);
			i--;
		}
		if(i>0) {
			this.setBlack(this.size - diff - 1, half);
			i--;
		}
	}

	private void setWhite(int x, int y) {
		this.setPiece(x,y, piece.white);
	}

	private void setBlack(int x, int y) {
		this.setPiece(x, y, piece.black);
	}

	private void setPiece(int x, int y, piece p) {
		assert(this.inRange(x, y));
		
		this.source[x][y] = new Intersection(p);
	}

	/*
	 * protected void undo() {
	 * 
	 * assert(p.loc.x < this.size);assert(p.loc.y < this.size);
	 * this.source[p.loc.x][p.loc.y] = piece.free;
	 * 
	 * piece color; if(p.team == piece.black) color = piece.white; else color =
	 * piece.black;
	 * 
	 * Chain c; for(int i = 0; i < p.chains.size(); i++) { c = p.chains.get(i);
	 * this.add(c,color);
	 * 
	 * } }
	 */

	protected void add(Chain c, piece p) {
		for( int i = 0; i < c.locationList.size(); i++) {
			this.source[c.locationList.get(i).x][c.locationList.get(i).y] = new Intersection(p);
		}
	}

	protected double get_territory_score() {
		Score s = new Score(this);
		s.set_influence();
		
		return s.get_white_territory() - (double) s.get_black_territory();
	}

	protected double get_area_score() {
		Score s = new Score(this);
		s.set_influence();

		return s.get_white_area() - (double) s.get_black_area();
	}

	protected void computer_play() {
		this.computer_tree_search();
		// this.computer_random();
		this.repaint();
	}

	protected void computer_random() {
		boolean success = false;
		Date d = new Date();
		Random rand = new Random(d.getTime());
		int x, y;

		do {
			x = Math.abs(rand.nextInt() % this.size);
			y = Math.abs(rand.nextInt() % this.size);
			success = this.Move(x, y);

		} while (!success);
	}

	protected void computer_tree_search() {
		GameTree g = new GameTree(this, this.turn);
		g.buildTree(5);
		g.setScore();

		Location L = new Location(g.getNextMove());

		boolean success;

		do {
			success = this.Move(L.x, L.y);
		} while (!success);
	}

	protected void serialize(String file) throws IOException {
		if (!file.contains(".")) {
			file += ".gg";
		}

		FileOutputStream file_out = new FileOutputStream(file);
		ObjectOutputStream obj_out = new ObjectOutputStream(file_out);

		obj_out.writeObject(this);
		obj_out.close();
	}

	protected void deserialize(String file) throws IOException, ClassNotFoundException, FileNotFoundException {
		FileInputStream f_in = new FileInputStream(file);
		ObjectInputStream o_in = new ObjectInputStream(f_in);

		Object o = o_in.readObject();
		this.copy((Board) o);
		o_in.close();
	}
}
