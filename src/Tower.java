import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import ShadowDefend;

public abstract class Tower {
    private Point point;
    private Image image;
    private double effectLength = 200;
    private Rectangle effectBox;
    private double radian = 0;

    protected Tower(Point point, Image image) {
        this.point = point;
        this.image = image;
        effectBox = new Rectangle(point.x - effectLength/2, point.y - effectLength/2,
                effectLength, effectLength );
        System.out.println(effectBox.toString());
    }

    public void spawn() {
        checkEnemy();
        image.draw(point.x,point.y, new DrawOptions().setRotation(radian));
    }

    public Rectangle getBoundingBox(){
        return image.getBoundingBoxAt(point);
    }

    public void checkEnemy(){
        if (ShadowDefend.start) {
            for (int i = 0; i < ShadowDefend.slicers.size(); i++) {
                Point enemyPoint = ShadowDefend.slicers.get(i).getPoint();
                if (isInRange(enemyPoint)) {
                    return;
                }
            }
        }
    }

    public boolean isInRange(Point enemyPoint){
        if ( effectBox.intersects(enemyPoint) ){
            radian = -ShadowDefend.calcRad(this.point.x - enemyPoint.x , this.point.y - enemyPoint.y );
            return true;
        }
        return false;
    }


}
