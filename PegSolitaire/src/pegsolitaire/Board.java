package pegsolitaire;

import java.util.*;

public class Board
    {

        public int BOARDSIZE = 7;

        //member variables
        ArrayList< ArrayList<Coordinate> > coordinates = new ArrayList< ArrayList<Coordinate> >();

        //constructors
        public Board()
	    {
            for (int i = 0; i < BOARDSIZE; i++)
            {
                ArrayList<Coordinate> list_c = new ArrayList<Coordinate>();
                for (int j = 0; j < BOARDSIZE; j++)
                {
                    Coordinate c = new Coordinate();
                    list_c.add(c);
                }
                coordinates.add(list_c);
            }

		    int x = -3;
		    int y = 3;
		    for(int i = 0; i<BOARDSIZE; i++)
		    {
			    for(int j = 0; j<BOARDSIZE; j++)
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
				    coordinates.get(i).set(j,c);
				
				    x++;
			    }
			    y--;
			    x=-3;
		    }
	    }
        public Board(Board b)
	    {
            for (int i = 0; i < BOARDSIZE; i++)
            {
                ArrayList<Coordinate> list_c = new ArrayList<Coordinate>();
                for (int j = 0; j < BOARDSIZE; j++)
                {
                    Coordinate c = new Coordinate();
                    list_c.add(c);
                }
                coordinates.add(list_c);
            }

		    for(int i = 0; i<BOARDSIZE; i++)
		    {
			    for(int j = 0; j<BOARDSIZE; j++)
			    {
				    coordinates.get(i).set(j,b.coordinates.get(i).get(j));
			    }
		    }
	    }

        //end constructors

        //member functions
        private boolean is_on_board(Coordinate c)
        {
            if((c.x < -1 || c.x > 1) && (c.y < 2 || c.y > -2))
                return true;
            return false;
        }

        public boolean win()
        {
            boolean win = false;
            for (int i = 0; i < BOARDSIZE; i++)
            {
                for (int j = 0; j < BOARDSIZE; j++)
                {
                    if (coordinates.get(i).get(j).filled && coordinates.get(i).get(j).onBoard && win == false)
                    {
                        win = true;
                    }
                    else if (coordinates.get(i).get(j).filled && coordinates.get(i).get(j).onBoard && win)
                    {
                        return false;
                    }
                }
            }
            return true;
        }

        /*public ArrayList<Board> getPossibleMoves()
        {
            ArrayList<Board> moves = new ArrayList<Board>();
            for (int i = 0; i < BOARDSIZE; i++)
            {
                for (int j = 0; j < BOARDSIZE; j++)
                {
                    ArrayList<Coordinate> jumps = coordinates[i][j].getJumps(this); //returns a list of all destinations from this src
                    for (int k = 0; k < jumps.Count; k++)
                    {
                        Board b = new Board(this);
                        b.move(coordinates[i][j], jumps[i]);
                        moves.Add(b);
                    }
                }
            }
            return moves;
        }*/

        public Coordinate getCoordinate(int x, int y)
        {
            for (int i = 0; i < BOARDSIZE; i++)
            {
                for (int j = 0; j < BOARDSIZE; j++)
                {
                    if (x == coordinates.get(i).get(j).x && y == coordinates.get(i).get(j).y)
                    {
                        return coordinates.get(i).get(j);
                    }
                }
            }
            return null;
        }

        //makes a move and returns true if it was successful
        public Coordinate move(Coordinate src, Coordinate dest)
        {
            Coordinate middlePeg = src.canJump(this, dest);

            if (middlePeg != null)
            {
                middlePeg.filled = false;
                src.filled = false;
                dest.filled = true;

                return middlePeg;
            }
            return null;
        }

        public Coordinate make_move(Move move)
        {
            Coordinate srcPeg = this.getCoordinate(move.src.x, move.src.y);
            Coordinate destPeg = this.getCoordinate(move.dest.x, move.dest.y);
            Coordinate middlePeg = srcPeg.canJump(this, destPeg);

            if (middlePeg != null)
            {
                middlePeg.filled = false;
                srcPeg.filled = false;
                destPeg.filled = true;

                return middlePeg;
            }
            return null;
        }

        public void un_make_move(Move move)
        {
            /*Coordinate srcPeg = this.getCoordinate(move.src.x, move.src.y);
            Coordinate destPeg = this.getCoordinate(move.dest.x, move.dest.y);
            Coordinate middlePeg = destPeg.canJump(this, srcPeg,false); //see if you can jump from dest to src

            if (middlePeg != null)
            {
                middlePeg.filled = false;
                srcPeg.filled = false;
                destPeg.filled = true;

                return middlePeg;
            }
            return null;*/

            Coordinate srcPeg = this.getCoordinate(move.src.x, move.src.y);
            Coordinate destPeg = this.getCoordinate(move.dest.x, move.dest.y);
            Coordinate middlePeg = this.getCoordinate(move.middle.x, move.middle.y);
            middlePeg.filled = true;
            srcPeg.filled = true;
            destPeg.filled = false;
        }

        public ArrayList<Move> get_all_possible_moves()
        {
            ArrayList<Move> moves = new ArrayList<Move>();

            //ArrayList<Board> moves = new ArrayList<Board>();
            for (int i = 0; i < BOARDSIZE; i++)
            {
                for (int j = 0; j < BOARDSIZE; j++)
                {
                    if (coordinates.get(i).get(j).onBoard && !coordinates.get(i).get(j).filled) //***********remove the '!' if using getJumpsSrc()
                    {
                        ArrayList<Move> jumps = coordinates.get(i).get(j).getJumpsDest(this); //returns a list of all moves for 'this' as src
                        moves.addAll(jumps);
                    }
                }
            }
            return moves;
        }
	
    //print the board to the terminal
    void printBoard()
    {
        for(int i = 0; i<BOARDSIZE; i++)
        {
            if(3-i >= 0)//if positive print two spaces
            {
                    System.out.print("  ");
            }
            else//if negative print one space (the '-' takes up a space)
            {
                    System.out.print(" ");
            }	
            System.out.print(3-i); //prints the y-axis
            System.out.print(" ");

            for(int j = 0; j<BOARDSIZE; j++)
            {
                    System.out.print(coordinates.get(i).get(j) + " ");

            }
            System.out.println();
        }
        System.out.print("   "); //start the axis 3 chars to the right
        for(int i = 0; i<BOARDSIZE; i++)
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
