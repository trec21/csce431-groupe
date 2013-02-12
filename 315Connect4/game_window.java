import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

class Game_Window
{
	public Game_Window()
	{
		boardWidth = 7;
		boardHeight = 7;
		roomNum = 0;
		running = false;
		canFlipBoard = true;
		flipEnabled = true;
		removeEnabled =true;
		gameOver = false;
		userWon = false;
		columnNum = 0;
		userColor = "";
		windowSize = 512;
		gameBoard = new int[boardHeight][boardWidth];
	}

	public Game_Window(int size)
	{
		boardWidth = size;
		boardHeight = size;
		roomNum = 0;
		running = false;
		canFlipBoard = true;
		flipEnabled = true;
		removeEnabled =true;
		gameOver = false;
		userWon = false;
		columnNum = 0;
		userColor = "";
		windowSize = 512;
		gameBoard = new int[boardHeight][boardWidth];

	}

	public Game_Window(int w, int h)
	{
		boardWidth = w;
		boardHeight = h;
		roomNum = 0;
		running = false;
		canFlipBoard = true;
		flipEnabled = true;
		removeEnabled =true;
		gameOver = false;
		userWon = false;
		columnNum = 0;
		userColor = "";
		windowSize = 512;
		gameBoard = new int[boardHeight][boardWidth];

	}
	
	public Game_Window(int w, int h, int size)
	{
		boardWidth = w;
		boardHeight = h;
		roomNum = 0;
		running = false;
		canFlipBoard = true;
		flipEnabled = true;
		removeEnabled =true;
		gameOver = false;
		userWon = false;
		columnNum = 0;
		userColor = "";
		windowSize = size;
		gameBoard = new int[boardHeight][boardWidth];

	}

	public static int check4(int w, int h, int s)
	{
		int x = w;
		int y = h;
		int color = gameBoard[h][w];
		int slope = s;
		int numInRow;
		if(color==0)
			numInRow=0;
		else
			numInRow=1;
		for(int i=0;i<3;i++)
		{
			if(slope!=100)
			{
				x+=1;
				y-=slope;
			} else
				y-=1;

			if((gameBoard[y][x]==color)&&(color!=0))
				numInRow++;
			else if((gameBoard[y][x]!=color)&&(color==0)) {
				color = gameBoard[y][x];
				if(gameBoard[y][x]==2)
					numInRow += 10;
				numInRow++;
			}
			else if(gameBoard[y][x]!=0) {
				numInRow = 0;
				break;
			}
		}
		return numInRow;
	}

	public static void checkWin()
	{
		int height = boardHeight;
		int width = boardWidth;
		int numInRow = 1;
		int color = 0;
		for(int h=height-1; h>=0; h--)
		{
			for(int w=0; w<width; w++)
			{
				if(gameBoard[h][w]!=0)
				{
					if(((w+3)<width)&&((h+3)<height))
						numInRow = check4(w, h, -1);
					if(((w+3)<width)&&(numInRow!=4))
						numInRow = check4(w, h, 0);
					if(((w+3)<width)&&((h-3)>=0)&&(numInRow!=4))
						numInRow = check4(w, h, 1);
					if(((h-3)>=0)&&(numInRow!=4))
						numInRow = check4(w, h, 100);
				}
				if(numInRow==4){
					color = gameBoard[h][w];
					break;
				}
			}
			if(numInRow==4)
				break;
		}
		if(numInRow==4)
		{
			gameOver = true;
			if(((color==1)&&(userColor=="Red"))||((color==2)&&(userColor=="Black")))
				userWon = true;
			else
				userWon = false;
		}
	}

	public static void PlayAgain()
	{
		roomNum = 1;
		gameOver = false;
		userWon = false;
		columnNum = 0;
		userColor = "";
		for(int i = 0; i < boardHeight; i++)
		{
			for(int j = 0; j < boardWidth; j++)
			{
				gameBoard[i][j] = 0;
			}
		}
		Init_Game();
		ShowTitle();
	}

	//GUI stuff--------------------------------------------------------
	public void AddGUI(Game_Window_Gui gui){
		GUI = gui;
	}

	public static void Init_All(){
		Init_Title();
		Init_Color();
		Init_Game();
		Init_Again();
		Init_Score();

		TitleScreen.hide();
		ColorSelectScreen.hide();
		GameScreen.hide();
		PlayAgainScreen.hide();
		ScoreScreen.hide();
	}

	public static void Init_Title(){
		TitleScreen = new JFrame("Title Screen");
			TitleScreen.setLocation(10,10);
			TitleScreen.setSize(windowSize, windowSize);

		ImagePanel image = new ImagePanel("images/titleScreen.jpg", windowSize);
		TitleScreen.add(image);
		
		//buttons
		ButtonListener Title = new ButtonListener();
		
		JButton Start = new JButton("Start Game");
		Start.setActionCommand("Start Game");
		
		JButton Quit = new JButton("Quit");
		Quit.setActionCommand("quit");
		
		TitleScreen.add(Start);
		TitleScreen.add(Quit);
		TitleScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}

	public static void Init_Color(){
		ColorSelectScreen = new JFrame("Color Select Screen");
			ColorSelectScreen.setLocation(50,50);
			ColorSelectScreen.setSize(windowSize, windowSize);
			ColorSelectScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//add shapes to Color Choose Screen


	}

	public static void Init_Game(){
		GameScreen = new JFrame("Game Screen");
		GameScreen.setLocation(10,10);
		GameScreen.setSize(windowSize, windowSize);
		GameScreen.add(GUI);
	}
	
	public static void Init_Again(){
		PlayAgainScreen = new JFrame("Play Again Screen");
			PlayAgainScreen.setLocation(10,10);
			PlayAgainScreen.setSize(windowSize,windowSize);
			PlayAgainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//add shapes to PlayAgainScreen

	}

	public static void Init_Score(){
		ScoreScreen = new JFrame("Score Screen");
			ScoreScreen.setLocation(10,10);
			ScoreScreen.setSize(windowSize,windowSize);
			ScoreScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//add shapes to ScoreScreen

	}

	public static void ShowTitle(){
		TitleScreen.show();
		ColorSelectScreen.hide();
		GameScreen.hide();
		PlayAgainScreen.hide();
		ScoreScreen.hide();
	}

	public static void ShowColor(){
		TitleScreen.hide();
		ColorSelectScreen.show();
		GameScreen.hide();
		PlayAgainScreen.hide();
		ScoreScreen.hide();
	}

	public static void ShowGame(){
		TitleScreen.hide();
		ColorSelectScreen.hide();
		GameScreen.show();
		PlayAgainScreen.hide();
		ScoreScreen.hide();
	}

	public static void ShowPlayAgain(){
		TitleScreen.hide();
		ColorSelectScreen.hide();
		GameScreen.hide();
		PlayAgainScreen.show();
		ScoreScreen.hide();
	}

	public static void ShowScore(){
		TitleScreen.hide();
		ColorSelectScreen.hide();
		GameScreen.hide();
		PlayAgainScreen.hide();
		ScoreScreen.show();
	}

  	//given a column, returns the top empty space where the piece will end up after dropping it
  	public int getRow(int column){
  		int row =0;
  		while(gameBoard[column][row]!=0 && row<boardWidth){
  			row++;
  		}
  		if(row >= boardWidth )return -1;
  		return row;
  	}

  	public Color getPlayerColor(){
  		if(userColor== "Red") return Color.red;
  		else return Color.black;
  	}

	public JFrame GameScreen(){

		return GameScreen;
	}
	
	public int getBoardHeight(){
		return boardHeight;
	}
	
	public int getBoardWidth(){
		return boardWidth;
	}
	
	public int getWindowSize(){
		return windowSize;
	}

	public void SetGameScreen(JFrame f){
		GameScreen = f;
	}
	
	public int[][] getGameBoard(){
		return gameBoard;
	}
	
	public void setGameBoard(int[][] a){
		gameBoard = a;
	}

	public void Run()
	{
		Scanner scan;
		try {
			//scan = new Scanner(new File("temp.txt"));
			scan = new Scanner(System.in);
		running = true;
		for(int i = 0; i < boardHeight; i++)
		{
			for(int j = 0; j < boardWidth; j++)
			{
				gameBoard[i][j] = 0;
			}
		}
		Random rand = new Random();

		
		
		String command = "";
		boolean full = true;

		String removeAnswer;
		String flipAnswer;

		while(running)
		{
			command = "no op";
			removeAnswer = "";
			flipAnswer = "";
			if(roomNum == 0)
			{
				ShowTitle();
				System.out.println("Connect Four");
				System.out.println("enter the command 'Start' to start playing");
				command = scan.next();
			}
			else if(roomNum == 1)
			{
				ShowColor();
				System.out.println("");
				System.out.println("Select your color(Red/Black)");
				command = scan.next();
				System.out.println("Do you want to enable the ability to remove pieces from the bottom of the board? (Yes/No)");
				removeAnswer = scan.next();
				System.out.println("Do you want to enable the ability to flip the board? (Yes/No)");
				flipAnswer = scan.next();
			}
			else if(roomNum == 2)
			{
				ShowGame();
				full = true;
				System.out.println("");
				for(int i = 0; i < boardHeight; i++)
				{
					for(int j = 0; j < boardWidth; j++)
					{	if(j < (boardWidth - 1))
						{
							if(gameBoard[i][j] == 0)
							{
								System.out.print("O ");
								GUI.changePieceColor(j,i, Color.white);
								full = false;
							}
							else if(gameBoard[i][j] == 1)
							{
								System.out.print("R ");
								GUI.changePieceColor(j,i, Color.red);
							}
							else if(gameBoard[i][j] == 2)
							{
								System.out.print("B ");
								GUI.changePieceColor(j,i, Color.black);
							}
						}
						else
						{
							if(gameBoard[i][j] == 0)
							{
								System.out.println("O");
								GUI.changePieceColor(j,i, Color.white);
								full = false;
							}
							else if(gameBoard[i][j] == 1)
							{
								System.out.println("R");
								GUI.changePieceColor(j,i, Color.red);
							}
							else if(gameBoard[i][j] == 2)
							{
								System.out.println("B");
								GUI.changePieceColor(j,i, Color.black);
							}
						}

					}
				}
				if(full == true)
				{
					gameOver = true;
				}

				// eval board state/ check win conditions
				checkWin();

				if(gameOver)
				{
					roomNum = 3;
				}
				else
				{
					System.out.println("");
					System.out.println("Select a row to place your piece, enter the number row you want to place your piece in.");
					if(removeEnabled == true)
					{
						System.out.println("Enter the negative number of the row to remove one of your pieces from the bottom.");
					}
					if(flipEnabled == true)
					{
						System.out.println("enter 0 to flip the board.");
					}
					columnNum = scan.nextInt();
				}
			}
			else if(roomNum == 3)
			{
				if(userWon)
				{
					System.out.println("");
					System.out.println("You Won!");
				}
				else
				{
					System.out.println("");
					System.out.println("Sorry, better luck next time.");
				}
				ShowPlayAgain();
				System.out.println("To play again enter 'Again', to exit enter 'Exit'");
				command = scan.next();// need this to get rid of junk data after reading in Ints
				//command = scan.nextLine();
			}


			if((roomNum == 0) && (command.equals("Start")))
			{
				roomNum = 1;
			}
			else if((roomNum == 1) && (command.equals("Red")) && ((removeAnswer.equals("Yes")) || (removeAnswer.equals("No"))) && ((flipAnswer.equals("Yes")) || (flipAnswer.equals("No"))))
			{
				userColor = "Red";
				if(removeAnswer.equals("Yes"))
				{
					removeEnabled = true;
				}
				else
				{
					removeEnabled = false;
				}

				if(flipAnswer.equals("Yes"))
				{
					flipEnabled = true;
				}
				else
				{
					flipEnabled = false;
				}
				roomNum = 2;
			}
			else if((roomNum == 1) && (command.equals("Black")) && ((removeAnswer.equals("Yes")) || (removeAnswer.equals("No"))) && ((flipAnswer.equals("Yes")) || (flipAnswer.equals("No"))))
			{
				userColor = "Black";
				if(removeAnswer.equals("Yes"))
				{
					removeEnabled = true;
				}
				else
				{
					removeEnabled = false;
				}

				if(flipAnswer.equals("Yes"))
				{
					flipEnabled = true;
				}
				else
				{
					flipEnabled = false;
				}
				roomNum = 2;
			}
			else if((roomNum == 2) && (Math.abs(columnNum) <= boardWidth))
			{
				boolean validMove = true;
				int xLocation = Math.abs(columnNum) - 1;
				int yLocation = boardHeight - 1;
				boolean found = false;
				if(columnNum > 0)
				{
					while(!found)
					{
						if(gameBoard[yLocation][xLocation] == 0)
						{
							if(userColor == "Red")
							{
								gameBoard[yLocation][xLocation] = 1;
							}
							else if(userColor == "Black")
							{
								gameBoard[yLocation][xLocation] = 2;
							}
							found = true;
						}
						else
						{
							if(yLocation > 0)
							{
								yLocation = yLocation - 1;
							}
							else
							{
								System.out.println("");
								System.out.println("That Column is full.");
								found = true;
								validMove = false;
							}
						}
					}
				}
				else if (columnNum < 0 && (removeEnabled == true))
				{
					// remove piece
					if((userColor == "Red") && (gameBoard[yLocation][xLocation] != 1))
					{
						System.out.println("");
						System.out.println("You cannot remove that piece.");
						validMove = false;
					}
					else if((userColor == "Black") && (gameBoard[yLocation][xLocation] != 2))
					{
						System.out.println("");
						System.out.println("You cannot remove that piece.");
						validMove = false;
					}
					else
					{
						while(yLocation > 0)
						{
							gameBoard[yLocation][xLocation] = gameBoard[yLocation - 1][xLocation];
							yLocation = yLocation - 1;
						}
						gameBoard[yLocation][xLocation] = 0;
					}
				}
				else if((columnNum == 0)&& (canFlipBoard == true) && (flipEnabled == true))
				{
					// flip board
					int topPieceLocation = 0;
					int tempPiece = 0;
					for(int i = 0; i < boardWidth; i++)
					{
						yLocation = boardHeight - 1;
						topPieceLocation = 0;
						found = false;
						while(!found)
						{
							if(gameBoard[yLocation][i] == 0)
							{
								topPieceLocation = yLocation+1;
								found = true;
							}
							else if(yLocation > 0)
							{
								yLocation = yLocation - 1;
							}
							else
							{
								found = true;
								topPieceLocation = 0;
							}
						}

						tempPiece = 0;
						for(int j = boardHeight - 1; j > boardHeight-1-((boardHeight-topPieceLocation)/2); j--)
						{
							tempPiece = gameBoard[j][i];
							gameBoard[j][i] = gameBoard[topPieceLocation+((boardHeight - 1)-j)][i];
							gameBoard[topPieceLocation+((boardHeight - 1)-j)][i] = tempPiece;

						}
					}
				}
				else if((columnNum == 0)&&(canFlipBoard==false) && (flipEnabled == true))
				{
					System.out.println("");
					System.out.println("You cannot flip the board immediately after it has been fliped.");
					validMove = false;
				}
				else
				{
					System.out.println("");
					System.out.println("That option is not enabled.");
					validMove = false;
				}

				if((validMove == true)&&(canFlipBoard == false))
				{
					canFlipBoard = true;
				}
				else if((validMove == true)&&(columnNum == 0))
				{
					canFlipBoard = false;
				}


				full = true;
				for(int i = 0; i < boardHeight; i++)
				{
					for(int j = 0; j < boardWidth; j++)
					{
						if(gameBoard[i][j] == 0)
						{
							full = false;
						}
					}
				}
				if(full == true)
				{
					gameOver = true;
				}

				// eval board state/ check for win conditions
				checkWin();

				if(validMove == true && gameOver == false)
				{
					boolean AIFinished = false;
					int AIChoice = 0;
					while(AIFinished == false)
					{
						// AI makes move
						if((removeEnabled == true) && (flipEnabled == true))
						{
							AIChoice = rand.nextInt(2*boardWidth+1) - (boardWidth+1);
						}
						else if((removeEnabled == false) && (flipEnabled == true))
						{
							AIChoice = rand.nextInt(boardWidth+1) - 1;
						}
						else if((removeEnabled == true) && (flipEnabled == false))
						{
							AIChoice = rand.nextInt(2*boardWidth) - boardWidth;
							if(AIChoice < 0)
							{
									AIChoice = 	AIChoice - 1;
							}
						}
						else
						{
							AIChoice = rand.nextInt(boardWidth);
						}

						found = false;
						yLocation = boardHeight - 1;
						////////////////////////////////////////////////////////
						if(AIChoice > -1)
						{
							while(!found)
							{
								if(gameBoard[yLocation][ AIChoice] == 0)
								{
									if(userColor == "Red")
									{
										gameBoard[yLocation][AIChoice] = 2;
									}
									else if(userColor == "Black")
									{
										gameBoard[yLocation][AIChoice] = 1;

									}
									found = true;
									 AIFinished = true;
									System.out.println("");
									System.out.print("The AI chose to place a piece in column ");
									System.out.println(AIChoice+1);
								}
								else
								{
									if(yLocation > 0)
									{
										yLocation = yLocation - 1;
									}
									else
									{
										AIChoice = rand.nextInt(2*boardWidth+1) - (boardWidth+1);
										yLocation = boardHeight - 1;
									}
								}
							}
						}
						else if (AIChoice < -1)
						{
							// remove piece
							xLocation = Math.abs(AIChoice) - 2;
							if((userColor == "Red") && (gameBoard[yLocation][xLocation] != 2))
							{
								AIChoice = rand.nextInt(2*boardWidth+1) - (boardWidth+1);
								yLocation = boardHeight - 1;
							}
							else if((userColor == "Black") && (gameBoard[yLocation][xLocation] != 1))
							{
								AIChoice = rand.nextInt(2*boardWidth+1) - (boardWidth+1);
								yLocation = boardHeight - 1;
							}
							else
							{
								while(yLocation > 0)
								{
									gameBoard[yLocation][xLocation] = gameBoard[yLocation - 1][xLocation];
									yLocation = yLocation - 1;
								}
								gameBoard[yLocation][xLocation] = 0;
								System.out.println("");
								System.out.print("The AI chose to remove it's piece in column ");
								AIFinished = true;
								System.out.println(xLocation+1);
							}
						}
						else if((AIChoice == -1)&&(canFlipBoard==true))
						{
							// flip board
							int topPieceLocation = 0;
							int tempPiece = 0;
							for(int i = 0; i < boardWidth; i++)
							{
								yLocation = boardHeight - 1;
								topPieceLocation = 0;
								found = false;
								while(!found)
								{
									if(gameBoard[yLocation][i] == 0)
									{
										topPieceLocation = yLocation+1;
										found = true;
									}
									else if(yLocation > 0)
									{
										yLocation = yLocation - 1;
									}
									else
									{
										found = true;
										topPieceLocation = 0;
									}
								}

								tempPiece = 0;
								for(int j = boardHeight - 1; j > boardHeight-1-((boardHeight-topPieceLocation)/2); j--)
								{
									tempPiece = gameBoard[j][i];
									gameBoard[j][i] = gameBoard[topPieceLocation+((boardHeight - 1)-j)][i];
									gameBoard[topPieceLocation+((boardHeight - 1)-j)][i] = tempPiece;

								}
							}
							System.out.println("");
							System.out.println("The AI chose to flip the board.");
							AIFinished = true;
						}

						else if((AIChoice == -1)&&(canFlipBoard==false))
						{
							AIChoice = rand.nextInt(2*boardWidth+1) - (boardWidth+1);
							yLocation = boardHeight - 1;
						}
					}


					if(canFlipBoard == false)
					{
						canFlipBoard = true;
					}
					else if((AIChoice+1) == 0)
					{
						canFlipBoard = false;
					}

					////////////////////////////////////////////////////////


				}


			}
			else if((roomNum == 3) && (command.equals("Again")))
			{
				ShowPlayAgain();
				PlayAgain();
			}
			else if(command.equals("Exit"))
			{
				running = false;
			}
			else if(!command.equals("no op"))
			{
				System.out.println("");
				System.out.println("Invalid command.");
			}

		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static int roomNum;
	private static int columnNum;
	private static String userColor;
	private static int boardWidth;
	private static int boardHeight;
	private static boolean running;
	private static boolean gameOver;
	private static boolean userWon;
	private static boolean canFlipBoard;
	private static boolean flipEnabled;
	private static boolean removeEnabled;
	private static int[][] gameBoard;
	private static int windowSize;
	private static Game_Window_Gui GUI;
	private static JFrame TitleScreen;
	private static JFrame ColorSelectScreen;
	private static JFrame GameScreen;
	private static JFrame PlayAgainScreen;
	private static JFrame ScoreScreen;
	private static JLabel[][] gameBoardPieces;
}

class ImagePanel extends JPanel {

  private Image img;

  public ImagePanel(String img, int windowSize) {
	Image image;
	try {
		image = ImageIO.read(new File("images/titleScreen.jpg"));
		image=image.getScaledInstance(windowSize,windowSize,0);
		this.img=image;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
  }

  public void paintComponent(Graphics g) {
    g.drawImage(img, 0, 0, null);
  }

}

class ButtonListener implements ActionListener{
	
	public ButtonListener(){
	
	}
	
	public void actionPerformed(ActionEvent e) {
		if ("Start Game".equals(e.getActionCommand())){
			System.out.println("Start Game");
		}
		if("Quit".equals(e.getActionCommand())){
			System.out.println("Quit");
		}
	}
}
