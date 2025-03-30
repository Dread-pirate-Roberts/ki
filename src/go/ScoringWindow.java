/**
 *@author: David Buebe
 *@date:Feb 6, 2013
 */

package go;

import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Container;
import javax.swing.JTextField;

public class ScoringWindow extends JDialog{
	private static final long serialVersionUID = 2781085662271509297L;
	private final Container container = getContentPane();
	private final Board board;
	
	public ScoringWindow(Board b) {
		board = b;
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		container.setLayout(null);
		setSize(224,222);
		setResizable(false);
		setVisible(true);

		setLabels();
		setScores();
	}
	
	private void setLabels() {
		JLabel territoryLabel = new JLabel("Territory");
		territoryLabel.setBounds(79, 11, 70, 14);
		territoryLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		container.add(territoryLabel);

		JLabel areaLabel = new JLabel("Area");
		areaLabel.setBounds(91, 93, 56, 14);
		areaLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		container.add(areaLabel);
	}
	
	private void setScores() {
		addTerritoryScoreTextField();
		addAreaScoreTextField();
	}
	
	private void addTerritoryScoreTextField() {
		JTextField territoryScoreTextField = new JTextField();
		
		territoryScoreTextField.setEditable(false);
		territoryScoreTextField.setBounds(43, 36, 126, 34);
		territoryScoreTextField.setColumns(10);
		
		double territoryScore = board.get_territory_score(); 
		
		if(territoryScore == 0){
			territoryScoreTextField.setText("Tie");
		} else if(territoryScore > 0){
			territoryScoreTextField.setText("White wins by:" + territoryScore);
		} else{
			territoryScoreTextField.setText("Black wins by:" + (0.0 - territoryScore));
		}
		
		container.add(territoryScoreTextField);
	}
	
	private void addAreaScoreTextField() {
		JTextField areaScoreTextField = new JTextField();
		
		areaScoreTextField.setEditable(false);
		areaScoreTextField.setBounds(43, 118, 126, 40);
		areaScoreTextField.setColumns(10);
		
		double areaScore = board.get_area_score();
		
		if(areaScore == 0){
			areaScoreTextField.setText("Tie");
		} else if(areaScore > 0){
			areaScoreTextField.setText("White wins by:" + areaScore);
		} else {
			areaScoreTextField.setText("Black wins by:" + (0.0 - areaScore));
		}
		
		container.add(areaScoreTextField);
	}
}
