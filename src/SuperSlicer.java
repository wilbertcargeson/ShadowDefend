import bagel.util.Point;

import java.util.List;

public class SuperSlicer extends Slicer{

    private final double CHILD_NUMBER = 2;
    List<Point> trail;

    public SuperSlicer(List<Point> trail) {
        super(trail);
        this.trail = trail;
        speed = REGULAR_SPEED * 0.75;
        health = REGULAR_HEALTH;
        reward = 15;
        setSlicerImage(ShadowDefend.getImageFile("superslicer"));
        createChild();
        penalty = 2;
    }

    public int getPenalty(){
        return penalty;
    }

    public void createChild(){
        for ( int i = 0 ; i < CHILD_NUMBER ; i++ ){
            Slicer child = new RegularSlicer(trail);
            children.add(child);
        }
    }

}
