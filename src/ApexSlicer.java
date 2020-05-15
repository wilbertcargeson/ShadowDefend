import bagel.Image;
import bagel.util.Point;
import java.util.List;

public class ApexSlicer extends Slicer{

    private final double CHILD_NUMBER = 4;
    private double speed = 0.5*REGULAR_SPEED;
    private double health = 25*REGULAR_HEALTH;
    private double reward = 150;
    private double penalty;
    private Slicer childSlicer[];


    public ApexSlicer(List<Point> trail) {
        super(trail);
        setStats(speed,health,reward,penalty);
        setSlicerImage(ShadowDefend.getImageFile("MegaSlicer"));
        childSlicer = new Slicer[]{
                new MegaSlicer(trail),
                new MegaSlicer(trail),
                new MegaSlicer(trail),
                new MegaSlicer(trail)};
        penalty = childSlicer[0].getPenalty() * CHILD_NUMBER;
    }


}
