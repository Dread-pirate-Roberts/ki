/**
 *@author: David Buebe
 *@date:Jan 23, 2013
 */


import javax.swing.JDialog;
import java.awt.Panel;
import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.TextField;
import javax.swing.JTextPane;

/**
 */
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
