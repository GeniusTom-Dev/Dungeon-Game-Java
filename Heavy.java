public class Heavy extends Player{
    private String capacity = "Coup de masse";

    public Heavy(int x, int y,int damage, int shield, int velocity,int health ,int maxObjInventory, int maxArtInventory, int maxPotInventory) {
        super(x, y,damage, shield, velocity, health,maxObjInventory, maxArtInventory, maxPotInventory);
    }

    @Override
    public String getCapacity() {
        return capacity;
    }
}
