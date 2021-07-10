package net.diegoqueres.mypianotiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import static net.diegoqueres.mypianotiles.Cons.*;

public class MainClass extends ApplicationAdapter {

	private ShapeRenderer shapeRenderer;

	private Array<Fileira> fileiras;

	private float tempoTotal;

	private int idxFileiraInferior;

	private int pontos;

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

		fileiras = new Array<>();
		fileiras.add(new Fileira(0,0));
		fileiras.add(new Fileira(1*tileHeight,1));
		fileiras.add(new Fileira(2*tileHeight,2));

		idxFileiraInferior = 0;
		pontos = 0;
	}

	@Override
	public void render () {
		input();
		update(Gdx.graphics.getDeltaTime());

		ScreenUtils.clear(1, 1, 1, 1);
		shapeRenderer.begin();
		for (Fileira f : fileiras) {
			f.draw(shapeRenderer);
		}
		shapeRenderer.end();
	}

	private void input() {
		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = screeny - Gdx.input.getY();
			for (int i = 0; i < fileiras.size; i++) {
				Fileira.TOQUE retorno = fileiras.get(i).toque(x, y);
				if (retorno != Fileira.TOQUE.NENHUM) {
					if (retorno == Fileira.TOQUE.TILE_CORRETA && i == idxFileiraInferior) {
						// tile certa -> fazer algo
						pontos++;
						idxFileiraInferior++;
					} else if (retorno == Fileira.TOQUE.TILE_CORRETA) {
						// finalizar da forma 1: tile certa mas numa fileira superior
						finalizar();
					} else {
						// finalizar -> tile errada. finalizar da forma 2
						finalizar();
					}
					break;
				}
			}
		}
	}

	private void finalizar() {
		Gdx.input.vibrate(200);
	}

	private void update(float deltaTime) {
		tempoTotal += deltaTime;
		velAtual = velIni + ((tileHeight*tempoTotal)/8f);
		for (int i = 0; i < fileiras.size; i++) {
			Fileira.DESCIDA retorno = fileiras.get(i).update(deltaTime);
			if (retorno != Fileira.DESCIDA.NAO_DESCEU) {
				if (retorno == Fileira.DESCIDA.DESCEU_ACERTOU) {
					fileiras.removeIndex(i);
					i--;
					idxFileiraInferior--;
					adicionar();
				}
			}
		}
	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
