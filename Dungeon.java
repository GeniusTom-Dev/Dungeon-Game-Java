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
        for (int i = 0; i < this.plateGame.length; i++) {
            this.plateGame[i] = new char[width];
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.plateGame[i][j] = ' ';
            }
        }

        for (int k = 0; k < wall.length; k++) {
            this.plateGame[wall[k][0]][wall[k][1]] = 'W';
        }

        for (int k = 0; k < chest.length; k++) {
            this.plateGame[chest[k][0]][chest[k][1]] = 'C';
        }

        for (int k = 0; k < monster.length; k++) {
            this.plateGame[monster[k][0]][monster[k][1]] = 'M';
        }

        this.plateGame[boss[0]][boss[1]] = 'B';
        this.plateGame[itemMapPos[0]][itemMapPos[1]] = 'D';

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(this.plateGame[i][j] == ' '){
                    this.emptyCharCpt += 1;
                }
            }
        }

        this.emptyChar = new int[this.emptyCharCpt][];
        this.emptyCharCpt = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(this.plateGame[i][j] == ' '){
                    this.emptyChar[this.emptyCharCpt] = new int[]{i, j};
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

    public void showMap(int x, int y){
        char[][] gamePlate = this.plateGame;
        char beforeChar = gamePlate[y][x];
        gamePlate[y][x] = 'P';
        this.flow.setColor(Flow.Color.YELLOW);
        for (int i = 0; i < gamePlate.length; i++) {
            for (int j = 0; j < gamePlate[i].length; j++) {
                if(gamePlate[i][j] == 'W'){
                    System.out.print(" □\t");
                }else{
                    System.out.print(" " + gamePlate[i][j] + "\t");
                }
            }
            System.out.println();
        }
        this.flow.setColor(Flow.Color.RESET);
        gamePlate[y][x] = beforeChar;
    }

    public char getCharInIndex(int x, int y){
        return this.plateGame[y][x];
    }

    public void removeCharInIndex(int x, int y){
        this.plateGame[y][x] = ' ';
    }

}
