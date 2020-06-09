import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class Tower extends Sprite{

    protected double effectLength;
    protected Rectangle effectBox;
    protected double cooldown;
    protected double cooldownFrameCount;

    protected Tower(Point point){
        rad = 0;
        spriteX = point.x;
        spriteY = point.y;
    }

    /**
     * Runs the tower
     */
    @Override
    public void run() {
        if (ready()){
            checkEnemy();
        }
        checkEnemy();
        drawSprite();
    }

    protected boolean isInRange(Slicer enemy){
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

    protected void checkEnemy(){

        if (ShadowDefend.start) {
            for (int i = 0; i < ShadowDefend.slicers.size(); i++) {
                Slicer enemy = ShadowDefend.slicers.get(i);
                if (isInRange(enemy)) {
                    return;
                }
            }
        }
    }

    // Returns true if ready
    protected boolean ready(){
        if ( cooldownFrameCount < cooldown ){
            cooldownFrameCount += ShadowDefend.timescale;
            return false;
        }
        return true;
    }


}
