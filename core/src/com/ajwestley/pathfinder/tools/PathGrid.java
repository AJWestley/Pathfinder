package com.ajwestley.pathfinder.tools;

import java.util.ArrayList;

public class PathGrid {

    public static final char EMPTY = 'o';
    public static final char OBSTACLE = 'w';
    public static final char VISITED = 'v';
    public static final char START_POINT = 's';
    public static final char DESTINATION_POINT = 'd';
    public static final char ACTIVE = 'a';
    public static final char PATH = 'p';

    boolean hasStart;
    boolean hasDestination;
    public boolean complete;

    public ArrayList<Path> paths;

    public char[][] grid;
    public int width;
    public int height;

    public PathGrid(int width, int height) {

        grid = new char[width][height];
        paths = new ArrayList<Path>();
        this.width = width;
        this.height = height;
        hasStart = false;
        hasDestination = false;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = EMPTY;
            }
        }

        complete = false;

    }

    public void addActive (int x, int y) {if (grid[x][y] != START_POINT && grid[x][y] != DESTINATION_POINT) grid[x][y] = ACTIVE;}

    public void addVisited (int x, int y) {if (grid[x][y] != START_POINT && grid[x][y] != DESTINATION_POINT) grid[x][y] = VISITED;}

    public void addPath (int x, int y) {if (grid[x][y] != START_POINT && grid[x][y] != DESTINATION_POINT) grid[x][y] = PATH;}

    public void addObstacle (int x, int y) {
        if (grid[x][y] == START_POINT) hasStart = false;
        if (grid[x][y] == DESTINATION_POINT) hasDestination = false;
        grid[x][y] = OBSTACLE;
    }

    public void erase (int x, int y) {
        if (grid[x][y] == START_POINT) hasStart = false;
        if (grid[x][y] == DESTINATION_POINT) hasDestination = false;
        grid[x][y] = EMPTY;
    }

    public void addStart (int x, int y) {
        if (grid[x][y] == DESTINATION_POINT) hasDestination = false;
        if (!hasStart) {
            grid[x][y] = START_POINT;
            hasStart = true;
        }
    }

    public void addDestination (int x, int y) {
        if (grid[x][y] == START_POINT) hasStart = false;
        if (!hasDestination) {
            grid[x][y] = DESTINATION_POINT;
            hasDestination = true;
        }
    }

    public boolean isValidGrid () {
        return hasStart && hasDestination;
    }

    public void begin() {

        int xBegin = 0, yBegin = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                if (grid[i][j] == START_POINT) {
                    xBegin = i;
                    yBegin = j;
                }

            }
        }

        paths.clear();
        paths.add(new Path(this, xBegin, yBegin));

    }

    public void progress() {

        if (!complete) {
            ArrayList<Path> toBeAdded = new ArrayList<Path>();
            ArrayList<Path> toBeRemoved = new ArrayList<Path>();

            for (Path path : paths) {

                if (!path.canMove()) {
                    toBeRemoved.add(path);
                    addVisited(path.xAt, path.yAt);
                } else {
                    for (Integer[] i : path.availableMoves()) {

                        Path temp = path.step(i[0], i[1]);
                        if (temp.grid.grid[temp.xAt][temp.yAt] == DESTINATION_POINT) {

                            complete = true;
                            toBeRemoved.addAll(paths);

                            for (Integer[] cell : path.pathSquares) {

                                addPath(cell[0], cell[1]);

                            }

                            for (int j = 0; j < width; j++) {
                                for (int k = 0; k < height; k++) {

                                    if (grid[j][k] == ACTIVE || grid[j][k] == VISITED) erase(j, k);

                                }
                            }

                        }

                        if (complete) break;
                        toBeAdded.add(temp);
                    }
                }
                if (complete) break;
            }

            paths.removeAll(toBeRemoved);
            paths.addAll(toBeAdded);

        }

    }
}
