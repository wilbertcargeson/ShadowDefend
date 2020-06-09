import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Tank extends Tower {

    public final static double DEFAULT_COOLDOWN = 1;
    public final static int DEFAULT_RADIUS = 100;

    /**
     * Creates as tank
     * @param point The point where the tank is placed
     */
    public Tank(Point point) {
        super(point);
        image = ShadowDefend.getImageFile("tank");
        effectLength = 2 * DEFAULT_RADIUS;
        cooldown = DEFAULT_COOLDOWN * ShadowDefend.FPS;
        cooldownFrameCount = cooldown;
        effectBox = new Rectangle(point.x - effectLength / 2, point.y - effectLength / 2,
                effectLength, effectLength);
    }


}




