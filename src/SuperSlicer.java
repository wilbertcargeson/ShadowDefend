import bagel.util.Point;
import java.util.List;

public class SuperSlicer extends Slicer{

    private final int CHILD_NUMBER = 2;

    /**
     * Create super slicer
     * @param trail The polyline where the slicer is moving through
     */
    public SuperSlicer(List<Point> trail) {
        super(trail);
        speed = REGULAR_SPEED * 0.75;
        health = REGULAR_HEALTH;
        reward = 15;
        image = ShadowDefend.getImageFile("superslicer");
        createChild();
        penalty = children.get(0).penalty * children.size();
    }

    @Override
    protected void createChild(){
        for ( int i = 0 ; i < CHILD_NUMBER ; i++ ){
            Slicer child = new RegularSlicer(trail);
            children.add(child);
        }
    }

}
