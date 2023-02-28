import java.util.Arrays;

public class Game {
    private final Dungeon map;
    private final Flow flow = new Flow();
    private final LootGenerator lootGenerator = new LootGenerator();
    private final String[] classList = {"Assassin", "Tank"};
    private Player player;
    private boolean inGame;
    private String lastMove;

    public Game(int width, int height, int[][] wall, int[][] chest, int[][] monster,int[] boss,int[] itemMapPos, int playerX, int playerY, int artefact, int maxObjInventory, int maxArtInventory, int maxPotInventory) {
        this.map = new Dungeon(width, height, wall, chest, monster,boss, artefact, itemMapPos);
        this.player = new Player(playerX, playerY,0,0,0, 100,maxObjInventory, maxArtInventory, maxPotInventory);
        initGame();
    }

    public void initGame(){
        String chosenClass ="";
        this.flow.showBasicText("Bienvenue dans: Le Donjon des monstres", Flow.Color.YELLOW);
        this.flow.showList("Bienvenue, Voici les différentes classes :", classList);

        while(chosenClass.length() == 0){
            chosenClass = this.flow.inputText("Quelle classe voulez vous utiliser : ");
            chosenClass = chosenClass.substring(0, 1).toUpperCase() + chosenClass.substring(1);
            if(Arrays.asList(this.classList).contains(chosenClass)){
                initClass(chosenClass);
            }else{
                chosenClass = "";
            }
        }
    }

    public void initClass(String chosenClass){
        switch (chosenClass){
            case "Assassin":
                this.player = new Assassin(player.getX(), player.getY(),15,1,2, 100,this.player.maxObjectType.get("object"),this.player.maxObjectType.get("potion"),this.player.maxObjectType.get("artefact"));
                break;
            case "Tank":
                this.player = new Heavy(player.getX(), player.getY(),10,3,1, 100,this.player.maxObjectType.get("object"),this.player.maxObjectType.get("potion"),this.player.maxObjectType.get("artefact"));
                break;
        }
        this.game();

    }

    public void game(){
        this.inGame = true;
        while (this.inGame){
            switch (this.flow.inputText("Quelle action voulez faire (help pour voir les commandes disponibles):")){
                case "help":
                    this.flow.help();
                    break;
                case "z":
                    if(this.checkMove(this.player.getX(), this.player.getY() - 1)){
                        this.lastMove = "top";
                        this.actionNextChar(this.player.getX(), this.player.getY() - 1);
                        this.player.moveTop();
                    }else{
                        this.flow.showBasicText("Aie il y a un mur, vous ne pouvez pas aller par là", Flow.Color.RED);
                    }
                    break;
                case "q":
                    if(this.checkMove(this.player.getX() - 1, this.player.getY())){
                        this.lastMove = "left";
                        this.actionNextChar(this.player.getX() - 1, this.player.getY());
                        this.player.moveLeft();
                    }else{
                        this.flow.showBasicText("Aie il y a un mur, vous ne pouvez pas aller par là", Flow.Color.RED);
                    }
                    break;
                case "s":
                    if(this.checkMove(this.player.getX(), this.player.getY() + 1)){
                        this.lastMove = "bottom";
                        this.actionNextChar(this.player.getX(), this.player.getY() + 1);
                        this.player.moveBottom();
                    }else{
                        this.flow.showBasicText("Aie il y a un mur, vous ne pouvez pas aller par là", Flow.Color.RED);
                    }
                    break;

                case "d":
                    if(this.checkMove(this.player.getX() + 1, this.player.getY())){
                        this.lastMove = "right";
                        this.actionNextChar(this.player.getX() + 1, this.player.getY());
                        this.player.moveRight();
                    }else{
                        this.flow.showBasicText("Aie il y a un mur, vous ne pouvez pas aller par là", Flow.Color.RED);
                    }
                    break;

                case "map":
                    if(this.player.haveMap()){
                        this.map.showMap(this.player.getX(), this.player.getY());
                        break;
                    }
                    this.flow.showBasicText("Vous n'avez pas de carte", Flow.Color.RED);
                    break;
                case "e":
                    switch (this.map.getCharInIndex(this.player.getX(), this.player.getY())){
                        case 'C' -> openChest();
                        case 'D' -> getMap();
                        case 'A' -> newArtefact();
                    }
                    break;
                case "i":
                    this.flow.showInventory(this.player);
                    break;
            }


        }
    }


    public boolean checkMove(int x, int y){
        if(x >= 0
                && x < this.map.getWidth()
                && y >= 0
                && y < this.map.getHeight()
                && this.map.getCharInIndex(x,y) != 'W'){
            return true;
        }
        return false;
    }

    public void actionNextChar(int x, int y){
        char newPosChar = this.map.getCharInIndex(x,y);
        switch (newPosChar) {
            case 'W':
                this.flow.showBasicText("Aie il y a un mur, vous ne pouvez pas aller par là", Flow.Color.RED);
                break;
            case 'C':
                this.flow.showBasicText("Il y a un coffre ! tu peux l'ouvrir avec la touche E",Flow.Color.RED);
                break;
            case 'A':
                this.flow.showBasicText("Il y a un artefact ! tu peux le prendre avec la touche E",Flow.Color.RED);
                break;
            case 'M':
                new Fight(this.player, new Monster(15, 1, 1, 30), this.flow, false);
                removeChar(this.lastMove);
                if(this.player.getHealth() <= 0){
                    this.flow.showBasicText("Vous être mort, le sort ne vous à pas été favorable..", Flow.Color.RED);
                    this.inGame = false;
                }
                break;
            case 'B':
                new Fight(this.player, new Monster(20, 1, 1, 50), this.flow, true);
                removeChar(this.lastMove);
                if(this.player.getHealth() >= 0){
                    this.flow.showBasicText("Félicitation vous avez finit le dongeon, le sort vous à été favorable !", Flow.Color.RED);
                    this.inGame = false;
                }
                break;
            case 'D':
                this.flow.showBasicText("Tu viens de trouver Une carte du Maraudeur ! tu peux le prendre avec la touche E",Flow.Color.RED);
                break;
            default:
                this.flow.showBasicText("Vous avancez... , rien à l'horizon !", Flow.Color.GREEN);
                break;
        }
    }

    public void openChest(){
        Item[] chestContent = this.lootGenerator.newChestContent();
        this.flow.showChestContent(chestContent);
        for(Item item: chestContent){
            if(this.player.haveItem(item)){
                System.out.printf("Malheuresement vous avez déjà %s\n", item.getName());
            }else{
                if(this.player.getNbElementByType(item.getType()) >= this.player.maxObjectType.get(item.getType())){
                    boolean doAction = false;
                    while(!doAction){
                        String action = this.flow.inputText("Votre Inventaire est plein, voulez vous le remplacer (r) par un autre ou le jeter (a) ou ne rien faire (back) ?");
                        if(action.equals("a")){
                            System.out.println("Vous venez de jeter " + item.getName());
                            doAction = true;
                        }else if(action.equals("back")) {
                            doAction = true;
                        }else if(action.equals("r")) {
                            String[] inv = this.player.getElementByType(item.getType());
                            System.out.println("Voici vos éléments:");
                            for (int i = 0; i < inv.length; i++) {
                                System.out.printf("%d. %s\n", i, inv[i]);
                            }
                            boolean deletedItem = false;
                            while (!deletedItem){
                                String value = this.flow.inputText("Quel élément voulez vous supprimer ?");
                                if(Integer.parseInt(value) <= inv.length - 1){
                                    this.player.removeInventoryItemByName(inv[Integer.parseInt(value)]);
                                    this.flow.itemRemove(item.getName());
                                    this.player.addInventory(item);
                                    this.flow.itemAdd(item.getName());
                                    deletedItem = true;
                                    doAction = true;
                                }
                            }
                        }
                    }
                }else{
                    this.player.addInventory(item);
                    this.flow.itemAdd(item.getName());
                }

            }
        }

        this.map.removeCharInIndex(this.player.getX(), this.player.getY());

    }

    public void newArtefact(){
        Item artefact = this.lootGenerator.newArtefact();
        if(!this.player.haveItem(artefact)){
            this.flow.itemAdd(artefact.getName());
            this.player.addInventory(artefact);
        }else{
            this.flow.showBasicText("Malheuresement vous avez déjà " + artefact.getName(), Flow.Color.RED);
            System.out.println();
        }

        this.map.removeCharInIndex(this.player.getX(), this.player.getY());
    }

    public void getMap(){
        Item map = new Item("Une carte du Maraudeur","object");
        this.player.addInventory(map);
        this.flow.itemAdd(map.getName());
        this.map.removeCharInIndex(this.player.getX(), this.player.getY());
    }

    public void removeChar(String dir){
        switch (dir){
            case "top":
                this.map.removeCharInIndex(this.player.getX(), this.player.getY() - 1);
                break;
            case "bottom":
                this.map.removeCharInIndex(this.player.getX(), this.player.getY() + 1);
                break;
            case "left":
                this.map.removeCharInIndex(this.player.getX() - 1, this.player.getY());
                break;
            case "right":
                this.map.removeCharInIndex(this.player.getX() + 1, this.player.getY());
                break;
        }
    }

}
