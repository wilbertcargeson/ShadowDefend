import bagel.util.Point;

import java.util.List;

public class MegaSlicer extends Slicer{

    private final double CHILD_NUMBER = 2;
    private double speed = REGULAR_SPEED*0.75;
    private double health;
    private double reward = 10;
    private double penalty;
    private Slicer childSlicer[];


    public MegaSlicer(List<Point> trail) {
        super(trail);
        setStats(speed,health,reward,penalty);
        setSlicerImage(ShadowDefend.getImageFile("megaslicer"));
        childSlicer = new Slicer[]{new SuperSlicer(trail), new SuperSlicer(trail)};
        health = childSlicer[0].getHealth() * 2;
        penalty = childSlicer[0].getPenalty() * CHILD_NUMBER;
    }

}
