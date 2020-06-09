import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class ShadowDefend extends AbstractGame {

    private BuyPanel buyPanel = new BuyPanel();
    private StatusPanel statusPanel = new StatusPanel();
    private List<Point> trail;
    private WaveProcessor wp;

    private int level;
    private List<TiledMap> maps = new ArrayList<>();

    public static boolean start;
    public static boolean won = false;

    // In game stats
    public static int waveNo;
    public static int life;
    public static int timescale;
    public static int money;

    public static TiledMap map;

    public static Status status = new Status();

    public static List<Slicer> slicers = new ArrayList<>();
    public static List<Tower> towers = new ArrayList<>();;
    public static List<Projectile> projectiles = new ArrayList<>();
    public static List<Wave> waveList = new ArrayList<>();

    public final static int FPS = 60;
    public final static int BASE_TIMESCALE = 1;

    public final static int START_WAVE_NUMBER = 1;
    public final static int START_LIFE = 25;
    public final static int START_MONEY = 4000;


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

        maps.add(new TiledMap("res/levels/1.tmx"));
        maps.add(new TiledMap("res/levels/2.tmx"));

        level = 0;
        map = maps.get(level);
        trail = map.getAllPolylines().get(0);

        wp = new WaveProcessor("res/levels/waves.txt",trail);
        wp.process();

        waveNo = START_WAVE_NUMBER;
        life = START_LIFE;
        timescale = BASE_TIMESCALE;
        money = START_MONEY;

        start = false;
        }

    /**
     * Updates the game state approximately 60 times a second, potentially reading from input.
     * @param input The input instance which provides access to keyboard/mouse state information.
     */
    @Override
    protected void update(Input input) {
        map.draw(0, 0, 0, 0, bagel.Window.getWidth(), bagel.Window.getHeight());

        int waveIndex = waveNo-1;

        // Level completed
        if (waveIndex >= waveList.size()){
            // Go to next level
            if ( level < maps.size()-1){
                nextLevel();
                return;
            }
            else {
                status.setWin();
                won = true;
            }
        }

        // Run built sprites
        runSprites(projectiles);
        runSprites(towers);
        runSlicers();

        // Draw panels
        buyPanel.run(input);
        statusPanel.draw();

        // Indicate start
        if ( input.wasPressed(Keys.S) ){
            start = true;
        }

        // Level in progress
        if (start && !won){
            status.setProgress();

            // Increase timescale
            if ( input.wasPressed(Keys.L) ){
                timescale++;
            }
            // Decrease timescale
            if (input.wasPressed(Keys.K) && timescale > BASE_TIMESCALE){
                timescale--;
            }

            // Wave has been completed and still alive
            if (!(waveList.get(waveIndex).run()) && (slicers.size() == 0) ) {
                money += 150 + ( waveNo * 100);
                waveNo++;
                status.setWaiting();
                start = false;
            }
        }

        // Lose condition
        if ( life <= 0 ){
            bagel.Window.close();
        }

    }

    // Returns Image of the specified filename
    protected static Image getImageFile(String fName){
        return new Image(String.format("res/images/%s.png", fName));
    }

    // Returns Font of the specified filename
    protected static Font getFontFile( String fName, int size ){
        return new Font(String.format("res/fonts/%s.ttf", fName), size);
    }

    // Inflict player damage if slicer reaches end
    protected static void inflictDamage( double penalty ){ life -= penalty ;}

    // Earn reward from killing slicers
    protected static void earnReward(int reward){
        money += reward;
    }

    // Run sprite list
    private static void runSprites ( List<? extends Sprite> sprites){
        for ( int i = 0 ; i < sprites.size(); i++ ){
            sprites.get(i).run();
        }
    }

    // Run all slicers and remove if dead
    private void runSlicers(){
        // Remove if the slicers are dead
        for ( int i = 0 ; i < ShadowDefend.slicers.size(); i++){
            if (ShadowDefend.slicers.get(i).dead()){
                ShadowDefend.slicers.get(i).aftermath();
                ShadowDefend.slicers.remove(i);
            }
        }
        ShadowDefend.runSprites(ShadowDefend.slicers);
    }

    // Reset everything and proceed to next level
    private void nextLevel(){
        waveNo = 1;
        life = 25;
        money = 500;
        level+=1;
        map = maps.get(level);
        slicers = new ArrayList<>();
        towers = new ArrayList<>();
        projectiles = new ArrayList<>();
        waveList = new ArrayList<>();
        trail = map.getAllPolylines().get(0);
        wp = new WaveProcessor("res/levels/waves.txt",trail);
        wp.process();
    }

}
