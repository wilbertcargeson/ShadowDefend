import bagel.util.Point;
import bagel.util.Rectangle;

public class SuperTank extends Tower {

    private final double radius = 150;

    protected SuperTank(Point point) {
        super(point);
        image = ShadowDefend.getImageFile("supertank");
        effectLength = 2*radius;
        cooldown = 0.5* ShadowDefend.FPS;
        effectBox = new Rectangle(point.x - effectLength/2, point.y - effectLength/2,
                effectLength, effectLength );
        cooldownFrameCount = cooldown;

    }

    @Override
    public boolean isInRange(Slicer enemy){
        Point enemyPoint = enemy.getPoint();
        if ( effectBox.intersects(enemy.getPoint()) ) {
            rad = -calcRad(spriteX - enemyPoint.x, spriteY - enemyPoint.y);
            if (ready()){
                ShadowDefend.projectiles.add(new SuperTankProjectile(this.getPoint(), enemy));
                cooldownFrameCount = 0;
            }
            return true;
        }
        return false;
    }
}
