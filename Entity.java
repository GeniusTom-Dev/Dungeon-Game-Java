public class Entity {
    private int damage;
    private int shield;
    private int velocity;
    private int health;

    public Entity(int damage, int shield, int velocity, int health) {
        this.damage = damage;
        this.shield = shield;
        this.velocity = velocity;
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getHealth() {
        return health;
    }

    public void addHealth(int health) {
        if(this.health + health < 100){
            this.health += health;
        }else{
            this.health = 100;
        }
    }

    public void removeHealth(int health) {
        if(this.health - health < 0){
            this.health = 0;
        }else{
            this.health -= health;
        }
    }
}
