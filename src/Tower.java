import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class Tower {
    protected Point point;
    protected Image image = ShadowDefend.getImageFile("tank");
    protected double effectLength = 2*DEFAULT_RADIUS;
    protected Rectangle effectBox;
    protected double radian = 0;

    public final static double DEFAULT_COOLDOWN = 1;
    public final static int DEFAULT_RADIUS = 100;

    protected double cooldown = DEFAULT_COOLDOWN * ShadowDefend.FPS;
    protected double cooldownFrameCount = cooldown;

    protected Tower(Point point) {
        this.point = point;
        effectBox = new Rectangle(point.x - effectLength/2, point.y - effectLength/2,
                effectLength, effectLength );
    }

    public void spawn() {
        checkEnemy();
        image.draw(point.x,point.y, new DrawOptions().setRotation(radian));
        if ( cooldownFrameCount < cooldown ){
            cooldownFrameCount++;
        }
    }

    public Rectangle getBoundingBox(){
        return image.getBoundingBoxAt(point);
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
            radian = -calcRad(this.point.x - enemyPoint.x, this.point.y - enemyPoint.y);
            if (cooldownFrameCount == cooldown){
                ShadowDefend.projectiles.add(new TankProjectile(point, enemy));
                cooldownFrameCount = 0;
            }
            return true;
        }
        return false;
    }

    public double calcRad( double opp, double adj){

        // Handling division by 0 error
        if ( (adj == 0) && (opp != 0)){
            if ( opp > 0){
                return 0.5*Math.PI;
            }
            else{
                return -0.5*Math.PI;
            }
        }
        double r = Math.atan(opp/adj);

        // Ensures that we get in range of 0 to 2PI
        while ( r < 0 ){
            r += 2 * Math.PI;
        }
        // Handles where adjacent sign was ignored when opp = 0
        if (( r == 0 ) && (adj<0)){
            return Math.PI;
        }
        // Handles 3rd quartile of the angles
        if ( ( opp > 0 ) && (adj < 0)){
            r -= Math.PI;
        }
        // Handles 4th quartile of the angles
        if ( (opp < 0) && (adj < 0)){
            r += Math.PI;
        }
        return r;
    }

}
