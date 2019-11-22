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
        
    }

    void initEmptyMaze(){
        int IDCounter = 1;
        for (int i=height-1; i>=0; i--){
            for (int j=0; j<width; j++){
                Tile tile = new Tile(IDCounter, " ", new TilePosition(j,i));
                IDCounter++;
                mazeList.add(tile);
            }
        }
    }

    void makeWallsAround(){
        for (Tile tile : mazeList){
            if (tile.getPosition().getX() == 0 || tile.getPosition().getY() == 0 || tile.getPosition().getX() == width-1 || tile.getPosition().getY() == height-1){
                tile.setContent("#");
            }
        }
    }

    Tile getTileAtPosition(TilePosition position){
        for (Tile tile:mazeList){
            if (tile.getPosition().equals(position)){
                return tile;
            }
        }
        return null;
    }

    public String getMazeAsString(){
        String mazeString = "";
        for (int i=height-1; i>=0; i--){
            for (int j=0; j<width; j++){
                Tile tile = getTileAtPosition(new TilePosition(j,i));
                mazeString += tile.getContent();
            }
            mazeString += "\n";
        }
        return mazeString;
    }
}
