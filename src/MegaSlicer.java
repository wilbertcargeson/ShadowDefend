import bagel.util.Point;
import java.util.List;

public class MegaSlicer extends Slicer{

    private final int CHILD_NUMBER = 2;

    public MegaSlicer(List<Point> trail) {
        super(trail);
        speed = REGULAR_SPEED*0.75;
        reward = 10;
        image = ShadowDefend.getImageFile("megaslicer");
        createChild();
        penalty = children.get(0).penalty * children.size();
    }

    @Override
    public void createChild(){
        for ( int i = 0 ; i < CHILD_NUMBER ; i++ ){
            Slicer child = new SuperSlicer(trail);
            children.add(child);
        }
    }

}
