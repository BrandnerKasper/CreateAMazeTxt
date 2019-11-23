import java.util.ArrayList;
import java.util.List;

public class Maze {

    private int height;
    private int width;

    private int rowCounter;

    private List<Tile> mazeList = new ArrayList<>(height * width);


    public Maze(int height, int width) {
        this.height = height;
        this.width = width;

        initEmptyMaze();
        makeWallsAround();
        randomlyChooseWalls();
        fillAlgorithm();
    }

    /**
     * initialize a maze(height*width) with empty tiles(".")
     * <pre>
     * ....................
     * ....................
     * ....................
     * ....................
     * ....................
     * ....................
     * ....................
     * ....................
     * ....................
     * ....................
     * ....................
     * ....................
     * </pre>
     */
    private void initEmptyMaze() {
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile(".", new TilePosition(j, i));
                mazeList.add(tile);
            }
        }
    }

    /**
     * creates walls("#") at the edge of the maze
     * <pre>
     * ####################
     * #..................#
     * #..................#
     * #..................#
     * #..................#
     * #..................#
     * #..................#
     * #..................#
     * #..................#
     * #..................#
     * #..................#
     * ####################
     * </pre>
     */
    private void makeWallsAround() {
        for (Tile tile : mazeList) {
            if (tile.getPosition().getX() == 0 || tile.getPosition().getY() == 0 || tile.getPosition().getX() == width - 1 || tile.getPosition().getY() == height - 1) {
                tile.setContent("#");
            }
        }
    }

    private void randomlyChooseWalls() {
        randomlyFillEverySecondRow();
        fillInTheRowsBetween();
    }

    /**
     * Sets walls to 50% every Second row
     * <pre>
     * ####################
     * #...##..#..###.##..#
     * #..................#
     * #.#.#..#..##.###...#
     * #..................#
     * #....#.##.#####.####
     * #..................#
     * #....#...###.##.####
     * #..................#
     * #..##...##.#.#.....#
     * #..................#
     * ####################
     * </pre>
     */
    private void randomlyFillEverySecondRow() {
        int random = (Math.random() <= 0.5) ? 1 : 2;
        rowCounter = random;
        for (int i = rowCounter; i < height - 1; i += 2) {
            for (int j = 2; j < width - 1; j++) {
                random = (int) (Math.random() * 10 + 1);
                if (random <= 5) {
                    getTileAtPosition(new TilePosition(j, i)).setContent("#");
                }
            }
        }
    }

    /**
     * looks in every second row and fills up all tiles where height+1/-1 is a wall
     * <pre>
     * ####################
     * #...##..#..###.##..#
     * #...#......#.#.#...#
     * #.#.#..#..##.###...#
     * #......#..##.##....#
     * #....#.##.#####.####
     * #....#....##.##.####
     * #....#...###.##.####
     * #........#.#.#.....#
     * #..##...##.#.#.....#
     * #..................#
     * ####################
     * </pre>
     */
    private void fillInTheRowsBetween() {
        for (int i = rowCounter + 1; i < height - 1; i += 2) {
            for (int j = 2; j <= width - 1; j++) {
                TilePosition tilePosition = new TilePosition(j, i);
                if (isTileUpAndDownWall(tilePosition)) {
                    getTileAtPosition(tilePosition).setContent("#");
                }
            }
        }
    }

    private boolean isTileUpAndDownWall(TilePosition position) {
        Tile tileOneUP = getTileAtPosition(new TilePosition(position.getX(), position.getY() + 1));
        Tile tileOneDown = getTileAtPosition(new TilePosition(position.getX(), position.getY() - 1));
        return tileOneUP.getContent().equals("#") && tileOneDown.getContent().equals("#");
    }

    /**
     * This function filles the maze so that there are no 2x2 fields of Tiles with no walls -> no turnaround are possible
     */
    private void fillAlgorithm() {
        fixBigPlaza();
        randomlyFixSmallPlaza();
    }

    /**
     * A big plaza is 3x3 field of Tiles with no walls, like this:
     * <pre>
     * #####
     * #...#
     * #...#
     * #...#
     * #####
     * </pre>
     * and gets fixed like this:
     * <pre>
     * #####
     * #...#
     * #.#.#
     * #...#
     * #####
     * </pre>
     */
    private void fixBigPlaza() {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 2; j < width - 1; j++) {
                TilePosition position = new TilePosition(j, i);
                if (isThereABigPlaza(position)){
                    getTileAtPosition(position).setContent("#");
                }
            }
        }
    }

    private boolean isThereABigPlaza(TilePosition position) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!isTileEmpty(new TilePosition(position.getX() + i, position.getY() + j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * A small plaza is a 2x2 field of Tiles with no walls like this:
     * <pre>
     * ####
     * #..#
     * #..#
     * ####
     * </pre>
     * Small plazas can be filled in 4 different ways (top left, top right, down left, down right) and can look like this:
     * <pre>
     * ####
     * #.##
     * #..#
     * ####
     * </pre>
     * -> we use this to further randomize how our mazes get filled.
     */
    private void randomlyFixSmallPlaza(){
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                TilePosition position = new TilePosition(j, i);
                if (isThereASmallPlaza(position)){
                    int randomYPosition = (Math.random() <= 0.5) ? 0 : 1;
                    int randomXPosition = (Math.random() <= 0.5) ? 0 : 1;
                    getTileAtPosition(new TilePosition(j+randomXPosition,i+randomYPosition)).setContent("#");
                }
            }
        }
    }
    
    private boolean isThereASmallPlaza(TilePosition position){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (!isTileEmpty(new TilePosition(position.getX() + i, position.getY() + j))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isTileEmpty(TilePosition position) {
        return getTileAtPosition(position).getContent().equals(".");
    }

    private Tile getTileAtPosition(TilePosition position) {
        for (Tile tile : mazeList) {
            if (tile.getPosition().equals(position)) {
                return tile;
            }
        }
        throw new IllegalArgumentException("invalid position: " + position.toString());
    }

    public String getMazeAsString() {
        StringBuilder mazeString = new StringBuilder();
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                Tile tile = getTileAtPosition(new TilePosition(j, i));
                mazeString.append(tile.getContent());
            }
            mazeString.append("\n");
        }
        return mazeString.toString();
    }
}
