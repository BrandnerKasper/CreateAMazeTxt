import java.util.ArrayList;
import java.util.List;

public class Maze {

    private int height;
    private int width;

    public List<Tile> mazeList = new ArrayList<>(height * width);


    public Maze(int height, int width) {
        this.height = height;
        this.width = width;

        initEmptyMaze();
        makeWallsAround();
        randomlyChooseWalls();
        fillAlgorithm();
    }

    void initEmptyMaze() {
        int IDCounter = 1;
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile(IDCounter, ".", new TilePosition(j, i));
                IDCounter++;
                mazeList.add(tile);
            }
        }
    }

    void makeWallsAround() {
        for (Tile tile : mazeList) {
            if (tile.getPosition().getX() == 0 || tile.getPosition().getY() == 0 || tile.getPosition().getX() == width - 1 || tile.getPosition().getY() == height - 1) {
                tile.setContent("#");
            }
        }
    }

    void randomlyChooseWalls() {
        randomlyFillEverySecondRow();
        fillInTheRowsBetween();
    }

    int rowCounter;

    void randomlyFillEverySecondRow() {
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

    //looks in every second row and fills up all tiles where height+1/-1 is a wall
    void fillInTheRowsBetween() {
        for (int i = rowCounter + 1; i < height - 1; i += 2) {
            for (int j = 2; j <= width - 1; j++) {
                TilePosition tilePosition = new TilePosition(j, i);
                if (checkIfUpAndDownIsWall(tilePosition)) {
                    getTileAtPosition(tilePosition).setContent("#");
                }
            }
        }
    }

    boolean checkIfUpAndDownIsWall(TilePosition position) {
        Tile tileOneUP = getTileAtPosition(new TilePosition(position.getX(), position.getY() + 1));
        Tile tileOneDown = getTileAtPosition(new TilePosition(position.getX(), position.getY() - 1));
        if (tileOneUP.getContent().equals("#") && tileOneDown.getContent().equals("#")) {
            return true;
        }
        return false;
    }

    void fillAlgorithm() {
        fixPlaza();

    }

    // A plaza is a Tile with no walls around
    void fixPlaza() {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 2; j < width - 1; j++) {
                TilePosition tilePosition = new TilePosition(j, i);
                if (isThereAPlaza(tilePosition)){
                    getTileAtPosition(tilePosition).setContent("#");
                }
            }
        }
    }

    boolean isThereAPlaza(TilePosition position) {
        int b = position.getX() + 1;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!isTileEmpty(new TilePosition(position.getX() + i, position.getY() + j))) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean isTileEmpty(TilePosition position) {
        if (getTileAtPosition(position).getContent().equals(".")) {
            return true;
        }
        return false;
    }

    Tile getTileAtPosition(TilePosition position) {
        for (Tile tile : mazeList) {
            if (tile.getPosition().equals(position)) {
                return tile;
            }
        }
        return null;
    }

    public String getMazeAsString() {
        String mazeString = "";
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                Tile tile = getTileAtPosition(new TilePosition(j, i));
                mazeString += tile.getContent();
            }
            mazeString += "\n";
        }
        return mazeString;
    }
}
