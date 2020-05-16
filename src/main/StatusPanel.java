package main;

import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;

import java.util.Arrays;
import java.util.List;


public class StatusPanel {

    private Image image = ShadowDefend.getImageFile("statuspanel");
    private List<String> stringList = Arrays.asList("Winner!","Placing", "Wave In Progress", "Awaiting Start");
    private Font font = ShadowDefend.getFontFile("DejaVuSans-Bold", 12);

    public void draw( int status, int wave, int life ){
        double imageX = 0;
        double imageY = bagel.Window.getHeight() - image.getHeight();
        image.drawFromTopLeft(imageX, imageY);

        // Y Coordinate of the middle of the status panel
        double textY = bagel.Window.getHeight() - image.getHeight()/2 ;

        // Wave number
        font.drawString(String.format("Wave:%d",wave), 3, textY);

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
        font.drawString(String.format("Status: %s", stringList.get(status)), bagel.Window.getWidth() * 0.66, textY);

        // Print live
        font.drawString(String.format("Lives : %d", life), bagel.Window.getWidth() - 75, textY);
    }
}
