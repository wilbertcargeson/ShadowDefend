public class FileLoc {
    public final String Images = "res/images/";
    public final String Fonts = "res/fonts/";
    public final String Maps = "res/levels/";

    public String getImageFile( String extension ){
        return Images.concat(extension).concat(".png");
    }

    public String getFontsFile( String extension ){
        return Fonts.concat(extension).concat(".ttf");
    }

    public String getMapsFile( String extension ){
        return Maps.concat(extension).concat(".tmx");
    }
}
