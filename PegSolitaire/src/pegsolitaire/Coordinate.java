package pegsolitaire;

import java.util.*;

public class Coordinate {
	
	int x;
	int y;
	
	boolean filled;
	boolean onBoard;
	
	Coordinate()
	{
		x = 0;
		y = 0;
		filled = true;
		onBoard = true;
	}
	
	Coordinate(Coordinate c)
	{
		x = new Integer(c.x);
		y = new Integer(c.y);
		filled = c.filled;
		onBoard = c.onBoard;
	}
	
	Coordinate(int a, int b, boolean f, boolean o)
	{
		x = new Integer(a);
		y = new Integer(b);
		filled = f;
		onBoard = o;
	}
	
	ArrayList<Coordinate> getDests(Board b)
	{
		ArrayList<Coordinate> dests = new ArrayList<Coordinate>();
		System.out.println("here1");
		if(y-2>=-3)
		{
			dests.add(b.getCoordinate(x,y-2));
		}
		if(y+2<=3)
		{
			dests.add(b.getCoordinate(x,y+2));
		}
		if(x-2>=-3)
		{
			dests.add(b.getCoordinate(x-2,y));
		}
		if(x+2<=3)
		{
			dests.add(b.getCoordinate(x+2,y));
		}
		if(y-2>=-3 && x+2<=3)
		{
			dests.add(b.getCoordinate(x+2,y-2));
		}
		if(y-2>=-3 && x-2>=-3)
		{
			dests.add(b.getCoordinate(x-2,y-2));
		}
		if(y+2<=3 && x+2<=3)
		{
			dests.add(b.getCoordinate(x+2,y+2));
		}
		if(y+2<=3 && x-2>=-3)
		{
			dests.add(b.getCoordinate(x-2,y+2));
		}
		System.out.println("here2");
		return dests;
	}
	
	ArrayList<Coordinate> getJumps(Board b)
	{
		ArrayList<Coordinate> jumps = new ArrayList<Coordinate>();
		
		//get all positions that could be destinations
		
		/*if(Coordinate up = b.getCoordinate(x,y+2)
		{
			if(up.filled == false)
		}
		Coordinate down = b.coordinates[x][y-2];
		Coordinate left = b.coordinates[x-2][y];
		Coordinate right = b.coordinates[x+2][y];
		Coordinate upright = b.coordinates[x+2][y+2];
		Coordinate upleft = b.coordinates[x-2][y+2];
		Coordinate downright = b.coordinates[x+2][y-2];
		Coordinate downleft = b.coordinates[x-2][y-2];
		//make sure dest's are empty
		//make sure in betwn there is a peg
		
		//USE THE CANJUMP() FUNCTION THAT IS BELOW TO DO THIS EASILY
		*/
		
		ArrayList<Coordinate> dests = getDests(b);
		for(int i = 0; i<dests.size(); i++)
		{
			Coordinate middle = canJump(b, dests.get(i));
			if(middle != null)
			{
				jumps.add(dests.get(i));
			}
			System.out.println("hereish " + i);
		}
		//down
		/*Coordinate dest = b.getCoordinate(x,y-2);
		Coordinate middle = canJump(b, dest);
		if(middle != null)
		{
			jumps.add(dest);
		}
		//up
		dest = b.getCoordinate(x,y+2);
		middle = canJump(b, dest);
		if(middle != null)
		{
			jumps.add(dest);
		}
		//left
		dest = b.getCoordinate(x-2,y);
		middle = canJump(b, dest);
		if(middle != null)
		{
			jumps.add(dest);
		}
		//right
		dest = b.getCoordinate(x+2,y);
		middle = canJump(b, dest);
		if(middle != null)
		{
			jumps.add(dest);
		}
		//upright
		dest = b.getCoordinate(x+2,y+2);
		middle = canJump(b, dest);
		if(middle != null)
		{
			jumps.add(dest);
		}
		//upleft
		dest = b.getCoordinate(x-2,y+2);
		middle = canJump(b, dest);
		if(middle != null)
		{
			jumps.add(dest);
		}
		//downleft
		dest = b.getCoordinate(x-2,y-2);
		middle = canJump(b, dest);
		if(middle != null)
		{
			jumps.add(dest);
		}
		//downright
		dest = b.getCoordinate(x+2,y-2);
		middle = canJump(b, dest);
		if(middle != null)
		{
			jumps.add(dest);
		}*/
		
		return jumps;
	}
	
	int max(int a, int b)
	{
		if(a>b)
		{
			return a;
		}
		return b;
	}
	int min(int a, int b)
	{
		if(a<b)
		{
			return a;
		}
		return b;
	}
	
	//finds out the direction of the jump
	int getJumpType(Coordinate dest)
	{
		//right
		if(dest.x>this.x && dest.y == this.y)
		{
		System.out.println("right");
			return 0;
		}
		//left
		if(dest.x<this.x && dest.y == this.y)
		{System.out.println("left");
			return 1;
		}
		//up
		if(dest.y>this.y && dest.x == this.x)
		{System.out.println("up");
			return 2;
		}
		//down
		if(dest.y<this.y && dest.x == this.x)
		{System.out.println("down");
			return 3;
		}
		//upright
		if(dest.y>this.y && dest.x>this.x)
		{System.out.println("upright");
			return 4;
		}
		//upleft
		if(dest.y>this.y && dest.x<this.x)
		{System.out.println("upleft");
			return 5;
		}
		//downright
		if(dest.y<this.y && dest.x>this.x)
		{System.out.println("downright");
			return 6;
		}
		//downleft
		if(dest.y<this.y && dest.x<this.x)
		{System.out.println("downleft");
			return 7;
		}
		
		//err: should never happen tho
		System.out.println("Err: in board getJumpType() function: shouldn't happen but it did");
		return -1;
	}
	
	//checks if the src(this) can jump to the dest
	Coordinate canJump(Board b, Coordinate dest) //returns the middle piece (that has a peg in it) if the requested move is possible; if not returns null
	{
		Coordinate middlePeg = new Coordinate();
		
		int jumpType = this.getJumpType(dest);
		
		//vertical (x's are the same)
		if(jumpType == 2 || jumpType == 3)
		{
			int dif = max(this.y,dest.y)-1;
			middlePeg = b.getCoordinate(this.x,dif);
			if(middlePeg.filled && this.filled && dest.filled == false)
			{
				System.out.println(middlePeg.x + " " + middlePeg.y);
				return middlePeg;
			}
			else
			{
				return null;
			}
		}
		
		//horizontal (y's are the same)
		else if(jumpType == 0 || jumpType == 1)
		{
			int dif = max(this.x,dest.x)-1;
			middlePeg = b.getCoordinate(dif,this.y);
			if(middlePeg.filled && this.filled && dest.filled == false)
			{
				System.out.println(middlePeg.x + " " + middlePeg.y);
				return middlePeg;
			}
			else
			{
				return null;
			}
		}
		//upright/downleft
		else if(jumpType == 4 || jumpType == 7)
		{
			int difx = max(this.x,dest.x)-1;
			int dify = max(this.y,dest.y)-1;
			middlePeg = b.getCoordinate(difx,dify);
			if(middlePeg.filled && this.filled && dest.filled == false)
			{
				return middlePeg;
			}
			else
			{
				return null;
			}
		}
		
		//upleft/downright
		else if(jumpType == 5 || jumpType == 6)
		{
			int difx = max(this.x,dest.x)-1;
			int dify = max(this.y,dest.y)+1;
			middlePeg = b.getCoordinate(difx,dify);
			if(middlePeg.filled && this.filled && dest.filled == false)
			{
				return middlePeg;
			}
			else
			{
				return null;
			}
		}
		
		return null;
	}
	
	
	public String toString()
	{
		if(onBoard == false)
		{
			return "~";
		}
		else if(filled)
		{
			return "X";
		}
		return "O";
	}

}
