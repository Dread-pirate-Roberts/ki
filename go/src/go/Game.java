/**
 *@author: David Buebe
 *@date:Oct 17, 2012
 */
package go;

import go.Board.piece;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;




/**
 */

public class Game extends JPanel{

	Board game;
	Mouse mouse;
	JFrame frame;
	JLabel statusbar;
	GoMenu menu;
	
	
	
	public Game()
	{
		this.setGame(3,0);
	}
	
	public Game(int size, int handicap)
	{
		this.setGame(size,handicap);
	}
	
	
	protected void setGame(int size, int handicap)
	{
		this.game = new Board();
		this.game.setSize(size);
		this.game.turn = piece.black;
		this.game.handicap(handicap);
		this.mouse = new Mouse();
		this.frame = new JFrame("Go");
		this.frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.menu = new GoMenu();
		this.frame.setJMenuBar(this.menu);
		this.setSize();
		this.frame.add(this.game);
		this.frame.setVisible(true);
		this.frame.setResizable(false);
		this.frame.addMouseListener(mouse);
	}
	
	protected void setSize()
	{
		this.frame.setSize((Board.BOARDER * 2) + (Board.SIZE * ( game.x))
				,10 + (Board.BOARDER * 2) + (Board.SIZE * ( game.y))
				+ Board.FOOTER_HEIGHT);
	}
	
	public static void main(String[] args) {

		Game m = new Game();


	}
	

	
	public class Mouse implements MouseListener{
		

		public Mouse(){}
		
		
		public void mouseClicked(MouseEvent event) {
	
	
		}

		public void mouseEntered(MouseEvent e) {	
		}

		public void mouseExited(MouseEvent e) {
			
		}

		
		public void mousePressed(MouseEvent event) {
			game.get_coordinates(event.getX(), event.getY() - 35);
		}

		
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	
	}

}
