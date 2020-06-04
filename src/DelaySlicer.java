import bagel.util.Point;

import java.util.List;

public class DelaySlicer extends Slicer{

    private double duration;
    public DelaySlicer(List<Point> trail, double duration) {
        super(trail);
        this.duration = duration;
    }

    @Override
    public void run(){

    }

}
