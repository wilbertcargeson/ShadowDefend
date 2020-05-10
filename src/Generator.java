import bagel.util.Point;
import java.util.List;

public class Generator {

    Slicer slicers[];

    public <T extends Slicer> Generator(int quantity, List<Point> trail, Class<T> type) {
        slicers = new Slicer[quantity];
        for (int i = 0; i < quantity; i++) {
            slicers[i] = new SuperSlicer(trail);
        }
    }

}
