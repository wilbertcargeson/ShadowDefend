import bagel.util.Point;
import java.util.List;

public class ApexSlicer extends Slicer{

    private final int CHILD_NUMBER = 4;

    public ApexSlicer(List<Point> trail) {
        super(trail);
        speed = 0.5*REGULAR_SPEED;
        health = 25*REGULAR_HEALTH;
        reward = 150;
        image = ShadowDefend.getImageFile("apexslicer");
        createChild();
        penalty = children.get(0).getPenalty() * CHILD_NUMBER;
    }

    @Override
    public void createChild(){
        for ( int i = 0 ; i < CHILD_NUMBER ; i++ ){
            Slicer child = new MegaSlicer(trail);
            children.add(child);
        }
    }


}
