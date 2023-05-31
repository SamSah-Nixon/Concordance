/*
 * Sam SN
 * Parses and stores information about the count and location of words in a text file
 * Created: 5/18/2023
 * Modified: 5/31/2023
 */
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        FileReader reader;
		Scanner inData = null;
        //Read and parse the included .txt file.
		try{
			reader = new FileReader("Input.txt");
			inData = new Scanner(reader);
		} catch(IOException e){
			System.out.println("Opening input file failed: "+e);	
			System.exit(0);
		}
        MyListMap<String, ArrayList<String>> map = new MyListMap<>();
        AVLTree<KeyValuePair<String,Integer>> tree = new AVLTree<>();
        int lineNum = 0;
        //Read each line of the file
        while(inData.hasNextLine()){
            lineNum++;
            String[] line = inData.nextLine().split(" ");
            int wordNum = 0;
            //Read each word of the line
            for(String word : line){
                //Map
                wordNum++;
                map.put(word, new ArrayList<>());
                //Add location of word to the map list
                try {
                    ArrayList<String> list = map.get(word);
                    list.add(lineNum + ":" + wordNum);
                    map.set(word,list);
                } catch (KeyError e) {
                    System.out.println("KeyError: " + e);
                }

                //Tree
                if(!tree.insert(new KeyValuePair<>(word,1))){
                    //Get number of previous tree node
                    int number = tree.findData(new KeyValuePair<>(word,null)).getSecond();
                    //Update tree node
                    tree.set(new KeyValuePair<>(word,null),
                             new KeyValuePair<>(word,number+1));
                }
            }

        }
        System.out.println("Map"+map);
        System.out.println("Tree"+tree);
    }   
}
