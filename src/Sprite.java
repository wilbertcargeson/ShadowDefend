import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

public abstract class Sprite {

    protected double spriteX;
    protected double spriteY;
    protected double rad = 0;
    protected Image image;
    protected DrawOptions rotate;

    protected abstract void run();

    // Helper functions

    public void drawSprite(){
        rotate = new DrawOptions().setRotation(rad);
        image.draw(spriteX, spriteY, rotate);
    }

    public void drawSprite( DrawOptions drawOptions){
        image.draw(spriteX, spriteY, drawOptions);
    }

    // Update sprite coordinates to move towards the target, returns the distance moved
    public double moveSpriteTo( Point target ){
        Vector2 vector2 = new Vector2(target.x - spriteX, target.y - spriteY);
        Point update = vector2.normalised().asPoint();
        spriteX += update.x;
        spriteY += update.y;
        rad = calcRad(update.y, update.x);

        return roundHundredth(vector2.length());
    }


    // Round up double values to the nearest hundredth
    public double roundHundredth(double n){
        return Math.round(n*100)/100;
    }

    // Calculate rad using opposite and adjacent
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

    // Return coordinates as Point
    public Point getPoint(){
        return new Point(spriteX, spriteY);
    }

    // Set coordinate according to
    public void setXY( Point point ){
        spriteX = point.x;
        spriteY = point.y;
    }

    public Rectangle getBoundingBox(){
        return image.getBoundingBoxAt(this.getPoint());
    }


}
