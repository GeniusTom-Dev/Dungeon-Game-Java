import java.util.Objects;
import java.util.Random;

public class Fight {
    private boolean inFight;
    private boolean isBoss;
    private Monster monster;
    private Player player;
    private boolean playerPlay = true;
    private boolean playerTurn = true;
    private boolean userUseCapacity = false;
    private final Flow flow;
    private final Random rand = new Random();
    private boolean activeDamagePotion = false;
    private final String[] actionCommand = {
            "\tf\t-\tFrapper son ennemy",
            "\tp\t-\tUtiliser une potion",
            "\tu\t-\tUtiliser un item",
            "\tr\t-\tUtiliser sa capacité",
    };


    public Fight(Player player, Monster monster, Flow flow, boolean isBoss) {
        this.monster = monster;
        this.player = player;
        this.isBoss = isBoss;
        this.flow = flow;
        startFight();
    }

    public void startFight(){
        this.inFight = true;
        this.flow.showBasicText(String.format("Vous entrez en combat avec %s", this.monster.getName()), Flow.Color.RED);
        while (inFight){
            showInstructions();
            playerPlay = true;
            playerTurn = true;
            while (playerTurn){
                String action = this.flow.inputText("Quelle action voulez vous faire ?");
                switch (action){
                    case "f":
                        hitEnemy(false);
                        playerTurn = false;
                        break;
                    case "p":
                        usePotion();
                        playerTurn = false;
                        break;
                    case "u":
                        useItem();
                        playerTurn = false;
                        break;
                    case "r":
                        useCapacity();
                        break;
                }
            }


            if(!this.entityAlive()){
                inFight = false;
                return;
            };

            // Monster Attak:
            boolean dodge = hasDodge("player");
            if(!dodge){
                int damage = rand.nextInt(this.monster.getDamage() - 2, this.monster.getDamage() + 2) / this.player.getShield();
                if(this.player.haveItemByName("Un collier de résistance")) damage -= damage * 0.2;

                this.player.removeHealth(damage);
                this.flow.showBasicText(String.format("%s vous inflige %d de dégats, il vous reste %d pv",this.monster.getName(), damage,this.player.getHealth()), Flow.Color.YELLOW);
            }else{
                this.flow.showBasicText("Vous avez esquivé l'attaque !");
            }

            if(!this.entityAlive()){
                inFight = false;
                return;
            };

        }
    }

    public boolean hasDodge(String entity){
        int vel = 0;
        if(Objects.equals(entity, "player")){
            vel = player.getVelocity();
        }else if(Objects.equals(entity, "monster")){
            vel = monster.getVelocity();
        }

        return rand.nextInt(10) <= vel;
    }

    private boolean entityAlive(){
        if(this.player.getHealth() <= 0 || this.monster.getHealth() <= 0){
            entityDeath();
            return false;
        }
        return true;
    }

    private void entityDeath(){
        if(this.player.getHealth() <= 0){
            this.inFight = false;
            return;
        }

        int xpAdd = rand.nextInt(50,100);
        this.flow.showBasicText(String.format("Vous avez vaincu %s, Félicitation vous gagnez %d xp", this.monster.getName(), xpAdd), Flow.Color.BLUE);
        this.player.addFightXP(xpAdd);
    }

    public void showInstructions(){
        this.flow.showList("Voici la liste des actions possibles:", actionCommand);
    }

    public void hitEnemy(boolean capacity){
        boolean dodge = hasDodge("monster");
        if(dodge){
            this.flow.showBasicText(this.monster.getName() + " à esquivé l'attaque !");
            return;
        }

        int damage = rand.nextInt(this.player.getDamage() - 2, this.player.getDamage() + 2);
        if(this.player.haveItemByName("Un anneau de force")) damage += damage * 0.25;
        if(this.activeDamagePotion){
            damage *= 2;
            this.activeDamagePotion = false;
        }
        if(capacity) damage *= 2;
        damage /= this.monster.getShield();
        this.monster.removeHealth(damage);
        this.flow.showBasicText(String.format("Vous infligez %d de dégats, il lui reste %d pv",damage,this.monster.getHealth()), Flow.Color.YELLOW);
    }

    public void usePotion(){
        String[] potionList = this.player.getElementByType("potion");
        this.flow.showListIndex("Liste de vos potions:", potionList);
        boolean userUsePotion = false;
        while (!userUsePotion){
            String usePotionIndex = this.flow.inputText("Quelle potion voulez vous utiliser (back pour reculer)?");
            if(Objects.equals(usePotionIndex, "back")){
                playerPlay = false;
                return;
            }
            if(Integer.parseInt(usePotionIndex) < this.player.getNbElementByType("potion")){
                if(Objects.equals(potionList[Integer.parseInt(usePotionIndex)], "Une potion de guérison")){
                    this.player.addHealth(100);
                    this.flow.showBasicText(String.format("Vous utilisez une potion de guérison vous regénérez tous vos pv ! Vous avez maintenant %d pv",this.player.getHealth()), Flow.Color.BLUE);
                    this.player.removeInventoryItemByName("Une potion de guérison");
                    return;
                } else if (Objects.equals(potionList[Integer.parseInt(usePotionIndex)], "Une potion de force")) {
                    this.activeDamagePotion = true;
                    this.player.removeInventoryItemByName("Une potion de force");
                    this.flow.showBasicText("Vous utilisez une potions de force ! Vos dégats seront augmenter au prochain tour", Flow.Color.BLUE);
                    return;
                }
            }
        }

    }

    public void useItem(){
        String[] objectList = this.player.getElementByType("object");
        this.flow.showListIndex("Liste de vos objets:", objectList);
        boolean userUseItem = false;
        while (!userUseItem){
            String useItemIndex = this.flow.inputText("Quelle object voulez vous utiliser (back pour reculer)?");
            if(Objects.equals(useItemIndex, "back")){
                userUseItem = true;
                playerPlay = false;
                return;
            }
            if(Integer.parseInt(useItemIndex) < objectList.length){
                switch (objectList[Integer.parseInt(useItemIndex)]){
                    case "Une Pomme":
                        this.player.addHealth(50);
                        this.flow.showBasicText(String.format("Vous mangez une pomme vous gagnez 50 pv vous avez maintenant %d pv",this.player.getHealth()), Flow.Color.BLUE);
                        this.player.removeInventoryItemByName("Une Pomme");
                        userUseItem = true;
                        break;
                }
            }
        }
    }

    public void useCapacity(){
        if(this.player.getLevel() == 1 || this.userUseCapacity){
            this.flow.showBasicText("Votre capacitée n'est pas disponible !", Flow.Color.RED);
            return;
        }
        hitEnemy(true);
        playerTurn = false;
    }
}
