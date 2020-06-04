import bagel.util.Point;
import bagel.util.Rectangle;

public class Explosive extends Projectile{

    private Rectangle effectBox ;
    private boolean detonate = false;
    private static final int DAMAGE = 500;

    private static double EFFECT_RADIUS = 200;
    private final int COOLDOWN = ShadowDefend.FPS * 2 ;
    private int count = 0;

    public Explosive(Point start) {
        super(start);

        double effectLength = EFFECT_RADIUS*2;
        effectBox = new Rectangle(start.x - effectLength/2, start.y - effectLength/2,
                effectLength, effectLength );
        image = ShadowDefend.getImageFile("explosive");

        spriteX = start.x;
        spriteY = start.y;
    }


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
    public boolean isInRange(Slicer enemy){
        if ( effectBox.intersects(enemy.getPoint()) ) {
            return true;
        }
        return false;
    }

    public boolean ready(){
        if ( count < COOLDOWN ){
            count += ShadowDefend.timescale;
            return false;
        }
        return true;
    }

}
