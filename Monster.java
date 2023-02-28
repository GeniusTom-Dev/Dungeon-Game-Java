import java.util.Random;

public class Monster extends Entity{
    private String name;
    public static final String[] names = {"Bobby", "Bubba", "Cletus", "Dinky", "Doodle", "Fido", "Fifi", "Fluffy", "Gizmo", "Hobo"};
    Random rand = new Random();

    public Monster(int damage, int shield, int velocity, int health) {
        super(damage, shield, velocity,health);
        this.name = names[rand.nextInt(names.length - 1)];
    }

    public String getName() {
        return name;
    }
}
