package net.diegoqueres.mypianotiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Cons {
    public static Color verde = new Color(0, .4f, 0, 1);
    public static Color certo = new Color(0.655f, 0.988f, 0.604f, 1);
    public static Color errado = new Color(0.71f, 0.282f, 0.302f, 1);

    public enum ESTADO { PARADO, INICIADO, PERDEU }

    public static int screenx = Gdx.graphics.getWidth();
    public static int screeny = Gdx.graphics.getHeight();

    public static int tileWidth = screenx/4;
    public static int tileHeight = screeny/4;

    public static float velIni = (1 * tileHeight) / 1f;     //1 tile por segundo
    public static float velAtual = 0f;
    public static float velAnim = 5f;

    public static final int MAX_TEXTURE_SIZE = 2048;
}
