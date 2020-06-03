import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.List;


public abstract class Slicer{

    private final List<Point> Trail;
    private Image SlicerImage = new Image("res/images/slicer.png");

    protected double slicerX;
    protected double slicerY;

    private int index;
    private double rad ;
    private final int maxIndex;
    private final double UPDATE_LENGTH = 1;

    // Default slicer stats
    public final double REGULAR_SPEED = 2.0;

    protected double speed = REGULAR_SPEED;

    public final int REGULAR_HEALTH = 1;
    public final int REGULAR_REWARD = 2;
    public final int REGULAR_PENALTY = 1;

    // Slicer.Slicer stats
    protected int health = REGULAR_HEALTH;
    protected int reward = REGULAR_REWARD;
    protected int penalty = REGULAR_PENALTY;

    protected List<Slicer> children = new ArrayList<>();

    public Slicer(List<Point> trail) {
        Trail = trail;
        index = 1;
        slicerX = Trail.get(0).x;
        slicerY = Trail.get(0).y;
        maxIndex = Trail.size() - 1;
    }


    public void spawn(){
        // If destroyed
        if ( health <= 0 ){
            spawnChildren();
            ShadowDefend.earnReward(reward);
            ShadowDefend.slicers.remove(this);
            return;
        }
        // If reach end of polyline
        if ( index >= maxIndex ){
            ShadowDefend.inflictDamage(penalty);
            ShadowDefend.slicers.remove(this);
        }


        double count = speed;
        for ( int i = 0 ; i<ShadowDefend.timescale ; i++) {
            if ( index <= maxIndex) {
                while ( count >= UPDATE_LENGTH){
                    updateSlicer(UPDATE_LENGTH);
                    count --;
                }
                updateSlicer(count);
            }
        }

        SlicerImage.draw(slicerX, slicerY, new DrawOptions().setRotation(rad));



    }

    // Update slicer utilizing vectors
    public void updateSlicer( double i ){
        Vector2 vector2 = new Vector2(Trail.get(index).x-slicerX, Trail.get(index).y-slicerY );
        Point update = vector2.normalised().asPoint();
        slicerX += update.x;
        slicerY += update.y;
        rad = calcRad(update.y, update.x);

        // If the slicer has arrived at the point, go to next
        if (roundHundredth(vector2.length()) == 0) {
            if ( index < maxIndex){
                index++;
            }
        }
    }

    // Sets new stats for different kinds of slicers
    public void setStats( double speed, int health, int reward, int penalty){
        this.speed = speed;
        this.health = health;
        this.reward = reward;
        this.penalty = penalty;
    }

    public void setSlicerImage( Image slicerImage){
        SlicerImage = slicerImage;
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


    public int getPenalty(){return penalty;}
    public int getHealth() { return health;}
    public int getReward() { return reward;}
    public double getSpeed() { return speed;}

    public Point getPoint(){
        return new Point(slicerX,slicerY);
    }



    public void damaged ( int damage ){
        health -= damage;
    }

    // Spawn child slicers on destory
    public void spawnChildren(){
        for ( int i = 0 ; i < children.size(); i++ ){
            Slicer child = children.get(i);
            child.setXY(this.getPoint());
            child.setIndex(this.index);
            ShadowDefend.slicers.add( child );

        }
    }

    public void setXY( Point point ){
        slicerX = point.x;
        slicerY = point.y;
    }

    public void setIndex( int index ){this.index = index;}

}