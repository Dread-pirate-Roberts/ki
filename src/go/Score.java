/**
 *@author: David Buebe
 *@date:Feb 6, 2013
 */

package go;

import java.util.ArrayList;

import go.Intersection.piece;

@SuppressWarnings("unused")
public class Score {

	protected Board board;

	public Score(Board rhs) {
		board = rhs;
		setInfluence();
	}

	public piece getTeam(int i, int j) {
		return board.source[i][j].team;
	}

	public boolean isFree(int i, int j) {
		return board.is_free(i, j);
	}

	public boolean isInRange(int i, int j) {
		boolean ret = i >= 0;
		ret &= i < this.board.size;
		ret &= j >= 0;
		ret &= j < this.board.size;
		return ret;
	}

	public void setBoardUnvisted() {
		for (Intersection j[] : this.board.source) {
			for (Intersection i : j) {
				i.visited = false;
			}
		}
	}

	protected void setInfluence() {
		clearInfluence();
		setBoardUnvisted();

		for (int i = 0; i < this.board.size; i++) {
			for (int j = 0; j < this.board.size; j++) {
				set_piece_influence(i, j);
				setBoardUnvisted();
			}
		}
	}

	protected void set_piece_influence(int i, int j) {
		piece p = getTeam(i, j);

		if (p == piece.white) {
			set_white_influence(i, j);
		} else if (p == piece.black) {
			set_black_influence(i, j);
		}
	}

	protected void set_white_influence_floodfill(int i, int j, int influ, int depth) {
		this.increase_white_influence(i, j);

		if (depth > 0) {
			if (isFree(i - 1, j)) {
				set_white_influence_floodfill(i - 1, j, influ + 1, depth - 1);
			}
			if (isFree(i, j - 1)) {
				set_white_influence_floodfill(i, j - 1, influ + 1, depth - 1);
			}
			if (isFree(i + 1, j)) {
				set_white_influence_floodfill(i + 1, j, influ + 1, depth - 1);
			}
			if (isFree(i, j + 1)) {
				set_white_influence_floodfill(i, j + 1, influ + 1, depth - 1);
			}
		}
	}

	protected void set_black_influence_floodfill(int i, int j, int influ, int depth) {
		this.increase_black_influence(i, j);

		if (depth > 0) {
			if (isFree(i - 1, j)) {
				set_black_influence_floodfill(i - 1, j, influ + 1, depth - 1);
			}
			if (isFree(i, j - 1)) {
				set_black_influence_floodfill(i, j - 1, influ + 1, depth - 1);
			}
			if (isFree(i + 1, j)) {
				set_black_influence_floodfill(i + 1, j, influ + 1, depth - 1);
			}
			if (isFree(i, j + 1)) {
				set_black_influence_floodfill(i, j + 1, influ + 1, depth - 1);
			}
		}
	}

	protected void set_white_influence(int i, int j) {
		set_white_influence_floodfill(i, j, 0, 5);
	}

	protected void set_black_influence(int i, int j) {
		set_black_influence_floodfill(i, j, 0, 5);
	}

	protected void update_white_influence_dist(int i, int j, int influence) {
		board.source[i][j].updateWhiteInfluence(influence);
	}

	protected void update_black_influence_dist(int i, int j, int influence) {
		board.source[i][j].updateBlackInfluence(influence);
	}

	protected void increase_black_influence(int i, int j) {
		board.source[i][j].blackInfluence++;
	}

	protected void increase_white_influence(int i, int j) {
		board.source[i][j].whiteInfluence++;
	}

	protected double getWhiteInfluence() {
		double ret = 0.0;

		for (Intersection row[] : board.source) {
			for (Intersection intersection : row) {
				if (intersection.team == piece.free) {
					if (intersection.whiteInfluence > intersection.blackInfluence) {
						ret++;
					} else if (intersection.whiteInfluence == intersection.blackInfluence) {
						ret += 0.5;
					}
				}
			}
		}
		return ret;
	}

	protected double getBlackInfluence() {
		double ret = 0.0;

		for (Intersection row[] : board.source) {
			for (Intersection intersection : row) {
				if (intersection.team == piece.free) {
					if (intersection.blackInfluence > intersection.whiteInfluence) {
						ret++;
					} else if (intersection.blackInfluence == intersection.whiteInfluence) {
						ret += 0.5;
					}
				}
			}
		}
		return ret;
	}

	protected double getBlackTerritory() {
		return getBlackInfluence() - board.black_prisoners;
	}

	protected double getWhiteTerritory() {
		return getWhiteInfluence() - board.white_prisoners;
	}

	protected double getWhiteArea() {
		return getWhiteInfluence() + board.white_stones;
	}

	protected double getBlackArea() {
		return getBlackInfluence() + board.black_stones;
	}

	protected void clearInfluence() {
		for (Intersection i[] : board.source) {
			for (Intersection j : i) {
				j.blackInfluence = 0;
				j.whiteInfluence = 0;
			}
		}
	}
}
