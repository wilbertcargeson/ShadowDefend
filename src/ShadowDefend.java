import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;
import java.util.List;

public class ShadowDefend extends AbstractGame {
    private TiledMap map;
    private List<Point> trail;
    private Spawner slicerSpawner;
    private int timescale;
    private Slicer[] slicerArr;
    private boolean start;

    private final int SLICER_QTY = 5;
    private final double SLICER_INTERVAL = 5;

    public final static int BASE_TIMESCALE = 1; // Public as this value is used to initialize timescale in other classes

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
        slicerSpawner = new Spawner(slicerArr,SLICER_INTERVAL,SLICER_QTY);
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
    private Slicer[] slicersGenerator(int quantity, List<Point> trail){
        Slicer[] arr = new Slicer[quantity];
        for ( int i = 0 ; i < quantity ; i++ ){
            arr[i] = new SuperSlicer(trail);
        }
        return arr;
    }
}
