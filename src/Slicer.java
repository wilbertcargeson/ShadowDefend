import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;
import java.util.List;

public class Slicer extends Attacker{

    private final List<Point> Trail;
    private final Image SlicerImage;

    private double slicerX;
    private double slicerY;
    private int index;
    private double rad ;
    private double timescale = ShadowDefend.BASE_TIMESCALE;
    private final int maxIndex;

    public Slicer(List<Point> trail, Image slicerImage) {
        super(trail, slicerImage);
        Trail = trail;
        SlicerImage = slicerImage;
        index = 1;
        slicerX = Trail.get(0).x;
        slicerY = Trail.get(0).y;
        maxIndex = Trail.size() - 1;
    }
    @Override
    public void spawn(){

        for ( int i = 0 ; i<timescale ; i++) {
            if ( index <= maxIndex) {
                updateSlicer();
            }
        }

        SlicerImage.draw(slicerX, slicerY, new DrawOptions().setRotation(rad));
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

    // Update slicer utilizing vectors
    public void updateSlicer(){
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

    @Override
    public int getIndex(){ return index;}

    @Override
    public int getMaxIndex(){ return maxIndex;}

    @Override
    public void setTimescale(double t){
        timescale = t;
    }
}
