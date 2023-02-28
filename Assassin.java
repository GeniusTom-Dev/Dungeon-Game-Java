public class Assassin extends Player{
    private String capacity = "Lancer de kunaï";

    public Assassin(int x, int y,int damage, int shield, int velocity,int health ,int maxObjInventory, int maxArtInventory, int maxPotInventory) {
        super(x, y,damage, shield, velocity, health,maxObjInventory, maxArtInventory, maxPotInventory);
    }

    @Override
    public String getCapacity() {
        return capacity;
    }
}
