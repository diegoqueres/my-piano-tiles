package net.diegoqueres.mypianotiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import static net.diegoqueres.mypianotiles.Cons.*;
import static net.diegoqueres.mypianotiles.Cons.ESTADO.*;

public class MainClass extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer;

	private SpriteBatch batch;

	private Array<Fileira> fileiras;

	private float tempoTotal;

	private int idxFileiraInferior;

	private int pontos;

	private Random random;

	private ESTADO estado;

	private Piano piano;

	private Texture textureIniciar;

	private BitmapFont fonte;

	private GlyphLayout glyphLayout;


	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		batch = new SpriteBatch();
		fileiras = new Array<>();
		random = new Random();
		piano = new Piano("natal");
		textureIniciar = new Texture("iniciar.png");
		glyphLayout = new GlyphLayout();
		carregarFonte();

		iniciar();
	}

	private void carregarFonte() {
		FreeTypeFontGenerator.setMaxTextureSize(MAX_TEXTURE_SIZE);
		FreeTypeFontGenerator generator =
				new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter =
				new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = (int) (.07f*screeny);
		parameter.color = Color.CYAN;
		fonte = generator.generateFont(parameter);
		generator.dispose();
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

		batch.begin();
		if (estado == PARADO) {
			int x = 0, y = tileHeight / 4;
			batch.draw(textureIniciar, x, y, screenx, tileHeight / 2);
		}
		fonte.draw(batch, String.valueOf(pontos), 0, screeny);
		fonte.draw(batch, getTaxaTilesPorSegundo(), screenx-getTextWidth(fonte, getTaxaTilesPorSegundo()), screeny);
		batch.end();
	}

	private void input() {
		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = screeny - Gdx.input.getY();

			switch (estado) {
				case PARADO:
					estado = INICIADO;
				case INICIADO:
					for (int i = 0; i < fileiras.size; i++) {
						Fileira.TOQUE retorno = fileiras.get(i).toque(x, y);
						if (retorno != Fileira.TOQUE.NENHUM) {
							if (retorno == Fileira.TOQUE.TILE_CORRETA && i == idxFileiraInferior) {
								// tile certa
								pontos++;
								idxFileiraInferior++;
								piano.tocar();
							} else if (retorno == Fileira.TOQUE.TILE_CORRETA) {
								// tile certa mas numa fileira superior
								fileiras.get(idxFileiraInferior).erro();
								finalizar(false);
							} else {
								// tile errada
								finalizar(false);
							}
							break;
						}
					}
					break;

				case PERDEU:
					iniciar();
					break;
			}
		}
	}

	private void finalizar(boolean isSubirFileira) {
		Gdx.input.vibrate(200);
		estado = PERDEU;
		if (isSubirFileira) {
			for (Fileira f : fileiras) {
				f.y += tileHeight;
			}
		}
	}

	private void update(float deltaTime) {
		if (estado != INICIADO) {
			for (Fileira f : fileiras) {
				f.updateAnim(deltaTime);
			}
			return;
		}

		tempoTotal += deltaTime;
		velAtual = velIni + ((tileHeight*tempoTotal)/8f);

		for (int i = 0; i < fileiras.size; i++) {
			Fileira.DESCIDA retorno = fileiras.get(i).update(deltaTime);
			fileiras.get(i).updateAnim(deltaTime);

			if (retorno != Fileira.DESCIDA.NAO_DESCEU) {
				switch (retorno) {
					case DESCEU_ACERTOU:
						fileiras.removeIndex(i);
						i--;
						idxFileiraInferior--;
						adicionar();
						break;
					case DESCEU_ERROU:
						finalizar(true);
						break;
				}
			}
		}
	}

	private void iniciar() {
		tempoTotal = 0;
		idxFileiraInferior = 0;
		pontos = 0;
		velAtual = 0;
		piano.reset();

		fileiras.clear();
		adicionar(tileHeight);
		for (int i = 1; i <= 4; i++)
			adicionar();

		estado = PARADO;
	}

	private void adicionar() {
		Fileira ultimaFileira = fileiras.get(fileiras.size-1);
		float y = ultimaFileira.y + tileHeight;
		adicionar(y);
	}

	private void adicionar(float y) {
		fileiras.add( new Fileira(y, random.nextInt(4)) );
	}

	private float getTextWidth(BitmapFont font, String text) {
		glyphLayout.reset();
		glyphLayout.setText(font, text);
		return glyphLayout.width;
	}

	public String getTaxaTilesPorSegundo() {
		return String.format("%.2f", velAtual/tileHeight);
	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();
		batch.dispose();
		textureIniciar.dispose();
		piano.dispose();
	}
}
