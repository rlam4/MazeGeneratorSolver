package MazeGenerator;

import java.util.Random;
import java.util.Stack;

public class Main {

	static char[][] toPrint;
	static Cell[][] maze;
	static Stack<Cell> stack; 
	static Stack<Cell> path;
	static boolean solved;

	public static void main(String[] args) {

		//Modify the size here
		int height = 4;
		int width = 7;


		maze = new Cell[height][width];
		stack = new Stack<Cell>();
		path = new Stack<Cell>();

		//Construct the maze
		for(int i = 0 ; i < height; i++) {
			for(int j = 0; j < width; j++) {
				maze[i][j] = new Cell(i, j, height, width);
			}
		}

		//Match the walls (since I opted to random generate each wall
		for(int i = 0 ; i < height; i++) {
			for(int j = 0; j < width; j++) {
				//match current cells up with top's down
				if(i != 0) {
					maze[i][j].up = maze[i-1][j].down;
				}

				//match current cells down with bottom's up
				if(i != height-1) {
					maze[i][j].down = maze[i+1][j].up;
				}

				//match current cells left cell with the previous right
				if(j != 0) {
					maze[i][j].left = maze[i][j-1].right;
				}

				//match current cells right with the next cells left
				if(j != width-1) {
					maze[i][j].right = maze[i][j+1].left;
				}
			}
		}

		System.out.println("Creating a maze of " + height + "x" + width + "\n");
		
		//Randomly place a start and an end.
		Random rand = new Random();
		int startHeight = rand.nextInt(height-1);
		int startWidth = rand.nextInt(width-1);

		int endHeight = rand.nextInt(height-1);
		int endWidth = rand.nextInt(width-1);
		
		//reroll the end if they're the same spot
		while(endHeight == startHeight && endWidth == startWidth) {
			endHeight = rand.nextInt(height-1);
			endWidth = rand.nextInt(width-1);
		}

		maze[startHeight][startWidth].start = true;
		maze[endHeight][endWidth].end = true;

		//Print out the maze first
		printMaze(maze, height, width);
		
		//Perform DFS to find the solution.
		stack.push(maze[startHeight][startWidth]);
		DFSSolver(maze[startHeight][startWidth]);
		if(solved){
			printPath(height, width, path);
		} else {
			System.out.println("The above maze does not have a solution.");
		}

	}


	//
	public static void DFSSolver (Cell current) {
		//Check if the end is reached, if so, add it to path

		//if no neighbors are found or at dead end, pop itself
		current.visited = true;

		if(!solved){
			path.push(current);
			if(current.end) {
				System.out.println("SOLVED! Follow the pieces of candy! (o's).\n");
				solved = true;
			} else {
				//Find all adjacent cells
				if(current.up && !maze[current.height-1][current.width].visited) {
					DFSSolver(maze[current.height-1][current.width]);
				} 

				if(current.down && !maze[current.height+1][current.width].visited) {
					DFSSolver(maze[current.height+1][current.width]);
				}

				if(current.left && !maze[current.height][current.width-1].visited) {
					DFSSolver(maze[current.height][current.width-1]);
				}

				if(current.right && !maze[current.height][current.width+1].visited) {
					DFSSolver(maze[current.height][current.width+1]);
				}	

				if(!solved){
					path.pop();
				}

			}
		}

	}

	public static void printPath(int height, int width, Stack<Cell> path) {
		Cell current;
		System.out.println("Cells to visit: ");
		for(int i = 0; i < path.size(); i++) {
			current = path.get(i);
			int cellMiddleHeight = 2*current.height + 1;
			int cellMiddleWidth = 2*current.width + 1;
			if(!current.start && !current.end){
				toPrint[cellMiddleHeight][cellMiddleWidth] = 'o';
			}
			System.out.println(path.get(i).height +"," +path.get(i).width);
		}
		
		System.out.println();
		
		int printHeight = 2*height+1;
		int printWidth = 2*width+1;
		//Print the maze!
		for(int i = 0; i < printHeight; i++) {
			for(int j = 0; j <printWidth; j++) {
				System.out.print(toPrint[i][j]);
			}
			System.out.println();
		}
	}

	public static void printMaze(Cell[][] maze, int height, int width) {
		//example, 1x2 maze, grid needed is a (2(1) + 1 by 2(2)+1) = 3x5
		//  *x*x* +2 for start, and 2 for each so 2x + 1 
		//  xsxex
		//  *x*x*
		//Corners are filled out with *
		//x denotes a blocked wall
		//an empty space is an open space
		//s denotes start, e denotes end.

		int printHeight = 2*height+1;
		int printWidth = 2*width+1;
		toPrint = new char[printHeight][printWidth];

		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				int cellMiddleHeight = 2*i + 1;
				int cellMiddleWidth = 2*j + 1;
				
				//print the corners, could populate it the grid with * to begin with
				toPrint[cellMiddleHeight-1][cellMiddleWidth-1] = '*';
				toPrint[cellMiddleHeight+1][cellMiddleWidth-1] = '*';
				toPrint[cellMiddleHeight-1][cellMiddleWidth+1] = '*';
				toPrint[cellMiddleHeight+1][cellMiddleWidth+1] = '*';
				
				//print necessary walls
				if (maze[i][j].up) {
					toPrint[cellMiddleHeight-1][cellMiddleWidth] = ' ';
				} else {
					toPrint[cellMiddleHeight-1][cellMiddleWidth] = 'x';
				}

				if (maze[i][j].down) {
					toPrint[cellMiddleHeight+1][cellMiddleWidth] = ' ';
				} else {
					toPrint[cellMiddleHeight+1][cellMiddleWidth] = 'x';
				}

				if (maze[i][j].left) {
					toPrint[cellMiddleHeight][cellMiddleWidth-1] = ' ';
				} else {
					toPrint[cellMiddleHeight][cellMiddleWidth-1] = 'x';
				}

				if (maze[i][j].right) {
					toPrint[cellMiddleHeight][cellMiddleWidth+1] = ' ';
				} else {
					toPrint[cellMiddleHeight][cellMiddleWidth+1] = 'x';
				}

				//print start/end/blank
				if (maze[i][j].start) {
					toPrint[cellMiddleHeight][cellMiddleWidth] = 'S';
				} else if (maze[i][j].end) {
					toPrint[cellMiddleHeight][cellMiddleWidth] = 'E';
				} else {
					toPrint[cellMiddleHeight][cellMiddleWidth] = ' ';
				}
			}
		}

		//Print the maze!
		for(int i = 0; i < printHeight; i++) {
			for(int j = 0; j <printWidth; j++) {
				System.out.print(toPrint[i][j]);
			}
			System.out.println();
		}

		System.out.println();
	}

}
