import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WaveProcessor {

    private List<String> strings = new ArrayList<>();
    public final char SEPARATOR = ',';

    public void main(){
        try {
            File myObj = new File("res/levels/waves.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                strings.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for ( int i = 0 ; i < strings.size(); i ++){
            stringProcessing(strings.get(i));
        }
        System.out.println(strings.toString());
    }


    //<wave number>,spawn,<number to spawn>,<enemy type>,<spawn delay in milliseconds>
    //<wave number>,delay,<delay in milliseconds>

    private void stringProcessing ( String str ){
        String[] arr = str.split(",");

        int wave_no = Integer.parseInt(arr[0]);
        String type = arr[1];
        if ( type == "spawn" ){
            // Do something
        }
        else {
            // Do something with delay
        }

    }

}
