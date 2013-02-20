/**
 * @(#)Game_Window_gui.java
 *
 *
 * @author 
 * @version 1.00 2012/3/17
 */
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;

public class Game_Window_Gui extends JPanel{
	private int windowSize;
	private int boardWidth;
	private int boardHeight;
	private int spacingX;
	private int spacingY;
	private static Color[][] gameBoardColor;
	private static JLabel[][] gameBoardPieces; //jframes handle events
	private Game_Window window;
		
    public Game_Window_Gui(Game_Window win) {
    	this.boardHeight = win.getBoardHeight();
    	this.boardWidth = win.getBoardWidth();
    	this.windowSize = win.getWindowSize();
    	window = win;
    	spacingX = (windowSize+10)/(boardWidth);
    	spacingY = (windowSize+10)/(boardHeight);
    	gameBoardColor = new Color[boardHeight][boardWidth];
    	gameBoardPieces = new JLabel[boardHeight][boardWidth];
    }
    
    public int getColumn(JLabel label) {
    	for(int column =0; column<boardWidth; column++){
    	 	for(int row =0; row<boardHeight; row++){
        		if (gameBoardPieces[column][row] == label) { 
        			System.out.println(column + " was selected.\n");
          			return column; 
        		} 
      		} 
    	} 
    	return -1; 
  	} 
    
    public void paintComponent( Graphics g ){
    	 super.paintComponent( g );
    	 
    	 Graphics2D g2d = ( Graphics2D ) g;
    	 g2d.setColor(Color.yellow);
    	 g2d.fillRect(0, 0, windowSize+10, windowSize+10);
    	 for(int column =0; column<boardWidth; column++){
    	 	for(int row =0; row<boardHeight; row++){
    	 		g2d.setColor(gameBoardColor[row][column]);
    	 		g2d.fill(new Ellipse2D.Double(column*spacingX,row*spacingY, Math.min(spacingX, spacingY), Math.min(spacingX, spacingY)));
    	 	}
    	 	
    	 }
    	 
    }
    
    public void changePieceColor(int col, int row, Color c){
    	gameBoardColor[col][row] = c;
    	repaint();
    }
    
    public void Init(){
    	JFrame GameWindow = window.GameScreen();
       	JPanel panel = (JPanel) GameWindow.getGlassPane();
       	panel.setLayout(new GridLayout(boardHeight,boardWidth));//grid layout
       	for(int row =0; row<boardHeight; row++){
    	 	for(int column =0; column<boardWidth; column++){
    	 		gameBoardPieces[column][row] = new JLabel();		                  
  				gameBoardPieces[column][row].setHorizontalAlignment(SwingConstants.CENTER); 
				gameBoardPieces[column][row].setBorder(new LineBorder(Color.red)); //makes a grid
        		panel.add(gameBoardPieces[column][row]); //adds it to the panel
    	 		gameBoardColor[column][row] = Color.white;
    	 	}
    	 	
    	 }
    	 panel.show();
    	 GameWindow.setGlassPane(panel);//glass panels are always on the top and used to handle events
    	 window.SetGameScreen(GameWindow);
    }
    
     
	
	public void addListener(MouseMethods listener) { 
    	for (int row=0; row<boardHeight; row++) { 
     		for (int column=0; column<boardWidth; column++) { 
       			gameBoardPieces[column][row].addMouseListener(listener); 
      		} 
    	} 
  	} 
	 

    
}