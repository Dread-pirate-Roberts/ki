/**
 *@author: David Buebe
 *@date:Feb 6, 2013
 */


import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;

/**
 */
public class ScoringWindow extends JDialog{
	private final JLabel lblTerritory = new JLabel("Territory");
	private final JLabel lblArea = new JLabel("Area");
	private final JTextField territoryScore = new JTextField();
	private final JTextField areaScore = new JTextField();
	
	public ScoringWindow() {
		initGUI();
	}
	
	public ScoringWindow(Board b)
	{
		initGUI();
		setScores(b);
	}
	private void initGUI() {
		setAlwaysOnTop(true);
		
		areaScore.setEditable(false);
		areaScore.setBounds(43, 118, 126, 40);
		areaScore.setColumns(10);
		territoryScore.setEditable(false);
		territoryScore.setBounds(43, 36, 126, 34);
		territoryScore.setColumns(10);
		getContentPane().setLayout(null);
		
		
		lblTerritory.setBounds(79, 11, 70, 14);
		
		getContentPane().add(lblTerritory);
		
		lblTerritory.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		lblArea.setBounds(91, 93, 56, 14);
		
		getContentPane().add(lblArea);
		
		lblArea.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		
		this.setSize(224,222);
		setResizable(false);
	}
	
	public void setScores(Board b)
	{
		
		
		double t_score = b.get_territory_score();
		
		double a_score = b.get_area_score();
		
		String territory, area;
		
		if(t_score == 0)
			territory = "Tie";
		else if(t_score > 0)
			territory = "White wins by:" + t_score;
		else
			territory = "Black wins by:" + (0.0 - t_score);
		
		if(t_score == 0)
			area = "Tie";
		else if(t_score > 0)
			area = "White wins by:" + a_score;
		else
			area = "Black wins by:" + (0.0 - a_score);
		
		
		territoryScore.setText(territory);
		areaScore.setText(area);
		
		
		getContentPane().add(territoryScore);
		getContentPane().add(areaScore);
		
			
	}

	
}
