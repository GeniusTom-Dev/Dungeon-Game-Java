public class Main {
    public static void main(String[] args) {
        int width = 5;
        int height= 5;
        int[][] wall = {
                {0,2},{0,3},{0,4},
                {1,4},
                {2,0},{2,1},{2,3},{2,4},
                {3,0},{3,1},
                {4,0},{4,1},{4,3},{4,4}
        };
        int[][] chest = {
                {1,3},{3,4},
        };
        int[][] monster = {{1, 1}, {3,3}};
        int[] boss = {0,0};
        int[] itemMapPos = {3,2};
        int playerX = 2;
        int playerY = 4;
        int nbArtefact = 2;
        int maxObjInventory = 2;
        int maxArtInventory = 3;
        int maxPotInventory = 5;

        new Game(width, height, wall, chest, monster,boss,itemMapPos, playerX, playerY, nbArtefact, maxObjInventory, maxArtInventory, maxPotInventory);

    }
}