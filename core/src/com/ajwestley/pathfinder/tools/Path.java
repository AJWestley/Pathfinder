package com.ajwestley.pathfinder.tools;

import java.util.ArrayList;

public class Path {

    PathGrid grid;
    public int xAt, yAt;
    public ArrayList<Integer[]> pathSquares;

    public Path (PathGrid grid, int xAt, int yAt) {

        this.xAt = xAt;
        this.yAt = yAt;
        this.grid = grid;
        pathSquares = new ArrayList<Integer[]>();
        if (this.grid.grid[xAt][yAt] != PathGrid.START_POINT) {
            this.grid.addActive(xAt, yAt);
        }

    }

    public ArrayList<Integer[]> availableMoves() {

        ArrayList<Integer[]> coOrds = new ArrayList<Integer[]>();

        int right = xAt + 1;
        if (right < grid.width) {
            if (grid.grid[right][yAt] == PathGrid.EMPTY || grid.grid[right][yAt] == PathGrid.DESTINATION_POINT) {
                Integer[] co = new Integer[2];
                co[0] = right;
                co[1] = yAt;
                coOrds.add(co);
            }
        }

        int left = xAt - 1;
        if (left >= 0) {
            if (grid.grid[left][yAt] == PathGrid.EMPTY || grid.grid[left][yAt] == PathGrid.DESTINATION_POINT) {
                Integer[] co = new Integer[2];
                co[0] = left;
                co[1] = yAt;
                coOrds.add(co);
            }
        }

        int down = yAt - 1;
        if (down >= 0) {
            if (grid.grid[xAt][down] == PathGrid.EMPTY || grid.grid[xAt][down] == PathGrid.DESTINATION_POINT) {
                Integer[] co = new Integer[2];
                co[0] = xAt;
                co[1] = down;
                coOrds.add(co);
            }
        }

        int up = yAt + 1;
        if (up < grid.height) {
            if (grid.grid[xAt][up] == PathGrid.EMPTY || grid.grid[xAt][up] == PathGrid.DESTINATION_POINT) {
                Integer[] co = new Integer[2];
                co[0] = xAt;
                co[1] = up;
                coOrds.add(co);
            }
        }

        return coOrds;

    }

    public boolean canMove() {

        if (availableMoves().isEmpty()) return false;
        return true;

    }

    public Path step(int x, int y) {

        if (grid.grid[xAt][yAt] != PathGrid.START_POINT) grid.addVisited(xAt, yAt);

        if (canMove()) {

            Integer[] at = new Integer[2];
            at[0] = xAt;
            at[1] = yAt;

            pathSquares.add(at);

            Path next = new Path(grid, x, y);
            next.pathSquares.addAll(pathSquares);

            return next;

        }

        return null;

    }

    public void showPath() {

        grid.complete = true;

        for (Integer[] cell : pathSquares) {
            grid.addPath(cell[0], cell[1]);
        }

    }

}
