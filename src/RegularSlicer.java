import bagel.util.Point;
import java.util.List;

public class RegularSlicer extends Slicer{
    /**
     * Create a Regular Slicer
     * @param trail The polyline the slicer is moving through
     */
    public RegularSlicer(List<Point> trail) {
        super(trail);
    }

    /**
     * Creates no child
     */
    @Override
    public void createChild(){}
}
