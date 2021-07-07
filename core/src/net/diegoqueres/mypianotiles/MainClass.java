package net.diegoqueres.mypianotiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import static net.diegoqueres.mypianotiles.Cons.tileHeight;

public class MainClass extends ApplicationAdapter {

	private ShapeRenderer shapeRenderer;

	private Array<Fileira> fileiras;
	
	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

		fileiras = new Array<>();
		fileiras.add(new Fileira(0,0));
		fileiras.add(new Fileira(1*tileHeight,1));
		fileiras.add(new Fileira(2*tileHeight,2));
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		shapeRenderer.begin();
		for (Fileira f : fileiras) {
			f.draw(shapeRenderer);
		}
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
