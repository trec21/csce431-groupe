/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pegsolitaire;

/**
 *
 * @author Tom
 */
public class Move
{
    public Coordinate src;
    public Coordinate dest;
    public Coordinate middle;

    public Move(Coordinate s, Coordinate d, Coordinate m)
    {
        src = s;
        dest = d;
        middle = m;
    }
}
