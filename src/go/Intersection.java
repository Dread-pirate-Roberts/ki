/**
 *@author: David Buebe
 *@date:Feb 20, 2013
 */

package go;

import java.io.Serializable;

public class Intersection implements Serializable
{
	private static final long serialVersionUID = -5093527831145393912L;

	public enum piece{
		white,black,free,edge
	}
	
	public piece team;
	public int blackInfluence, whiteInfluence;
	public boolean visited;
	
	public Intersection() {
		team = piece.free;
		blackInfluence = 0;
		whiteInfluence = 0;
		visited = false;
	}
	
	public Intersection(piece p) {
		team = p;
		blackInfluence = 0;
		whiteInfluence = 0;
		visited = false;
	}
	
	public Intersection(Intersection rhs) {
		team = rhs.team;
		blackInfluence = rhs.blackInfluence;
		whiteInfluence = rhs.whiteInfluence;
		visited = rhs.visited;
	}

	protected void updateBlackInfluence(int i) {
		if(i < blackInfluence) {
			blackInfluence = i;
		}	
	}
	
	protected void updateWhiteInfluence(int i ) {
		if(i < whiteInfluence) {
			whiteInfluence = i;
		}
	}
	
	public boolean blackHasMoreInfluence() {
		return blackInfluence < whiteInfluence;
	}
	
	public boolean whiteHasMoreInfluence() {
		return whiteInfluence < blackInfluence;
	}
	
	public boolean influenceIsEqual() {
		return blackInfluence == whiteInfluence;
	}
}