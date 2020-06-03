import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class ApexSlicer extends Slicer{

    private final int CHILD_NUMBER = 4;

    public ApexSlicer(List<Point> trail) {
        super(trail);
        setStats(speed,health,reward,penalty);
        setSlicerImage(ShadowDefend.getImageFile("apexslicer"));

        // Add children slicers
        for ( int i = 0 ; i < CHILD_NUMBER ; i++ ){
            children.add(new MegaSlicer(trail));
        }

        penalty = children.get(0).getPenalty() * CHILD_NUMBER;
        speed = 0.5*REGULAR_SPEED;
        health = 25*REGULAR_HEALTH;
        reward = 150;
    }


}
