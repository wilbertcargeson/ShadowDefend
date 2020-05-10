import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;
import java.util.List;

public abstract class Slicer{

    private final List<Point> Trail;
    private Image SlicerImage = new Image("res/images/slicer.png");

    private double slicerX;
    private double slicerY;
    private int index;
    private double rad ;
    private double timescale = ShadowDefend.BASE_TIMESCALE;
    private final int maxIndex;
    private final double UPDATE_LENGTH = 1;

    // Default slicer stats
    public final double REGULAR_SPEED = 2.0;
    public final double REGULAR_HEALTH = 1.0;
    public final double REGULAR_REWARD = 2.0;
    public final double REGULAR_PENALTY = 1.0;

    // Slicer stats
    private double speed = REGULAR_SPEED;
    private double health = REGULAR_HEALTH;
    private double reward = REGULAR_REWARD;
    private double penalty = REGULAR_PENALTY;


    public Slicer(List<Point> trail) {
        Trail = trail;
        index = 1;
        slicerX = Trail.get(0).x;
        slicerY = Trail.get(0).y;
        maxIndex = Trail.size() - 1;
    }


    public void spawn(){
        double count = speed;


        for ( int i = 0 ; i<timescale ; i++) {
            if ( index <= maxIndex) {
                while ( count >= UPDATE_LENGTH) {
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
            index++;
        }
    }

    // Sets new stats for different kinds of slicers
    public void setStats( double speed, double health, double reward, double penalty){
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

    public int getIndex(){ return index;}

    public int getMaxIndex(){ return maxIndex;}

    public void setTimescale(double t){
        timescale = t;
    }

    public double getPenalty(){return penalty;}
    public double getHealth() { return health;}
    public double getReward() { return reward;}
    public double getSpeed() { return speed;}

}
