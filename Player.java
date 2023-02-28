import java.util.*;

public class Player extends Entity{
    private int x;
    private int y;
    public  Map<String, Integer> maxObjectType = new HashMap<String, Integer>();
    private ArrayList<Item> inventory = new ArrayList<>();
    private int fightXP = 0;
    private int playerLevel = 1;
    private String capacity;


    public Player(int x, int y, int damage, int shield, int velocity, int health , int maxObjInventory, int maxArtInventory, int maxPotInventory) {
        super(damage, shield, velocity,health);
        this.x = x;
        this.y = y;
        maxObjectType.put("object", maxObjInventory);
        maxObjectType.put("potion", maxPotInventory);
        maxObjectType.put("artefact", maxArtInventory);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveRight(){
        this.x += 1;
    }

    public void moveLeft(){
        this.x -= 1;
    }

    public void moveTop(){
        this.y -= 1;
    }

    public void moveBottom(){
        this.y += 1;
    }

    public int getLevel() {
        return playerLevel;
    }

    public void addFightXP(int fightXP) {
        if (this.fightXP + fightXP >= 100){
            playerLevel++;
            new Flow().showBasicText("Vous passez level " + playerLevel, Flow.Color.BLUE);
            this.fightXP = 0;
        }else{
            this.fightXP += fightXP;
        }
    }

    public String getCapacity(){
        return capacity;
    };

    public int getNbElementByType(String type){
        int nbElement = 0;
        for(Item item: inventory){
            if(Objects.equals(item.getType(), type)) nbElement++;
        }
        return nbElement;
    }

    public boolean haveItem(Item item){
        for(Item existItem: inventory){
            if(Objects.equals(existItem.getName(), item.getName())) return true;
        }
        return false;
    }

    public boolean haveItemByName(String name){
        for(Item item: inventory){
            if(Objects.equals(item.getName(), name)) return true;
        }
        return false;
    }

    public void addInventory(Item item){
        inventory.add(item);
    }

    public void removeInventoryItemByName(String name){
        for(Item item: inventory){
            if(Objects.equals(item.getName(), name)){
                inventory.remove(item);
            }
        }
    }

    public String[] getElementByType(String type){
        String[] itemName = new String[this.getNbElementByType(type)];
        int listIndex = 0;

        for(Item item: inventory){
            if(Objects.equals(item.getType(), type)){
                itemName[listIndex] = item.getName();
                listIndex++;
            }
        }
        return itemName;
    }

    public boolean haveMap(){
        for(Item item: inventory){
            if(Objects.equals(item.getName(), "Une carte du Maraudeur")) return true;
        }
        return false;
    }




}
