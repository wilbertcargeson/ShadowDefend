import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class Tower extends Sprite{

    protected double effectLength = 2*DEFAULT_RADIUS;
    protected Rectangle effectBox;

    public final static double DEFAULT_COOLDOWN = 1;
    public final static int DEFAULT_RADIUS = 100;

    protected double cooldown = DEFAULT_COOLDOWN * ShadowDefend.FPS;
    protected double cooldownFrameCount = cooldown;

    protected Tower(Point point) {
        effectBox = new Rectangle(point.x - effectLength/2, point.y - effectLength/2,
                effectLength, effectLength );

        image = ShadowDefend.getImageFile("tank");
        rad = 0;
        spriteX = point.x;
        spriteY = point.y;
    }

    @Override
    public void run() {
        if (ready()){
            checkEnemy();
        }
        checkEnemy();
        drawSprite();
    }


    public void checkEnemy(){

        if (ShadowDefend.start) {
            for (int i = 0; i < ShadowDefend.slicers.size(); i++) {
                Slicer enemy = ShadowDefend.slicers.get(i);
                if (isInRange(enemy)) {
                    return;
                }
            }
        }
    }

    public boolean isInRange(Slicer enemy){
        Point enemyPoint = enemy.getPoint();
        if ( effectBox.intersects(enemy.getPoint()) ) {
            rad = -calcRad(spriteX - enemyPoint.x, spriteY - enemyPoint.y);
            if (ready()){
                // Shoot projectile and reset cooldown;
                ShadowDefend.projectiles.add(new TankProjectile(this.getPoint(), enemy));
                cooldownFrameCount = 0;
            }
            return true;
        }
        return false;
    }

    // Returns true if ready
    public boolean ready(){
        if ( cooldownFrameCount < cooldown ){
            cooldownFrameCount += ShadowDefend.timescale;
            return false;
        }
        return true;
    }


}
