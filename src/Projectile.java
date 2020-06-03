import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Rectangle;
import bagel.util.Vector2;
import bagel.util.Point;

public abstract class Projectile {
    protected double x;
    protected double y;
    protected Slicer target;
    protected Image image = ShadowDefend.getImageFile("tank_projectile");
    protected int damage = 1;
    protected double rad;

    private final int speed = 10;

    Projectile( Point start, Slicer target){
        this.target = target;
        this.x = start.x;
        this.y = start.y;
    }

    public void run(){

        for ( int i = 0 ; i < speed ; i++ ) {
            updateProj();
            if (isHit()) {
                target.damaged(damage);
                ShadowDefend.projectiles.remove(this);
                return;
            }
            image.draw(x,y,new DrawOptions().setRotation(rad));
        }
    }

    boolean isHit(){
        Rectangle boundBox = image.getBoundingBoxAt(new Point(x,y));
        if ( boundBox.intersects(target.getPoint())){
            return true;
        }
        return false;
    }

    // Update slicer utilizing vectors
    public void updateProj(){
        Vector2 vector2 = new Vector2( target.getPoint().x - x, target.getPoint().y - y );
        Point update = vector2.normalised().asPoint();
        x += update.x;
        y += update.y;
        rad = calcRad(update.y, update.x);
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

    // Round up double values to the nearest hundredth
    public double roundHundredth(double n){
        return Math.round(n*100)/100;
    }
}
