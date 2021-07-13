package net.diegoqueres.mypianotiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static net.diegoqueres.mypianotiles.Cons.*;

public class Fileira {
    public enum TOQUE { TILE_CORRETA, FILEIRA_CORRETA, NENHUM }
    public enum DESCIDA { DESCEU_ACERTOU, DESCEU_ERROU, NAO_DESCEU }

    private static final int MAX_TILES = 4;

    public float y;

    private int pos;

    private int correta;  // de 0 a 3 (col correta)

    private boolean ok;   //indica se acertou a tile na fileira

    public Fileira(float y, int correta) {
        this.y = y;
        this.correta = correta;
        this.ok = false;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(verde);
        shapeRenderer.rect(correta * tileWidth, y, tileWidth, tileHeight);

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);

        for (int i = 0; i < MAX_TILES; i++) {
            shapeRenderer.rect(i * tileWidth, y, tileWidth, tileHeight);
        }
    }

    public DESCIDA update(float time) {
        y -= time * velAtual;
        if (y < 0 - tileHeight) {   //se fileira saiu completamente da tela
            if (ok) {
                return DESCIDA.DESCEU_ACERTOU;
            } else {
                return DESCIDA.DESCEU_ERROU;
            }
        }
        return DESCIDA.NAO_DESCEU;
    }

    public TOQUE toque(int tx, int ty) {
        if (ty >= y && ty <= y + tileHeight) {
            pos = tx / tileWidth;
            if (pos == correta) {
                ok = true;
                return TOQUE.TILE_CORRETA;
            } else {
                ok = false;
                return TOQUE.FILEIRA_CORRETA;
            }
        }
        return TOQUE.NENHUM;
    }
}
