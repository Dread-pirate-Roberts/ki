/**
 *@author: David Buebe
 *@date:Jan 23, 2013
 */

package go;

import javax.swing.JDialog;
import java.awt.Container;
import javax.swing.JTextPane;

public final class AboutWindow extends JDialog{
	private static final long serialVersionUID = -5533117580272065148L;

	public AboutWindow() {
		JTextPane textPane = new JTextPane();
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("About Ki");
		setLocationRelativeTo(null);
		setSize(367,208);
		
		Container container = getContentPane();
		container.setLayout(null);
		
		textPane.setBounds(10, 11, 341, 158);
		
		container.add(textPane);
		setVisible(true);
	}
}
