/**
 *@author: David Buebe
 *@date:Dec 29, 2012
 */
package go;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 */
public class GoMenu extends JMenuBar{
	public GoMenu()
	{
		  
		  this.fileMenu();
		  this.gameMenu();
		  this.helpMenu();
		 
		
	}
	
	
	public void fileMenu(){
		  JMenu first =  new JMenu("File");
		  JMenuItem newGame = new JMenuItem("New Game");
		  first.add(newGame);
		  first.addSeparator();
		  JMenuItem save = new JMenuItem("Save Game");
		  JMenuItem load = new JMenuItem("Load Game");
		  first.add(save);first.add(load);
		  this.add(first);
	}
	
	public void gameMenu()
	{
		  JMenu second = new JMenu("Game");
		  JMenuItem undo = new JMenuItem("Undo last move");
		  second.add(undo);
		  second.addSeparator();
		  JMenuItem score = new JMenuItem("Score");
		  second.add(score);
		  this.add(second);
	}
	
	public void helpMenu()
	{
		  JMenu third = new JMenu("Help");
		  JMenuItem rules = new JMenuItem("How to Play");
		  JMenuItem about = new JMenuItem("About the Program");
		  third.add(rules);
		  third.add(about);
		  this.add(third);
	}
}
