import bagel.util.Point;

public class SuperTankProjectile extends Projectile{

    /**
     * Creates the super tank projectile
     * @param start The starting point
     * @param target The target the projectile is heading to
     */
    SuperTankProjectile(Point start, Slicer target) {
        super(start, target);
        image = ShadowDefend.getImageFile("supertank_projectile");
        damage = DEFAULT_DAMAGE * 3;
    }
}
