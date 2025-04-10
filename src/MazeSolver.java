/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */

    // This returns the solution by tracing through the parents and returning the new array
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Used to reverse parent order for the solution
        ArrayList<MazeCell> path = new ArrayList<MazeCell>();
        Stack<MazeCell> revert = new Stack<MazeCell>();
        MazeCell nextCell = maze.getEndCell();
        // Put the reversed path into a stack
        // Adds all parents to stack
        while (nextCell != maze.getStartCell()){
            revert.push(nextCell);
            nextCell = nextCell.getParent();
        }
        // Make sure to include the start cell
        revert.push(maze.getStartCell());
        // Pop out of stack into ArrayList so the order is effectively reversed
        while (!revert.empty()){
            path.add(revert.pop());
        }
        return path;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Uses stack to keep track of where to go next
        MazeCell cell = maze.getStartCell();
        Stack<MazeCell> cells = new Stack<MazeCell>();
        while (cell != maze.getEndCell()){
            // Checks West
            DFS(cell.getRow() - 1, cell.getCol(), cell, cells);
            // South
            DFS(cell.getRow(), cell.getCol() - 1, cell, cells);
            // East
            DFS(cell.getRow() + 1, cell.getCol(), cell, cells);
            // North
            DFS(cell.getRow(), cell.getCol() + 1, cell, cells);
            cell = cells.pop();
        }

        return getSolution();
    }

    // Helper method that does the checking and adding or cells
    public void DFS(int row, int col, MazeCell cell, Stack cells){
        if (maze.isValidCell(row, col)){
            // If valid then add to stack to check
            cells.push(maze.getCell(row, col));
            maze.getCell(row, col).setExplored(true);
            // Switch parent so it can be traced later
            maze.getCell(row, col).setParent(cell);
        }
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Uses a queue instead of stack
        MazeCell cell = maze.getStartCell();
        Queue<MazeCell> cells = new LinkedList<MazeCell>();
        while (cell != maze.getEndCell()){
            // Checks North
            BFS(cell.getRow() + 1, cell.getCol(), cell, cells);
            // East
            BFS(cell.getRow(), cell.getCol() + 1, cell, cells);
            // South
            BFS(cell.getRow() - 1, cell.getCol(), cell, cells);
            // West
            BFS(cell.getRow(), cell.getCol() - 1, cell, cells);
            cell = cells.remove();
        }

        return getSolution();
    }

    // Helper method to check and add the cells
    public void BFS(int row, int col, MazeCell cell, Queue cells){
        if (maze.isValidCell(row, col)){
            // Add to queue to check later
            cells.offer(maze.getCell(row, col));
            maze.getCell(row, col).setExplored(true);
            // Switches parent cell to be tracked later
            maze.getCell(row, col).setParent(cell);
        }
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
