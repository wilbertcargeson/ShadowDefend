import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;


public class StatusPanel {

    private Image image = ShadowDefend.getImageFile("statuspanel");
    private Font font = ShadowDefend.getFontFile("DejaVuSans-Bold", 12);

    public void draw(){
        double imageX = 0;
        double imageY = bagel.Window.getHeight() - image.getHeight();
        image.drawFromTopLeft(imageX, imageY);

        // Y Coordinate of the middle of the status panel
        double textY = bagel.Window.getHeight() - image.getHeight()/2 ;

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
}
