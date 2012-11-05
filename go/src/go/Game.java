/**
 *@author: David Buebe
 *@date:Oct 17, 2012
 */
package go;

import go.Board.piece;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




/**
 */

public class Game extends JPanel{

	Board game;
	Mouse mouse;
	JFrame frame;
	JLabel statusbar;
	
	
	public Game()
	{
		this.game = new Board();
		this.game.intermediate();
		this.game.turn = piece.black;
		this.mouse = new Mouse();
		this.frame = new JFrame("Go");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize( (Board.BOARDER * 2) + (Board.SIZE * ( game.x))
				,10 + (Board.BOARDER * 2) + (Board.SIZE * ( game.y))
				+ Board.FOOTER_HEIGHT);
		this.frame.add(this.game);
		this.frame.setVisible(true);
		this.frame.setResizable(false);
		
		this.frame.addMouseListener(mouse);
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