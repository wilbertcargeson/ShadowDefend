import bagel.util.Point;

import java.util.List;

public class MegaSlicer extends Slicer{

    private final int CHILD_NUMBER = 2;
    private Slicer childSlicer[];


    public MegaSlicer(List<Point> trail) {
        super(trail);
        speed = REGULAR_SPEED*0.75;
        reward = 10;
        image = ShadowDefend.getImageFile("megaslicer");
        // Implement children..
        health = childSlicer[0].getHealth() * 2;
        penalty = childSlicer[0].getPenalty() * CHILD_NUMBER;
    }

}
