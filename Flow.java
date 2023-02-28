import java.util.Scanner;

public class Flow {
    private final Scanner scan = new Scanner(System.in);
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_BLUE = "\u001B[34m";
    final String ANSI_YELLOW = "\u001B[33m";
    public enum Color {
        RESET,
        RED,
        GREEN,
        BLUE,
        YELLOW
    }
    private final String[] helpListCommand = {
            "\tz\t-\tAvancer vers l'avant",
            "\tq\t-\tAvancer vers la gauche",
            "\ts\t-\tAvancer vers l'arrère",
            "\td\t-\tAvancer vers la droite",
            "\ti\t-\tAfficher son inventaire",
            "\tmap\t-\tAfficher la Carte du Maraudeur",
    };

    public Flow(){}

    public void setColor(Color color){
        switch (color){
            case RESET:
                System.out.print(ANSI_RESET);
                break;
            case RED:
                System.out.print(ANSI_RED);
                break;
            case GREEN:
                System.out.print(ANSI_GREEN);
                break;
            case BLUE:
                System.out.print(ANSI_BLUE);
                break;
            case YELLOW:
                System.out.print(ANSI_YELLOW);
                break;
        }
    }

    public void showBasicText(String text){
        this.showBasicText(text, null);
    }
    public void showBasicText(String text, Color color){
        if(color != null){
            setColor(color);
            System.out.println(text);
            setColor(Color.RESET);
        }else{
            System.out.println(text);
        }
    }

    public void showTextWhiteSpace(String Text){
        System.out.println(Text);
        System.out.println();
    }

    public void showList(String message, String[] list){
        System.out.println();
        System.out.println(message);
        setColor(Color.GREEN);
        for(String value: list){
            System.out.println(value);
        }
        setColor(Color.RESET);
    }

    public void showListIndex(String message, String[] list){
        System.out.println();
        System.out.println(message);
        setColor(Color.GREEN);
        for (int index = 0; index < list.length; index++) {
            System.out.println(String.format("%d. %s",index, list[index]));
        }
        setColor(Color.RESET);
    }

    public String inputText(String Text){
        setColor(Color.BLUE);
        System.out.println();
        System.out.println(Text);
        setColor(Color.RESET);
        String value = this.scan.nextLine();
        return value;

    }

    public void help(){
        System.out.println();
        System.out.println("Voici les commandes disponibles :");
        setColor(Color.GREEN);
        for(String value: this.helpListCommand){
            System.out.println(value);
        }
        setColor(Color.RESET);
    }

    public void showChestContent(Item[] chest){
        this.showTextWhiteSpace(String.format("Vous avez trouvé %s et %s", chest[0].getName(), chest[1].getName()));
    }

    public void itemAdd(String item){
        System.out.println(item + " à bien était ajouté à votre inventaire");
    }

    public void itemRemove(String item){
        System.out.println(item + " à bien était retiré de votre inventaire");
    }

    public void showInventory(Player player){
        this.showBasicText("Inventaire:");
        this.showList("Objet:", player.getElementByType("object"));
        this.showList("Potions:", player.getElementByType("potion"));
        this.showList("Artefact:", player.getElementByType("artefact"));
    }

}
