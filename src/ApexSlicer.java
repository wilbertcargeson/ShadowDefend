import bagel.util.Point;
import java.util.List;

public class ApexSlicer extends Slicer{

    private final int CHILD_NUMBER = 4;

    /**
     * Creates the Apex Slicer
     * @param trail The polyline in which the Slicer will move through
     */
    public ApexSlicer(List<Point> trail) {
        super(trail);
        speed = 0.5*REGULAR_SPEED;
        health = 25*REGULAR_HEALTH;
        reward = 150;
        image = ShadowDefend.getImageFile("apexslicer");
        createChild();
        penalty = children.get(0).getPenalty() * CHILD_NUMBER;
    }

    /**
     * Create MegaSlicer children
     */
    @Override
    protected void createChild(){
        for ( int i = 0 ; i < CHILD_NUMBER ; i++ ){
            Slicer child = new MegaSlicer(trail);
            children.add(child);
        }
    }


}
