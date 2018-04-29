package org.daleon.Test1;

import org.daleon.framework.gl.Font;
import org.daleon.framework.gl.Texture;
import org.daleon.framework.gl.TextureRegion;
import org.daleon.framework.impl.GLGame;

public class Assets {
    public static Texture picture;
    public static TextureRegion black;
    public static TextureRegion white;
    public static Texture fontTexture;
    public static Font font;

    public static void load(GLGame game){
        picture = new Texture(game,"picter.png");
        black = new TextureRegion(picture,0,64,64,64);
        white = new TextureRegion(picture,64,64,64,64);
        fontTexture = new Texture(game,"font_times_new_roman.png");
        font =new Font(fontTexture,0,0,16,35,62);

    }
    public static void reload(){
        picture.reload();
        fontTexture.reload();
    }
}
