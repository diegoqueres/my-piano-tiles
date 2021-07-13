package net.diegoqueres.mypianotiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Cons {
    public static Color verde = new Color(0, .4f, 0, 1);

    public enum ESTADO { PARADO, INICIADO, PERDEU }

    public static int screenx = Gdx.graphics.getWidth();
    public static int screeny = Gdx.graphics.getHeight();

    public static int tileWidth = screenx/4;
    public static int tileHeight = screeny/4;

    public static float velIni = (1 * tileHeight) / 1f;     //2 tiles por segundo
    public static float velAtual = 0f;
}
