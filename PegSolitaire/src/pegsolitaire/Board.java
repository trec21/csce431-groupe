package pegsolitaire;

import java.util.*;

public class Board {
	
	int BOARD_SIZE = 7;
	
	//member variables
	Coordinate[][] coordinates;
	
	//constructors
	Board()
	{
		coordinates = new Coordinate[BOARD_SIZE][BOARD_SIZE];
		int x = -3;
		int y = 3;
		for(int i = 0; i<BOARD_SIZE; i++)
		{
			for(int j = 0; j<BOARD_SIZE; j++)
			{
				boolean o = true;
				if((y<-1 || y>1) && (x<-1 || x>1))
				{
					o = false;
				}
				boolean f = true;
				if(x==0 && y==0)
				{
					f = false;
				}
				
				Coordinate c = new Coordinate(x,y,f,o);
				coordinates[i][j] = c;
				
				x++;
			}
			y--;
			x=-3;
		}
	}
	Board(Board b)
	{
		coordinates = new Coordinate[BOARD_SIZE][BOARD_SIZE];
		for(int i = 0; i<BOARD_SIZE; i++)
		{
			for(int j = 0; j<BOARD_SIZE; j++)
			{
				coordinates[i][j] = new Coordinate(b.coordinates[i][j]);
			}
		}
	}
	
	//end constructors
	
	//member functions
	ArrayList<Board> getPossibleMoves()
	{
		ArrayList<Board> moves = new ArrayList<Board>();
		for(int i = 0; i<BOARD_SIZE; i++)
		{
			for(int j = 0; j<BOARD_SIZE; j++)
			{
				ArrayList <Coordinate> jumps = coordinates[i][j].getJumps(this); //returns a list of all destinations from this src
				for(int k = 0; k<jumps.size(); k++)
				{
					Board b = new Board(this);
					b.move(coordinates[i][j], jumps.get(i));
					moves.add(b);
				}
			}
		}
		return moves;
	}
	
	Coordinate getCoordinate(int x, int y)
	{
		for(int i = 0; i<BOARD_SIZE; i++)
		{
			for(int j = 0; j<BOARD_SIZE; j++)
			{
				if(x == coordinates[i][j].x && y == coordinates[i][j].y)
				{
					return coordinates[i][j];
				}
			}
		}
		return null;
	}
	
	//makes a move and returns true if it was successful
	boolean move(Coordinate src, Coordinate dest)
	{
		Coordinate middlePeg = src.canJump(this, dest);
		
		if(middlePeg != null)
		{
			middlePeg.filled = false;
			src.filled = false;
			dest.filled = true;
			return true;
		}
		return false;
	}
	
	//print the board to the terminal
	void printBoard()
	{
		for(int i = 0; i<BOARD_SIZE; i++)
		{
			if(i-3 >= 0)//if positive print two spaces
			{
				System.out.print("  ");
			}
			else//if negative print one space (the '-' takes up a space)
			{
				System.out.print(" ");
			}	
			System.out.print(i-3); //prints the y-axis
			System.out.print(" ");
			
			for(int j = 0; j<BOARD_SIZE; j++)
			{
				System.out.print(coordinates[i][j] + " ");
				
			}
			System.out.println();
		}
		System.out.print("   "); //start the axis 3 chars to the right
		for(int i = 0; i<BOARD_SIZE; i++)
		{
			if(i-3 >= 0)//if positive print a space between each #
			{
				System.out.print(" ");
			}
			System.out.print(i-3); //prints the x-axis
		}
		System.out.println();
	}

}
