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

    private boolean destruindo;

    private float anim;

    private boolean acertou;   //indica se acertou a tile na fileira

    public Fileira(float y, int correta) {
        this.y = y;
        this.correta = correta;
        this.acertou = false;
        this.destruindo = false;
        this.anim = 0;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(verde);
        float xCorreta = correta * tileWidth;
        shapeRenderer.rect(xCorreta, y, tileWidth, tileHeight);

        if (destruindo)
            drawAnim(acertou, shapeRenderer);

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);

        for (int i = 0; i < MAX_TILES; i++) {
            float x = i * tileWidth;
            shapeRenderer.rect(x, y, tileWidth, tileHeight);
        }
    }

    private void drawAnim(boolean acertou, ShapeRenderer shapeRenderer) {
        final float x = pos*tileWidth;
        if (acertou)
            shapeRenderer.setColor(certo);
        else
            shapeRenderer.setColor(errado);

        shapeRenderer.rect(
                x + (tileWidth - anim*tileWidth)/2f,
                y + (tileHeight - anim*tileHeight)/2f,
                anim*tileWidth,
                anim*tileHeight
        );
    }

    public void updateAnim(float time) {
        if (destruindo && anim < 1) {
            anim += velAnim * time;
            if (anim >= 1)
                anim = 1;
        }
    }

    public DESCIDA update(float time) {
        y -= time * velAtual;
        if (y < 0 - tileHeight) {   //se fileira saiu completamente da tela
            if (acertou) {
                return DESCIDA.DESCEU_ACERTOU;
            } else {
                erro();
                return DESCIDA.DESCEU_ERROU;
            }
        }
        return DESCIDA.NAO_DESCEU;
    }

    public TOQUE toque(int tx, int ty) {
        if (ty >= y && ty <= y + tileHeight) {
            pos = tx / tileWidth;
            if (pos == correta) {
                acertou = true;
                destruindo = true;
                return TOQUE.TILE_CORRETA;
            } else {
                acertou = false;
                destruindo = true;
                return TOQUE.FILEIRA_CORRETA;
            }
        }
        return TOQUE.NENHUM;
    }

    public void erro() {
        destruindo = true;
        pos = correta;
    }
}
