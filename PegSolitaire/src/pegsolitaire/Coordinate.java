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
            x = (c.x);
            y = (c.y);
            filled = c.filled;
            onBoard = c.onBoard;
        }

        Coordinate(int a, int b, boolean f, boolean o)
        {
            x = a;
            y = b;
            filled = f;
            onBoard = o;
        }

        ArrayList<Coordinate> getTwoSpacesAway(Board b)
	    {
		    ArrayList<Coordinate> dests = new ArrayList<Coordinate>();
		    if(y-2>=-3)
		    {
			    dests.add(b.getCoordinate(x,y-2));
		    }
		    if(y+2<=3)
		    {
                dests.add(b.getCoordinate(x, y + 2));
		    }
		    if(x-2>=-3)
		    {
                dests.add(b.getCoordinate(x - 2, y));
		    }
		    if(x+2<=3)
		    {
                dests.add(b.getCoordinate(x + 2, y));
		    }
		    if(y-2>=-3 && x+2<=3)
		    {
                dests.add(b.getCoordinate(x + 2, y - 2));
		    }
		    if(y-2>=-3 && x-2>=-3)
		    {
                dests.add(b.getCoordinate(x - 2, y - 2));
		    }
		    if(y+2<=3 && x+2<=3)
		    {
                dests.add(b.getCoordinate(x + 2, y + 2));
		    }
		    if(y+2<=3 && x-2>=-3)
		    {
                dests.add(b.getCoordinate(x - 2, y + 2));
		    }
		    return dests;
	    }

         ArrayList<Move> getJumpsSrc(Board b)
	    {
            ArrayList<Move> jumps = new ArrayList<Move>();
		    
		    //get all positions that could be destinations


            ArrayList<Coordinate> dests = getTwoSpacesAway(b);
		    for(int i = 0; i<dests.size(); i++)
		    {
			    Coordinate middle = canJump(b, dests.get(i));
			    if(middle != null)
			    {
                    Move m = new Move(this, dests.get(i), middle);
                    jumps.add(m);
				    //jumps.Add(dests[i]);
			    }
			    //System.out.println("hereish " + i);
		    }
		
		    return jumps;
	    }

         ArrayList<Move> getJumpsDest(Board b)
        {
            ArrayList<Move> jumps = new ArrayList<Move>();

            ArrayList<Coordinate> srcs = getTwoSpacesAway(b);
            for (int i = 0; i < srcs.size(); i++)
            {
                Coordinate middle = srcs.get(i).canJump(b, this);
                if (middle != null)
                {
                    Move m = new Move(srcs.get(i), this, middle);
                    jumps.add(m);
                    //jumps.Add(dests[i]);
                }
                //System.out.println("hereish " + i);
            }

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
		    if(this.x+2==dest.x && dest.y == this.y)
		    {
		    
			    return 0;
		    }
		    //left
		    if(this.x-2==dest.x && dest.y == this.y)
		    {
			    return 1;
		    }
		    //up
		    if(this.y+2==dest.y && dest.x == this.x)
		    {
			    return 2;
		    }
		    //down
		    if(this.y-2==dest.y && dest.x == this.x)
		    {
			    return 3;
		    }
		    //upright
            if (this.y + 2 == dest.y && this.x + 2 == dest.x)
		    {
			    return 4;
		    }
		    //upleft
            if (this.y + 2 == dest.y && this.x - 2 == dest.x)
		    {
			    return 5;
		    }
		    //downright
            if (this.y - 2 == dest.y && this.x + 2 == dest.x)
		    {
			    return 6;
		    }
		    //downleft
            if (this.y - 2 == dest.y && this.x - 2 == dest.x)
		    {
			    return 7;
		    }
		
		    //err: should never happen tho
		    //System.out.println("Err: in board getJumpType() function: shouldn't happen but it did");
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
				    //System.out.println(middlePeg.x + " " + middlePeg.y);
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
				    //System.out.println(middlePeg.x + " " + middlePeg.y);
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
