package pegsolitaire;


import java.util.Scanner;

public class Game {
	
	public static void main(String[] args)
	{
		Board b = new Board();
		b.printBoard();
		
		Scanner scan = new Scanner(System.in);
		
		while (true) 
		{
			System.out.println("Please enter in the src coordinates");
			int x1 = scan.nextInt();
			int y1 = scan.nextInt();
			System.out.println("Please enter in the dest coordinates");
			int x2 = scan.nextInt();
			int y2 = scan.nextInt();
			
			Coordinate c1 = b.getCoordinate(x1, y1);
			Coordinate c2 = b.getCoordinate(x2, y2);
			
			if(c1 != null && c2 != null)
			{
				//input was good, proceed to make the move if possible
				boolean moveMade = b.move(c1, c2);
				if(moveMade == false)
				{
					System.out.println("Bad Move: try again");
				}
				else
				{
					b.printBoard();
				}
			}
			else if(x1 == 99)
			{
				System.out.println("So you want some help?");
				if(scan.nextInt()==1)
				{
					//Solver s = new Solver();
					//Coordinate c3 = s.getNextMove(b);
					//System.out.println("Try: "+c3.x+ " " + c3.y);
				}
			}
			else
			{
				//err one or both of the coordinates given by user was bad
				System.out.println("ERRRRRRR: bad input");
				break;
			}
			
			
	      }
	}

}
