public class Program {
    public static void main(String[] args) {
        Maze maze = new Maze(12,17);

        String output = maze.getMazeAsString();
        System.out.println(output);

    }
}
