import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Program {
    public static void main(String[] args) {
        Maze maze = new Maze(8,20);

        String output = maze.getMazeAsString();
        System.out.println(output);
        makeMazeAsTxt(output);
    }

    public static void makeMazeAsTxt(String maze){
        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter("test.txt")));
            pWriter.println(maze);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (pWriter != null){
                pWriter.flush();
                pWriter.close();
            }
        }
    }
}
