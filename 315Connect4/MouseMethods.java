import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;

public class MouseMethods implements MouseListener {
	private Game_Window window;
	private Game_Window_Gui GUI;
	
	public MouseMethods(Game_Window win, Game_Window_Gui gui) {
		window = win;	
		GUI = gui;
		GUI.addListener(this);
	}
	
	public void mouseClicked(MouseEvent e) {
    	int column = GUI.getColumn((JLabel) e.getSource()); 
    	int row = window.getRow(column); 
    	System.out.println("column: " + column + " row: " + row);
    	if (row != -1) { 
    		int[][] temp = window.getGameBoard();
    		temp[column][row]=1;
    		window.setGameBoard(temp);
    		GUI.changePieceColor(window.getBoardHeight()-row-1,column, window.getPlayerColor());
      		//change color of this spot 
    	} 	
	}

	public void mouseEntered(MouseEvent e) {	
	}

	public void mouseExited(MouseEvent e) {	
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
	
}