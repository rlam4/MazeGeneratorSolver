package MazeGenerator;

import java.util.Random;

/* Each cell is basically a space you can travel through. 
 * The walls are defined as booleans, but in retrospect I could have 
 * made them references to the adjacent walls.
 */

public class Cell {
	
	//True denotes the cell will be open, false closed
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	
	boolean start;
	boolean end;
	boolean visited;
	
	int height;
	int width;
	
	//The standard constructor for a cell that does not have initialized neighbors
	public Cell(int height, int width, int maxHeight, int maxWidth ) {
		//Randomly generate the cells based on the size of maze
		Random rand = new Random();
		this.height = height;
		this.width = width;
		//If the cell happens to lay on the edge, fill it out without randomizing
		if (height == 0) {
			this.up = false;
		} else {
			if(rand.nextInt(2) == 1) {
				up = false;
			} else {
				up = true;
			}
		}
		
		if (height == maxHeight-1) {
			this.down = false;
		} else {
			if(rand.nextInt(2) == 1) {
				down = false;
			} else {
				down = true;
			}
		}
		
		if (width == 0) {
			this.left = false;
		} else {
			if(rand.nextInt(2) == 1) {
				left = false;
			} else {
				left = true;
			}
		}
		
		if (width == maxWidth-1) {
			this.right = false;
		} else {
			if(rand.nextInt(2) == 1) {
				right = false;
			} else {
				right = true;
			}
		}
		
		start = false;
		end = false;
		visited = false;
	}
	
	public void setStart() {
		start = true;
	}
	
	public void setEnd() {
		end = true;
	}
	
}
