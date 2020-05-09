import bagel.Image;
import bagel.util.Point;

import java.util.List;

public abstract class Attacker {

    private final List<Point> Trail;
    private final Image SlicerImage;

    protected Attacker(List<Point> trail, Image slicerImage) {
        Trail = trail;
        SlicerImage = slicerImage;
    }


    public abstract void spawn();
    public abstract int getIndex();
    public abstract int getMaxIndex();
    public abstract void setTimescale( double t);
}
