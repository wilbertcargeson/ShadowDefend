import bagel.util.Point;
import java.util.List;

public class RegularSlicer extends Slicer{
    public RegularSlicer(List<Point> trail) {
        super(trail);
    }

    @Override
    public void createChild(){}
}
