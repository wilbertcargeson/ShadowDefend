import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class ShadowDefend extends AbstractGame {
    public static TiledMap map;
    private List<Point> trail;
    private SlicerSpawn slicerSpawner;
    private int timescale;
    private List<Slicer> slicerArr;
    private boolean start;
    private BuyPanel buyPanel = new BuyPanel();

    private final int SLICER_QTY = 5;
    private final double SLICER_INTERVAL = 5;

    public final static int BASE_TIMESCALE = 1; // Public as this value is used to initialize timescale in other classes

    public static int money = 500;
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
        slicerArr = slicersGenerator(SLICER_QTY, trail);
        slicerSpawner = new SlicerSpawn(slicerArr,SLICER_INTERVAL);
        timescale = BASE_TIMESCALE;
        start = false;
        }

    /**
     * Updates the game state approximately 60 times a second, potentially reading from input.
     * @param input The input instance which provides access to keyboard/mouse state information.
     */
    @Override
    protected void update(Input input) {
        map.draw(0, 0, 0, 0, bagel.Window.getWidth(), bagel.Window.getHeight());

        buyPanel.draw();

        if (input.wasPressed(MouseButtons.LEFT)){
            buyPanel.isClicked(input.getMousePosition());
        }
        buyPanel.towerSelected(input);

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
