/*
 * Sam SN
 * Parses and stores information about the count and location of words in a text file
 * Created: 5/18/2023
 * Modified: 6/01/2023
 */

public class Main {
    public static void main(String[] args) {
        Concordance concordance = new Concordance("input.txt");
        System.out.println("Map\n"+concordance.getMap());
        System.out.println("Tree\n"+concordance.getTree());
    }   
}
