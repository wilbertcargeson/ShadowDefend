import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class ShadowDefend extends AbstractGame {

    private SlicerSpawn slicerSpawner;
    private BuyPanel buyPanel = new BuyPanel();
    private StatusPanel statusPanel = new StatusPanel();
    private List<Point> trail;
    private List<List<SlicerSpawn>> slicerSpawns;

    public static boolean start;

    // In game stats
    private int status = 0;
    private int wave = 1;
    private int life = 25;

    public static TiledMap map;
    public static List<Slicer> slicers;
    public static List<Tower> towers ;

    public static int timescale;
    public static int money = 5000;

    // Constants
    private final int SLICER_QTY = 5;
    private final double SLICER_INTERVAL = 5;

    public final static int FPS = 60;
    public final static int BASE_TIMESCALE = 1; // Public as this value is used to initialize timescale in other classes


    public static Input test;
    /**
     * Entry point for Bagel game
     *
     * Explore the capabilities of Bagel: https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/
     */
    public static void main(String[] args) {
        // Create new instance of game and run it
        new ShadowDefend().run();
    }
    /**
     * Setup the game
     */
    public ShadowDefend() {
        map = new TiledMap("res/levels/1.tmx");
        trail = map.getAllPolylines().get(0);
        slicers = slicersGenerator(SLICER_QTY, trail);
        slicerSpawner = new SlicerSpawn(slicers,SLICER_INTERVAL);
        timescale = BASE_TIMESCALE;
        start = false;

        towers = new ArrayList<>();
        }

    /**
     * Updates the game state approximately 60 times a second, potentially reading from input.
     * @param input The input instance which provides access to keyboard/mouse state information.
     */
    @Override
    protected void update(Input input) {
        map.draw(0, 0, 0, 0, bagel.Window.getWidth(), bagel.Window.getHeight());
        test = input;
        if ( input.wasPressed(Keys.S) ){
            start = true;
        }

        if (start){
            // Increase timescale
            if ( input.wasPressed(Keys.L) ){
                timescale++;
                slicerSpawner.setTimescale(timescale);
            }
            // Decrease timescale
            if (input.wasPressed(Keys.K) && timescale > BASE_TIMESCALE){
                timescale--;
                slicerSpawner.setTimescale(timescale);
            }
            // Spawns the wave of slicers
            // Close window when the last slicer has exited
            if (!(slicerSpawner.waveSpawn())) {
                Window.close();
            }
        }

        buyPanel.draw();

        buyPanel.towerSelected(input);
        if (input.wasPressed(MouseButtons.LEFT)){
            buyPanel.isClicked(input.getMousePosition());
        }

        // Spawn towers
        for ( int i = 0 ; i < towers.size(); i++){
            towers.get(i).spawn();
        }

        statusPanel.draw(status, wave, life);
    }

    // Creates an array of attackers for the spawner
    private List<Slicer> slicersGenerator(int quantity, List<Point> trail){
        List<Slicer> arr = new ArrayList<Slicer>();
        for ( int i = 0 ; i < quantity ; i++ ){
            arr.add(new SuperSlicer(trail));
        }
        return arr;
    }

    private double getMoney(){return money;}

    public static double calcRad( double opp, double adj){

        // Handling division by 0 error
        if ( (adj == 0) && (opp != 0)){
            if ( opp > 0){
                return 0.5*Math.PI;
            }
            else{
                return -0.5*Math.PI;
            }
        }
        double r = Math.atan(opp/adj);

        // Ensures that we get in range of 0 to 2PI
        while ( r < 0 ){
            r += 2 * Math.PI;
        }
        // Handles where adjacent sign was ignored when opp = 0
        if (( r == 0 ) && (adj<0)){
            return Math.PI;
        }
        // Handles 3rd quartile of the angles
        if ( ( opp > 0 ) && (adj < 0)){
            r -= Math.PI;
        }
        // Handles 4th quartile of the angles
        if ( (opp < 0) && (adj < 0)){
            r += Math.PI;
        }
        return r;
    }

    // File handlers
    public static Image getImageFile(String fName){
        return new Image(String.format("res/images/%s.png", fName));
    }
    public static Font getFontFile( String fName, int size ){
        return new Font(String.format("res/fonts/%s.ttf", fName), size);
    }
    public static TiledMap getMapFile( String fName ){
        return new TiledMap(String.format("res/levels/%s.tmx", fName));
    }

}
