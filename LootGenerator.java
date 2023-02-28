import java.util.Random;

public class LootGenerator {
    private final String[] listObject = {"Une Pomme"};
    private final String[] listPotion = {"Une potion de guérison", "Une potion de force"};
    private final String[] listArtefact = {"Une amulette de régénération", "Un anneau de force", "Un collier de résistance"};
    Random rand = new Random();

    public LootGenerator(){}

    public Item[] newChestContent(){
        Item[] chestContent = new Item[2];
        chestContent[0] = new Item(listObject[rand.nextInt(listObject.length)], "object");
        chestContent[1] = new Item(listPotion[rand.nextInt(listPotion.length)], "potion");
        return chestContent;
    }

    public Item newArtefact(){
        return new Item(listArtefact[rand.nextInt(listArtefact.length)], "artefact");
    }
}
