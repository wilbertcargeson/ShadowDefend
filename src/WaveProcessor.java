import bagel.util.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WaveProcessor {

    private List<String> strings = new ArrayList<>();
    public final String SEPARATOR = ",";
    public String filename;
    public List<Point> trail;

    public static final String REG_SLICER = "slicer";
    public static final String SUPER_SLICER = "superslicer";
    public static final String MEGA_SLICER = "megaslicer";
    public static final String APEX_SLICER = "apexslicer";
    public static final int MS_TO_S = 1000;

    public WaveProcessor( String filename, List<Point> trail){
        this.filename = filename;
        this.trail = trail;
    }

    public void process(){
        readFile();
        for ( int i = 0 ; i < strings.size() ; i++ ){
            stringProcessing(strings.get(i));
        }
    }

    // Stores the data of the text file into the list
    public void readFile(){
        try {
            File myObj = new File(filename);
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
    }

    // Process string
    private void stringProcessing ( String str ){
        String[] arr = str.split(SEPARATOR);

        int waveNum = Integer.parseInt(arr[0]);
        if ( waveNum > ShadowDefend.waveList.size() ){
            ShadowDefend.waveList.add( new Wave());
        }

        String type = arr[1];
        if ( type.equals("spawn") ){
            // Add slicer spawn
            int spawnQty = Integer.parseInt(arr[2]);
            String enemyType = arr[3];
            Double delay = Double.parseDouble(arr[4])/MS_TO_S;
            addSpawner(waveNum,spawnQty,enemyType,delay);
        }
        else if( type.equals("delay")){
            // Add delay
            int duration = Integer.parseInt(arr[2]);
            DelayEvent delayEvent = new DelayEvent(duration/MS_TO_S);
            ShadowDefend.waveList.get(waveNum-1).add(delayEvent);
        }

    }

    // Add appropriate slicer into the spawner
    private void addSpawner( int waveNum ,int spawnQty, String enemyType, Double delay){
        List<Slicer> slicerList = new ArrayList<>();

        if ( enemyType.equals(REG_SLICER) ){
            for ( int i = 0 ; i < spawnQty ; i++){
                slicerList.add(new RegularSlicer(trail));
            }
        }
        else if ( enemyType.equals(SUPER_SLICER)){
            for ( int i = 0 ; i < spawnQty ; i++){
                slicerList.add(new SuperSlicer(trail));
            }

        }
        else if ( enemyType.equals(MEGA_SLICER) ){
            for ( int i = 0 ; i < spawnQty ; i++){
                slicerList.add(new MegaSlicer(trail));
            }

        }
        else if ( enemyType.equals(APEX_SLICER) ){
            for ( int i = 0 ; i < spawnQty ; i++){
                slicerList.add(new ApexSlicer(trail));
            }

        }
        SlicerSpawn current = new SlicerSpawn(slicerList, delay);
        int waveIndex = waveNum-1;
        ShadowDefend.waveList.get(waveIndex).add(current);
    }

}


