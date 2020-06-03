import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Colour;
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
    public static Status status = new Status();
    public static int wave = 1;
    public static int life = 25;
    public static int timescale;
    public static int money = 5000;

    public static TiledMap map;
    public static List<Slicer> slicers = new ArrayList<>();
    public static List<Tower> towers = new ArrayList<>();;
    public static List<Projectile> projectiles = new ArrayList<>();

    // Constants
    private final int SLICER_QTY = 20;
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
        slicerSpawner = new SlicerSpawn(slicersGenerator(SLICER_QTY, trail),SLICER_INTERVAL);
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
            status.setProgress();
        }

        if (start){
            // Increase timescale
            if ( input.wasPressed(Keys.L) ){
                timescale++;
            }
            // Decrease timescale
            if (input.wasPressed(Keys.K) && timescale > BASE_TIMESCALE){
                timescale--;
            }

            // Win condition
            if (!(slicerSpawner.runWave()) && ( life > 0) ) {
                status.setWin();
                start = false;
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

        statusPanel.draw();

        // Lose condition
        if ( life <= 0 ){
            loseCondition(input);
        }
        runProjectile();
    }

    // Creates an array of attackers for the spawner
    private List<Slicer> slicersGenerator(int quantity, List<Point> trail){
        List<Slicer> arr = new ArrayList<Slicer>();
        for ( int i = 0 ; i < quantity ; i++ ){
            arr.add(new SuperSlicer(trail));
        }
        return arr;
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

    public static void inflictDamage( double penalty ){ life -= penalty ;}

    private void loseCondition(Input input){
        start = false;
        Font font = getFontFile("DejaVuSans-Bold", 144 );
        font.drawString("GAME OVER", bagel.Window.getWidth()/2 - font.getWidth("GAME OVER")/2
                , bagel.Window.getHeight()/2, new DrawOptions().setBlendColour(Colour.BLACK));

        String restart = "PRESS R TO CONTINUE";
        font = getFontFile("DejaVuSans-Bold", 36);
        font.drawString(restart, bagel.Window.getWidth()/2 - font.getWidth(restart)/2,
                bagel.Window.getHeight()/2 + 144, new DrawOptions().setBlendColour(Colour.GREEN));

        if (input.wasPressed(Keys.R)){
            life = 25;
            start = true;
        }
    }

    public static void earnReward(int reward){
        money += reward;
    }

    public void runProjectile(){
        for ( int i = 0 ; i < projectiles.size(); i++){
            projectiles.get(i).run();
        }
    }



}
