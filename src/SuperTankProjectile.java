import bagel.util.Point;

public class SuperTankProjectile extends Projectile{

    SuperTankProjectile(Point start, Slicer target) {
        super(start, target);
        image = ShadowDefend.getImageFile("supertank_projectile");
    }
}
