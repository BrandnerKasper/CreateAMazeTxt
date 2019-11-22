import java.util.Objects;

public class Tile {

    private int ID;
    private String content;
    private TilePosition position;

    public Tile(int ID, String content, TilePosition position) {
        this.ID = ID;
        this.content = content;
        this.position = position;
    }

    public String getContent(){return content;}

    public void setContent(String content) {this.content = content;}

    public TilePosition getPosition(){return position;}

}

class TilePosition{

    private int x;
    private int y;

    public TilePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //We override the default equals Method, so the position of two TilePosition will be compared not the instance!
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TilePosition)) return false;
        TilePosition that = (TilePosition) o;
        return x == that.x &&
                y == that.y;
    }
}