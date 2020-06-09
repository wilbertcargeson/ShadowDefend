import bagel.util.Point;
import bagel.util.Rectangle;

public class Explosive extends Projectile{

    private Rectangle effectBox ;
    private boolean detonate = false;
    private static final int DAMAGE = 500;

    private static double EFFECT_RADIUS = 200;
    private final int COOLDOWN = ShadowDefend.FPS * 2 ;
    private int count = 0;

    /**
     * Creates explosive in the game
     * @param start The point in which the explosive is placed
     */
    public Explosive(Point start) {
        super(start);

        double effectLength = EFFECT_RADIUS*2;
        effectBox = new Rectangle(start.x - effectLength/2, start.y - effectLength/2,
                effectLength, effectLength );
        image = ShadowDefend.getImageFile("explosive");

        spriteX = start.x;
        spriteY = start.y;
    }

    /**
     * Runs the explosive to explode if enemy are within range
     */
    @Override
    public void run(){
        if ( ready() ) {
            for (int i = 0; i < ShadowDefend.slicers.size(); i++) {
                if (isInRange(ShadowDefend.slicers.get(i))) {
                    detonate = true;
                    ShadowDefend.slicers.get(i).damaged(DAMAGE);
                }
            }
        }
        if(detonate){
            ShadowDefend.projectiles.remove(this);
        }
        drawSprite();
    }

    // Checks if any enemy is in range
    private boolean isInRange(Slicer enemy){
        if ( effectBox.intersects(enemy.getPoint()) ) {
            return true;
        }
        return false;
    }

    private boolean ready(){
        if ( count < COOLDOWN ){
            count += ShadowDefend.timescale;
            return false;
        }
        return true;
    }

}
