/**
 *@author: David Buebe
 *@date:Oct 17, 2012
 */

package go;

import go.Intersection.piece;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class Game extends JPanel{

	private Board game;
	private Mouse mouse;
	private JFrame frame;
	
	private final JLabel lblBlackStones = new JLabel("Stones");
	private final JLabel lblBlackPrisoners = new JLabel("Prisoners");
	private final JLabel lblWhiteStones = new JLabel("Stones");
	private final JLabel lblWhitePrisoners = new JLabel("Prisoners");
	private JTextField txtBlackStoneCount = new JTextField();
	private JTextField txtBlackPrisonerCount = new JTextField();
	private JTextField txtWhiteStoneCount = new JTextField();
	private JTextField txtWhitePrisonerCount = new JTextField();

	public Game() {
		setGame(9,0,Computer.none);
	}
	
	public Game(int size, int handicap,Computer comp) {
		setGame(size,handicap, comp);
	}
	
	protected void setGame(go.Game.newGameMenu.GameInfo newGameInfo) {
		setGame(newGameInfo.getSize(), newGameInfo.getHandicap(), newGameInfo.computer());
	}
	
	protected void setGame(int size, int handicap, Computer computer) {
		game = new Board();
		game.setSize(size);
		game.turn = piece.black;
		game.handicap(handicap);
		game.setComputer(computer);
		game.setLayout(null);
		
		render();
		
		if(computer == Computer.black && handicap == 0) {
			game.computer_play();
		} else if(computer == Computer.white && handicap != 0){
			game.computer_play();
		}
		
		updateStoneInfo();
	}
	
	protected void initGameInfo() {
		
		lblBlackStones.setBounds(120, ((game.size - 1) * Board.SIZE) + ( 2 *Board.BOARDER) + 24, 40, 14);
		game.add(lblBlackStones);
		
		lblWhiteStones.setBounds(308, ((game.size - 1) * Board.SIZE) + ( 2 *Board.BOARDER) + 24, 40, 14);
		game.add(lblWhiteStones);
		
		lblBlackPrisoners.setBounds(120, (game.size - 1) * Board.SIZE + (2 * Board.BOARDER + 54), 58, 14);
		game.add(lblBlackPrisoners);
		
		lblWhitePrisoners.setBounds(308, (game.size - 1) * Board.SIZE + 2 * Board.BOARDER + 54, 58, 14);
		game.add(lblWhitePrisoners);
		txtBlackStoneCount.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtBlackStoneCount.setEditable(false);
		txtBlackStoneCount.setBounds(180, ((game.size - 1) * Board.SIZE) + ( 2 *Board.BOARDER) + 24, 30, 20);
		game.add(txtBlackStoneCount);
		txtBlackPrisonerCount.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtBlackPrisonerCount.setEditable(false);
		txtBlackPrisonerCount.setBounds(180,  ((game.size - 1) * Board.SIZE) +  2 * Board.BOARDER + 54, 30, 20);
		game.add(txtBlackPrisonerCount);
		txtWhiteStoneCount.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtWhiteStoneCount.setEditable(false);
		txtWhiteStoneCount.setBounds(372, ((game.size - 1) * Board.SIZE) + ( 2 *Board.BOARDER) + 24, 30, 20);
		game.add(txtWhiteStoneCount);
		txtWhitePrisonerCount.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtWhitePrisonerCount.setEditable(false);
		txtWhitePrisonerCount.setBounds(372,  ((game.size - 1) * Board.SIZE) + 2 * Board.BOARDER + 54, 30, 20);
		game.add(txtWhitePrisonerCount);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		updateStoneInfo();
	}
	
	protected void render() {
		txtWhitePrisonerCount.setColumns(10);
		txtWhiteStoneCount.setColumns(10);
		txtBlackPrisonerCount.setColumns(10);
		txtBlackStoneCount.setColumns(10);
		mouse = new Mouse();
		frame = new JFrame("Ki");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize();
		frame.getContentPane().add(game);
		frame.setVisible(true);
		frame.setResizable(false);
		
		setMenus();
		
		initGameInfo();
		
		frame.addMouseListener(mouse);
		
	}
	
	protected void setMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.add(getFileMenu());
		menuBar.add(getGameMenu());
		menuBar.add(getHelpMenu());
		
		frame.setJMenuBar(menuBar);
	}

	private JMenu getFileMenu() {
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem newGameMenuItem = new JMenuItem("New Game");

		newGameMenuItem.addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent arg0) {
				new newGameMenu();
			}
		});
		
		fileMenu.add(newGameMenuItem);
		
		fileMenu.add(new JSeparator());
		
		JMenuItem saveGameMenuItem = new JMenuItem("Save Game");
		
		saveGameMenuItem.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent event) {
				JFileChooser saveGameMenuItem = new JFileChooser();
				saveGameMenuItem.setDialogTitle("Save Game");
				saveGameMenuItem.setApproveButtonText("Save");
				
				int val = saveGameMenuItem.showSaveDialog(Game.this);
				
				if(val == JFileChooser.APPROVE_OPTION) {
					File file = saveGameMenuItem.getSelectedFile();
					try {
						game.serialize(file.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		});

		fileMenu.add(saveGameMenuItem);
		
		JMenuItem loadGameMenuItem = new JMenuItem("Load Game");
		
		loadGameMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser loadGameMenuItem = new JFileChooser();
				loadGameMenuItem.setDialogTitle("Load Game");
				loadGameMenuItem.setApproveButtonText("Load");
				
				int val = loadGameMenuItem.showOpenDialog(Game.this);
				
				if(val == JFileChooser.APPROVE_OPTION) {
					File file = loadGameMenuItem.getSelectedFile();
					try {
						game.deserialize(file.toString()); 
					}catch (Exception e){
						e.printStackTrace();
					}
					setSize();
					updateStoneInfo();
					game.repaint();
					initGameInfo();
				}

			}
		});

		fileMenu.add(loadGameMenuItem);
		
		return fileMenu;
	}
	
	private JMenu getGameMenu() {
		JMenu gameMenu = new JMenu("Game");
		
		JMenuItem undoMoveMenuItem = new JMenuItem("Undo Move");
		gameMenu.add(undoMoveMenuItem);
		
		JMenuItem scoreGameMenuItem = new JMenuItem("Score Game");

		scoreGameMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ScoringWindow(game);
			}
		});
		
		gameMenu.add(scoreGameMenuItem);
		
		return gameMenu;
	}
	
	private JMenu getHelpMenu() {
		
		JMenu helpMenu = new JMenu("Help");
	
		helpMenu.add(new JMenuItem("How to Play"));

		JMenuItem aboutItem = new JMenuItem("About Ki");
		
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AboutWindow();
			}
		});
		
		helpMenu.add(aboutItem);
		
		return helpMenu;
	}
	
	protected void setSize() {
		this.frame.setSize((Board.BOARDER * 2) + (Board.SIZE * (( game.size)-1)) + 10,
				10 + (Board.BOARDER * 3) + (Board.SIZE * ( game.size))
				+ Board.FOOTER_HEIGHT);
	}
	
	public static void main(String[] args) { 
		new Game();
	}
	
	private class Mouse implements MouseListener{
		
		public Mouse(){}
		public void mouseClicked(MouseEvent event) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		
		public void mousePressed(MouseEvent event) {
			
			boolean success = game.get_coordinates(event.getX(), event.getY() - 35);
			updateStoneInfo();
			if(success && game.comp != Computer.none) {
				game.computer_play();
				updateStoneInfo();
			}
			
		} 
	}
 
	public class newGameMenu extends JDialog{
		private static final long serialVersionUID = -1135473400125396418L;
		private final JLabel opponentLabel = new JLabel("Opponent");
		private final JRadioButton humanRadioButton = new JRadioButton("Human");
		private final JRadioButton whiteComputerRadioButton = new JRadioButton("Computer (White)");
		private final JRadioButton blackComputerRadioButton = new JRadioButton("Computer (Black)");
		private final JLabel difficultyLabel = new JLabel("Board Size");
		private final JRadioButton beginnerRadioButton = new JRadioButton("Beginner (9 x 9)");
		private final JRadioButton intermediateRadioButton = new JRadioButton("Intermediate (13 x 13)");
		private final JRadioButton advancedRadioButton = new JRadioButton("Advanced (19 x 19)");
		private final JLabel handicapLabel = new JLabel("Handicap");
		private final ButtonGroup opponentButtonGroup = new ButtonGroup();
		private final ButtonGroup difficultyButtonGroup = new ButtonGroup();
		private JSlider slider;
		private final JButton newButton = new JButton("New Game");
		private final JButton cancelButton = new JButton("Cancel");
		
		protected GameInfo newGameInfo;

		public newGameMenu() {
			
			initGUI();
		}
			
		private void initGUI() {
			getContentPane().setLayout(null);
			opponentLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
			opponentLabel.setBackground(Color.WHITE);
			
			
			opponentLabel.setBounds(100, 11, 65, 36);
			
			getContentPane().add(opponentLabel);
			
			humanRadioButton.setSelected(true);
			humanRadioButton.setBounds(63, 41, 65, 23);
			getContentPane().add(humanRadioButton);
			
			whiteComputerRadioButton.setBounds(63, 66, 153, 23);
			getContentPane().add(whiteComputerRadioButton);
			
			
			blackComputerRadioButton.setBounds(63, 92, 153, 23);
			getContentPane().add(blackComputerRadioButton);
			
			difficultyLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
			difficultyLabel.setBounds(100, 127, 65, 14);
			
			opponentButtonGroup.add(humanRadioButton);
			opponentButtonGroup.add(whiteComputerRadioButton);
			opponentButtonGroup.add(blackComputerRadioButton);
			
			getContentPane().add(difficultyLabel);
			beginnerRadioButton.setSelected(true);
			beginnerRadioButton.setBounds(63, 148, 142, 23);
			getContentPane().add(beginnerRadioButton);
			intermediateRadioButton.setBounds(63, 174, 168, 23);
			getContentPane().add(intermediateRadioButton);
			advancedRadioButton.setBounds(63, 200, 168, 23);
			getContentPane().add(advancedRadioButton);
			handicapLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
			handicapLabel.setBounds(100, 230, 55, 23);
			
			difficultyButtonGroup.add(beginnerRadioButton);
			difficultyButtonGroup.add(intermediateRadioButton);
			difficultyButtonGroup.add(advancedRadioButton);
			
			
			getContentPane().add(handicapLabel);
			
			slider = new JSlider(JSlider.HORIZONTAL,0,9,0);
			slider.setSnapToTicks(true);
			slider.setBounds(23, 250, 229, 55);
			slider.setMajorTickSpacing(1);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			getContentPane().add(slider);
			
			newButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					newGameInfo = new GameInfo();
					
					if(humanRadioButton.isSelected()) {
						newGameInfo.comp = Computer.none;
					} else if (whiteComputerRadioButton.isSelected()) {
						newGameInfo.comp = Computer.white;
					} else {
						newGameInfo.comp = Computer.black;
					}
					
					if(beginnerRadioButton.isSelected()) {
						newGameInfo.size = 9;
					} else if (intermediateRadioButton.isSelected()) {
						newGameInfo.size = 13;
					} else {
						newGameInfo.size = 19;
					}
					
					newGameInfo.handicap = slider.getValue();
					
					frame.dispose();
					setGame(newGameInfo);
					dispose();
				}
			});
				
			newButton.setSelected(true);
			newButton.setBounds(138, 316, 109, 23);
			
			getContentPane().add(newButton);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			
			cancelButton.setBounds(34, 316, 94, 23);
			
			getContentPane().add(cancelButton);
			
			setTitle("New Game");
			setSize(new Dimension(279, 401));
			
			setResizable(false); 	
			setVisible(true);
		}
			
		public class GameInfo{ 
			private Computer comp;
			private int size;
			private int handicap;
			
			GameInfo(){}
			
			GameInfo(int size, int handicap, Computer comp) {
				this.comp = comp;
				
				assert(size == 9 || size == 13 || size == 19);
				this.size = size;
				
				assert(handicap >= 0);
				assert(handicap < 10);
				this.handicap = handicap;
			}
			
			public int getSize() {
				return this.size;
			}
			
			public int getHandicap() {
				return this.handicap;
			}
			
			public Computer computer() {
				return comp;
			} 
		} 
	}
	
	public enum Computer{
		black, white, none
	}
	
	public void updateStoneInfo() {
		txtBlackStoneCount.setText(Integer.toString(this.game.black_stones));
		txtWhiteStoneCount.setText(Integer.toString(this.game.white_stones));
		txtBlackPrisonerCount.setText(Integer.toString(this.game.black_prisoners));
		txtWhitePrisonerCount.setText(Integer.toString(this.game.white_prisoners));
	}
}

	

