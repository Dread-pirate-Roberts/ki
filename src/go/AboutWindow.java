/**
 *@author: David Buebe
 *@date:Jan 23, 2013
 */

package go;

import javax.swing.JDialog;
import javax.swing.JTextPane;


public class AboutWindow extends JDialog{
	private final JTextPane textPane = new JTextPane();
	public AboutWindow() {
		initGUI();
	}
	private void initGUI() {
		setAlwaysOnTop(true);
		setResizable(false);
		setResizable(false);
		setTitle("About Ki");
		getContentPane().setLayout(null);
		getContentPane().setLayout(null);
		getContentPane().setLayout(null);
		getContentPane().setLayout(null);
		
		getContentPane().setLayout(null);
		
		this.setSize(367,208);
		textPane.setBounds(10, 11, 341, 158);
		
		getContentPane().add(textPane);
	}
}
