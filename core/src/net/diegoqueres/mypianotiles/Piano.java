package net.diegoqueres.mypianotiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;


public class Piano {

    private Map<String, Sound> sounds;

    private Array<String> notas;

    private int indice;

    public Piano(String musica) {
        sounds = new HashMap<String, Sound>();
        indice = 0;

        carregarNotasMusicais(musica);
    }

    private void carregarNotasMusicais(String musica) {
        FileHandle file = Gdx.files.internal(musica + ".txt");
        String texto = file.readString();
        notas = new Array<>(texto.split(" "));
        Gdx.app.log("Log", notas.toString());

        for (String s : notas) {
            if (!sounds.containsKey(s)) {
                sounds.put(s, Gdx.audio.newSound(Gdx.files.internal("sons/" + s + ".wav")));
            }
        }
    }

    public void tocar() {
        String notaMusical = notas.get(indice);
        sounds.get(notaMusical).play(.5f);
        avancarIndice();
    }

    private void avancarIndice() {
        indice++;
        if (indice == notas.size)
            reset();
    }

    public void reset() {
        indice = 0;
    }

    public void dispose() {
        for (String key : sounds.keySet()) {
            sounds.get(key).dispose();
        }
    }

}
