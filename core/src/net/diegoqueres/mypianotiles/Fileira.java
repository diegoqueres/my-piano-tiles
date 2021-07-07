package net.diegoqueres.mypianotiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static net.diegoqueres.mypianotiles.Cons.tileHeight;
import static net.diegoqueres.mypianotiles.Cons.tileWidth;
import static net.diegoqueres.mypianotiles.Cons.verde;

public class Fileira {
    private static final int MAX_TILES = 4;

    private float y;

    private int correta;  // de 0 a 3 (col correta)

    public Fileira(float y, int correta) {
        this.y = y;
        this.correta = correta;
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
}
