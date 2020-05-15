import bagel.Image;
import bagel.util.Point;
import java.util.List;

public class SuperSlicer extends Slicer{

    private final double CHILD_NUMBER = 2;
    private double speed = REGULAR_SPEED * 0.75;
    private double health = REGULAR_HEALTH;
    private double reward = 15.0;
    private double penalty;
    private Slicer childSlicer[];


    public SuperSlicer(List<Point> trail) {
        super(trail);
        setStats(speed,health,reward,penalty);
        setSlicerImage(ShadowDefend.getImageFile("SuperSlicer"));
        childSlicer = new Slicer[]{new RegularSlicer(trail), new RegularSlicer(trail)};
        penalty = childSlicer[0].getPenalty() * CHILD_NUMBER;
    }

    public double getPenalty(){
        return penalty;
    }
}
