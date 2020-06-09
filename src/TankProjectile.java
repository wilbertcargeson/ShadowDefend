import bagel.util.Point;

public class TankProjectile extends Projectile {
    /**
     * Create a regular tank projectile
     * @param start The starting point of the projectile
     * @param target The target of the projectile
     */
    TankProjectile(Point start, Slicer target) {
        super(start, target);
    }
}
