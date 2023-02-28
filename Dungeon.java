import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Dungeon {
    private char[][] plateGame;
    private int[][] emptyChar;
    private int emptyCharCpt = 0;
    private int artefactCpt = 0;
    private int nbArtefact;
    private int width;
    private int height;
    private Flow flow = new Flow();

    public Dungeon(int width, int height, int[][] wall, int[][] chest, int[][] monster,int[] boss, int artefact, int[] itemMapPos){
        this.nbArtefact = artefact;
        generateMap(width, height, wall, chest,boss, monster, itemMapPos);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void generateMap(int width, int height, int[][] wall, int[][] chest,int[] boss, int[][] monster, int[] itemMapPos){
        this.plateGame = new char[height][];
        for (int indexChar = 0; indexChar < this.plateGame.length; indexChar++) {
            this.plateGame[indexChar] = new char[width];
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.plateGame[y][x] = ' ';
            }
        }

        for (int indexWall = 0; indexWall < wall.length; indexWall++) {
            this.plateGame[wall[indexWall][0]][wall[indexWall][1]] = 'W';
        }

        for (int indexChest = 0; indexChest < chest.length; indexChest++) {
            this.plateGame[chest[indexChest][0]][chest[indexChest][1]] = 'C';
        }

        for (int indexMonster = 0; indexMonster < monster.length; indexMonster++) {
            this.plateGame[monster[indexMonster][0]][monster[indexMonster][1]] = 'M';
        }

        this.plateGame[boss[0]][boss[1]] = 'B';
        this.plateGame[itemMapPos[0]][itemMapPos[1]] = 'D';

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(this.plateGame[y][x] == ' '){
                    this.emptyCharCpt += 1;
                }
            }
        }

        this.emptyChar = new int[this.emptyCharCpt][];
        this.emptyCharCpt = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(this.plateGame[y][x] == ' '){
                    this.emptyChar[this.emptyCharCpt] = new int[]{y, x};
                    this.emptyCharCpt++;
                }
            }
        }
        Random random = new Random();

        while(this.artefactCpt < this.nbArtefact ){
            int randomValue = random.nextInt(this.emptyChar.length - 1);
            if(this.plateGame[this.emptyChar[randomValue][0]][this.emptyChar[randomValue][1]] == ' '){
                this.plateGame[this.emptyChar[randomValue][0]][this.emptyChar[randomValue][1]] = 'A';
                this.artefactCpt++;
            }
        }

    }

    public void showMap(int playerX, int playerY){
        char[][] gamePlate = this.plateGame;
        char beforeChar = gamePlate[playerY][playerX];
        gamePlate[playerY][playerX] = 'P';
        this.flow.setColor(Flow.Color.YELLOW);
        for (int y = 0; y < gamePlate.length; y++) {
            for (int x = 0; x < gamePlate[y].length; x++) {
                if(gamePlate[y][x] == 'W'){
                    System.out.print(" □\t");
                }else{
                    System.out.print(" " + gamePlate[y][x] + "\t");
                }
            }
            System.out.println();
        }
        this.flow.setColor(Flow.Color.RESET);
        gamePlate[playerY][playerX] = beforeChar;
    }

    public char getCharInIndex(int x, int y){
        return this.plateGame[y][x];
    }

    public void removeCharInIndex(int x, int y){
        this.plateGame[y][x] = ' ';
    }

}
