package com.ajwestley.pathfinder.tools;

public class PathGrid {

    public static final char EMPTY = '0';
    public static final char OBSTACLE = 'w';
    public static final char VISITED = 'v';
    public static final char START_POINT = 's';
    public static final char DESTINATION_POINT = 'd';

    boolean hasStart;
    boolean hasDestination;

    public char[][] grid;

    public PathGrid(int width, int height) {

        grid = new char[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = EMPTY;
            }
        }

        hasStart = false;
        hasDestination = false;

    }

    public void edit (char item, int x, int y) {
        grid[x][y] = item;
    }

    public boolean isValidGrid () {
        return hasStart && hasDestination;
    }
}
