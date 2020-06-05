import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Colour;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class ShadowDefend extends AbstractGame {

    private BuyPanel buyPanel = new BuyPanel();
    private StatusPanel statusPanel = new StatusPanel();
    private List<Point> trail;
    private WaveProcessor wp;

    //TODO Create multiple level mechanism
    private int level;
    private List<TiledMap> maps = new ArrayList<>();

    public static boolean start;
    public static boolean won = false;

    // In game stats
    public static int waveNo = 1;
    public static int life = 25;
    public static int timescale;
    public static int money = 4000;

    public static TiledMap map;

    public static Status status = new Status();

    public static List<Slicer> slicers = new ArrayList<>();
    public static List<Tower> towers = new ArrayList<>();;
    public static List<Projectile> projectiles = new ArrayList<>();
    public static List<Wave> waveList = new ArrayList<>();

    public final static int FPS = 60;
    public final static int BASE_TIMESCALE = 1;


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

        int waveIndex = waveNo-1;

        // Level completed
        if (waveIndex >= waveList.size()){
            // Go to next level
            if ( level < maps.size()){
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
        buyPanel.draw();
        buyPanel.towerSelected(input);
        if (input.wasPressed(MouseButtons.LEFT)){
            buyPanel.isClicked(input.getMousePosition());
        }
        statusPanel.draw();

        if ( input.wasPressed(Keys.S) ){
            start = true;
        }

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

        // Win condition
        if (won){
            Font font = getFontFile("DejaVuSans-Bold", 144 );
            font.drawString("YOU WIN!", bagel.Window.getWidth()/2 - font.getWidth("YOU WIN!")/2
                    , bagel.Window.getHeight()/2, new DrawOptions().setBlendColour(Colour.BLACK));
        }

        // Lose condition
        if ( life <= 0 ){
            //bagel.Window.close();
            loseCondition(input);
            start = false;
        }
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

        String restart = "INSERT COIN TO CONTINUE ( press R )";
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

    public static void runSprites ( List<? extends Sprite> sprites){
        for ( int i = 0 ; i < sprites.size(); i++ ){
            sprites.get(i).run();
        }
    }

    private List<Slicer> slicersGenerator(int quantity) {
        List<Slicer> arr = new ArrayList<Slicer>();
        for (int i = 0; i < quantity; i++) {
            arr.add(new RegularSlicer(trail));
        }
        return arr;

    }

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
