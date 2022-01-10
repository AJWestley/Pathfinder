package com.ajwestley.pathfinder.tools;

public class PathGrid {

    public static final char EMPTY = '0';
    public static final char OBSTACLE = 'w';
    public static final char VISITED = 'v';
    public static final char START_POINT = 's';
    public static final char DESTINATION_POINT = 'd';
    public static final char ACTIVE = 'a';

    boolean hasStart;
    boolean hasDestination;

    public char[][] grid;
    public int width;
    public int height;

    public PathGrid(int width, int height) {

        grid = new char[width][height];
        this.width = width;
        this.height = height;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = EMPTY;
            }
        }

        hasStart = false;
        hasDestination = false;

    }

    public void addActive (int x, int y) {
        if (grid[x][y] == START_POINT) hasStart = false;
        grid[x][y] = ACTIVE;
    }

    public void addVisited (int x, int y) {
        if (grid[x][y] == START_POINT) hasStart = false;
        grid[x][y] = VISITED;
    }

    public void addObstacle (int x, int y) {
        if (grid[x][y] == START_POINT) hasStart = false;
        grid[x][y] = OBSTACLE;
    }

    public void erase (int x, int y) {
        if (grid[x][y] == START_POINT) hasStart = false;
        grid[x][y] = EMPTY;
    }

    public boolean addStart (int x, int y) {
        if (!hasStart) {
            grid[x][y] = START_POINT;
            hasStart = true;
        }
        else return false;

        return true;
    }

    public boolean addDestination (int x, int y) {
        if (!hasDestination) {
            grid[x][y] = DESTINATION_POINT;
            hasDestination = true;
        }
        else return false;

        return true;
    }

    public boolean isValidGrid () {
        return hasStart && hasDestination;
    }
}
