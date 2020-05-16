package tower;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import main.Spawnnable;

public abstract class Tower implements Spawnnable {
    private Point point;
    private Image image;

    protected Tower(Point point, Image image) {
        this.point = point;
        this.image = image;
    }

    @Override
    public void spawn() {
        image.draw(point.x,point.y);
    }

    public Rectangle getBoundingBox(){
        return image.getBoundingBoxAt(point);
    }
}
