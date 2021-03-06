import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;


public class StatusPanel {

    private static Image statuspanel = ShadowDefend.getImageFile("statuspanel");
    private Font font = ShadowDefend.getFontFile("DejaVuSans-Bold", 12);

    /**
     * Draws the status with the appropriate texts
     */
    public void draw(){
        double imageX = 0;
        double imageY = bagel.Window.getHeight() - statuspanel.getHeight();
        statuspanel.drawFromTopLeft(imageX, imageY);

        // Y Coordinate of the middle of the status panel
        double textY = bagel.Window.getHeight() - statuspanel.getHeight()/2 ;

        // Wave number
        font.drawString(String.format("Wave:%d",ShadowDefend.waveNo), 3, textY);

        // Print timescale into status panel
        double timescale = ShadowDefend.timescale;
        if( timescale > ShadowDefend.BASE_TIMESCALE){
            font.drawString(String.format("Time Scale: %.1f", timescale), bagel.Window.getWidth() * 0.33 , textY,
                    new DrawOptions().setBlendColour(Colour.GREEN));
        }
        else {
            font.drawString(String.format("Time Scale: %.1f", timescale),bagel.Window.getWidth() * 0.33, textY);
        }

        // Print status
        font.drawString(String.format("Status: %s", ShadowDefend.status.getStatus()),
                bagel.Window.getWidth() * 0.66, textY);

        // Print live
        font.drawString(String.format("Lives : %d", ShadowDefend.life), bagel.Window.getWidth() - 75, textY);
    }

    /**
     * Gets the height of the status panel
     * @return
     */
    public static double getHeight(){
        return statuspanel.getHeight();
    }
}
