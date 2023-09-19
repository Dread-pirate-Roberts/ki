/**
 *@author: David Buebe
 *@date:Oct 17, 2012
 */

package go;

import go.Intersection.piece;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectOutputStream;
import java.awt.FlowLayout;
import java.awt.Canvas;
import javax.swing.JTextField;
import java.awt.Panel;
import javax.swing.UIManager;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.SwingConstants;



public class Game extends JPanel{

	private Board game;
	private Mouse mouse;
	private JFrame frame;
	
	//private GoMenu menu;
	
	//Listener listener;
	
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu mnFile = new JMenu("File");
	private final JMenuItem mntmNewGame = new JMenuItem("New Game");
	private final JSeparator separator = new JSeparator();
	private final JMenuItem mntmSaveGame = new JMenuItem("Save Game");
	private final JMenuItem mntmLoadGame = new JMenuItem("Load Game");
	private final JMenu mnGame = new JMenu("Game");
	private final JMenu mnHelp = new JMenu("Help");
	private final JMenuItem mntmUndoMove = new JMenuItem("Undo Move");
	private final JMenuItem mntmScoreGame = new JMenuItem("Score Game");
	private final JMenuItem mntmHowToPlay = new JMenuItem("How to Play");
	private final JMenuItem mntmAboutKi = new JMenuItem("About Ki");
	//private JFrame newGameMenu = new newGame();
	private newGameMenu newGamePopup = new newGameMenu();
	private ScoringWindow score = new ScoringWindow();
	
	private JFileChooser loadGameMenu = new JFileChooser();
	private JFileChooser saveGameMenu = new JFileChooser();
	private final JLabel lblBlackStones = new JLabel("Stones");
	private final JLabel lblBlackPrisoners = new JLabel("Prisoners");
	private final JLabel lblWhiteStones = new JLabel("Stones");
	private final JLabel lblWhitePrisoners = new JLabel("Prisoners");
	private JTextField txtBlackStoneCount = new JTextField();
	private JTextField txtBlackPrisonerCount = new JTextField();
	private JTextField txtWhiteStoneCount = new JTextField();
	private JTextField txtWhitePrisonerCount = new JTextField();

	public Game() {
		this.setGame(9,0,Computer.none);
	}
	
	public Game(int size, int handicap,Computer comp) {
		this.setGame(size,handicap, comp);
	}
	
	protected void setGame(go.Game.newGameMenu.GameInfo newGameInfo) {
		this.setGame(newGameInfo.getSize(), newGameInfo.getHandicap(), newGameInfo.computer());
	}
	
	protected void setGame(int size, int handicap, Computer computer) {
		//this.popup_in_use = false;
		this.game = new Board();// this.listener = new Listener();
		this.game.setSize(size);
		this.game.turn = piece.black;
		this.game.handicap(handicap);
		this.game.setComputer(computer);
		game.setLayout(null);
		
		this.render();
		
		if(computer == Computer.black && handicap == 0) {
			this.game.computer_play();
		} else if(computer == Computer.white && handicap != 0){
			this.game.computer_play();
		}
		
		this.update_stone_info();
	}
	
	protected void initGameInfo() {
		lblBlackStones.setBounds(120, ((this.game.size - 1) * Board.SIZE) + ( 2 *Board.BOARDER) + 24, 40, 14);
		game.add(lblBlackStones);
		
		lblWhiteStones.setBounds(308, ((this.game.size - 1) * Board.SIZE) + ( 2 *Board.BOARDER) + 24, 40, 14);
		game.add(lblWhiteStones);
		
		lblBlackPrisoners.setBounds(120, (this.game.size - 1) * Board.SIZE + (2 * Board.BOARDER + 54), 58, 14);
		game.add(lblBlackPrisoners);
		
		lblWhitePrisoners.setBounds(308, (this.game.size - 1) * Board.SIZE + 2 * Board.BOARDER + 54, 58, 14);
		game.add(lblWhitePrisoners);
		txtBlackStoneCount.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtBlackStoneCount.setEditable(false);
		txtBlackStoneCount.setBounds(180, ((this.game.size - 1) * Board.SIZE) + ( 2 *Board.BOARDER) + 24, 30, 20);
		game.add(txtBlackStoneCount);
		txtBlackPrisonerCount.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtBlackPrisonerCount.setEditable(false);
		txtBlackPrisonerCount.setBounds(180,  ((this.game.size - 1) * Board.SIZE) +  2 * Board.BOARDER + 54, 30, 20);
		game.add(txtBlackPrisonerCount);
		txtWhiteStoneCount.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtWhiteStoneCount.setEditable(false);
		txtWhiteStoneCount.setBounds(372, ((this.game.size - 1) * Board.SIZE) + ( 2 *Board.BOARDER) + 24, 30, 20);
		game.add(txtWhiteStoneCount);
		txtWhitePrisonerCount.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtWhitePrisonerCount.setEditable(false);
		txtWhitePrisonerCount.setBounds(372,  ((this.game.size - 1) * Board.SIZE) + 2 * Board.BOARDER + 54, 30, 20);
		game.add(txtWhitePrisonerCount);
		
		this.update_stone_info();
	}
	
	protected void render() {
		txtWhitePrisonerCount.setColumns(10);
		txtWhiteStoneCount.setColumns(10);
		txtBlackPrisonerCount.setColumns(10);
		txtBlackStoneCount.setColumns(10);
		this.mouse = new Mouse();
		this.frame = new JFrame("Ki");
		this.frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize();
		this.frame.getContentPane().add(this.game);
		this.frame.setVisible(true);
		this.frame.setResizable(false);
		
		
		this.setMenus();
		
		this.initGameInfo();
		
		this.frame.addMouseListener(mouse);
	//	this.frame.addItemListener(listener);
		
	}
	
	protected void setMenus() {
		frame.setJMenuBar(menuBar);
		
		menuBar.add(mnFile);
		mntmNewGame.addActionListener(new ActionListener()  {
			
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				newGamePopup.show();

			}
		});
		
		mnFile.add(mntmNewGame);
		
		mnFile.add(separator);
		
		mntmSaveGame.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent event) {
				saveGameMenu = new JFileChooser();
				saveGameMenu.setDialogTitle("Save Game");
				saveGameMenu.setApproveButtonText("Save");
				if( true /*event.getSource() == this */) {
					int val = saveGameMenu.showSaveDialog(Game.this);
					
					if(val == JFileChooser.APPROVE_OPTION) {
						File file = saveGameMenu.getSelectedFile();
						try {
							game.serialize(file.toString());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					saveGameMenu = null;
				}
			}
		});

		mnFile.add(mntmSaveGame);
		
		mntmLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				loadGameMenu = new JFileChooser();
				loadGameMenu.setDialogTitle("Load Game");
				loadGameMenu.setApproveButtonText("Load");
				if(true) {
					int val = loadGameMenu.showOpenDialog(Game.this);
					
					if(val == JFileChooser.APPROVE_OPTION) {
						File file = loadGameMenu.getSelectedFile();
						try {
							game.deserialize(file.toString()); 
						}catch (IOException e){
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} 
					}
					setSize();
					update_stone_info();
					//render();
					game.repaint();
					initGameInfo();
				}
			}
		});

		mnFile.add(mntmLoadGame);
		
		menuBar.add(mnGame);
	/*	mntmUndoMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!game.history.isEmpty())
					game.undo();
			}
		});*/
		
		mnGame.add(mntmUndoMove);
	
		
		mnGame.add(mntmScoreGame);
		score.setVisible(false);
		mntmScoreGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				score.setScores(game);
				score.setVisible(true);
			}
		});
		menuBar.add(mnHelp);
		
		mnHelp.add(mntmHowToPlay);
		mntmAboutKi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				AboutWindow a = new AboutWindow();
				a.setVisible(true);
			}
		});
		
		mnHelp.add(mntmAboutKi);
	}
	
	protected void setSize() {
		this.frame.setSize((Board.BOARDER * 2) + (Board.SIZE * (( game.size)-1)) + 10,
				10 + (Board.BOARDER * 3) + (Board.SIZE * ( game.size))
				+ Board.FOOTER_HEIGHT);
	}
	
	public boolean popup_in_use() {
		return false;
	}
	
	public static void main(String[] args) { 
		Game m = new Game();
	}
	
	private class Mouse implements MouseListener{
		
		public Mouse(){}
		public void mouseClicked(MouseEvent event) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		
		public void mousePressed(MouseEvent event) {
			
			boolean success = game.get_coordinates(event.getX(), event.getY() - 35);
			update_stone_info();
			if(success && game.comp != Computer.none) {
				game.computer_play();
				update_stone_info();
			}
			
		} 
	}
 
	public class newGameMenu extends JDialog{
		private final JLabel lblOpponent = new JLabel("Opponent");
		private final JRadioButton rdbtnHuman = new JRadioButton("Human");
		private final JRadioButton rdbtnComputerwhite = new JRadioButton("Computer (White)");
		private final JLabel lblDifficulty = new JLabel("Board Size");
		private final JRadioButton rdbtnBeginner = new JRadioButton("Beginner (9 x 9)");
		private final JRadioButton rdbtnIntermediate = new JRadioButton("Intermediate (13 x 13)");
		private final JRadioButton rdbtnAdvanced = new JRadioButton("Advanced (19 x 19)");
		private final JLabel lblHandicap = new JLabel("Handicap");
		//private final JList list = new JList();
		private final ButtonGroup grpOpponent = new ButtonGroup();
		private final ButtonGroup grpDifficulty = new ButtonGroup();
		private JSlider slider;
		private final JButton btnNewButton = new JButton("New Game");
		private final JButton btnCancel = new JButton("Cancel");
		
		protected GameInfo newGameInfo;
		protected boolean waiting;
		private final JRadioButton rdbtnComputerblack = new JRadioButton("Computer (Black)");
		
		public newGameMenu() {
			
			initGUI();
		}
			
			private void initGUI() {
				//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				getContentPane().setLayout(null);
				lblOpponent.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblOpponent.setBackground(Color.WHITE);
				
				
				lblOpponent.setBounds(100, 11, 65, 36);
				
				getContentPane().add(lblOpponent);
				
				rdbtnHuman.setSelected(true);
				rdbtnHuman.setBounds(63, 41, 65, 23);
				getContentPane().add(rdbtnHuman);
				
				rdbtnComputerwhite.setBounds(63, 66, 153, 23);
				getContentPane().add(rdbtnComputerwhite);
				
				
				rdbtnComputerblack.setBounds(63, 92, 153, 23);
				getContentPane().add(rdbtnComputerblack);
			
				
				
				lblDifficulty.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblDifficulty.setBounds(100, 127, 65, 14);
				
				grpOpponent.add(rdbtnHuman);
				grpOpponent.add(rdbtnComputerwhite);
				grpOpponent.add(rdbtnComputerblack);
				
				
				getContentPane().add(lblDifficulty);
				rdbtnBeginner.setSelected(true);
				rdbtnBeginner.setBounds(63, 148, 142, 23);
				getContentPane().add(rdbtnBeginner);
				rdbtnIntermediate.setBounds(63, 174, 168, 23);
				getContentPane().add(rdbtnIntermediate);
				rdbtnAdvanced.setBounds(63, 200, 168, 23);
				getContentPane().add(rdbtnAdvanced);
				lblHandicap.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblHandicap.setBounds(100, 230, 55, 23);
				
				grpDifficulty.add(rdbtnBeginner);
				grpDifficulty.add(rdbtnIntermediate);
				grpDifficulty.add(rdbtnAdvanced);
				
				
				getContentPane().add(lblHandicap);
			//	list.setBounds(88, 224, 1, 1);
				//getContentPane().add(list);
				
				this.slider = new JSlider(JSlider.HORIZONTAL,0,9,0);
				slider.setSnapToTicks(true);
				slider.setBounds(23, 250, 229, 55);
				slider.setMajorTickSpacing(1);
				slider.setPaintTicks(true);
				slider.setPaintLabels(true);
				getContentPane().add(slider);
				
				btnNewButton.addActionListener(new ActionListener() {
					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent arg0) {
						
						newGameInfo = new GameInfo();
						
						if(rdbtnHuman.isSelected())
							newGameInfo.comp = Computer.none;
						else if(rdbtnComputerwhite.isSelected())
							newGameInfo.comp = Computer.white;
						else
							newGameInfo.comp = Computer.black;
						
						if(rdbtnBeginner.isSelected())
							newGameInfo.size = 9;
						else if(rdbtnIntermediate.isSelected())
							newGameInfo.size = 13;
						else
							newGameInfo.size = 19;
						
						newGameInfo.handicap = slider.getValue();
						
						
						frame.dispose();
						setGame(newGameInfo);
						
						hide();
						
						
					}
				});
				btnNewButton.setSelected(true);
				btnNewButton.setBounds(138, 316, 109, 23);
				
				getContentPane().add(btnNewButton);
				btnCancel.addActionListener(new ActionListener() {
					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent e) { 		
						hide();
					}
				});
				
				btnCancel.setBounds(34, 316, 94, 23);
				
				getContentPane().add(btnCancel);
				
				setTitle("New Game");
				setVisible(false);
				setSize(new Dimension(279, 401));
				
				setResizable(false); 	
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
	
	public void update_stone_info() {
		this.setTxtBlackStoneCount();
		this.setTxtWhiteStoneCount();
		this.setTxtBlackPrisonerCount();
		this.setTxtWhitePrisonerCount();
	}
	
	public void setTxtBlackStoneCount() {
		this.txtBlackStoneCount.setText(new String(Integer.toString(this.game.black_stones)));
	}
	public void setTxtWhiteStoneCount() {
		this.txtWhiteStoneCount.setText(new String(Integer.toString(this.game.white_stones)));
	}
	public void setTxtBlackPrisonerCount() {
		this.txtBlackPrisonerCount.setText(new String(Integer.toString(this.game.black_prisoners)));
	}
	public void setTxtWhitePrisonerCount() {
		this.txtWhitePrisonerCount.setText(new String(Integer.toString(this.game.white_prisoners)));
	}
}

	

