package Components;

import android.media.MediaPlayer;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MusicState {

    public static MusicState musicObject;
    private static boolean isPlaying = false;

    public MediaPlayer mediaPlayer;

    public static MusicState getMusicObject(){
        if(musicObject == null){
            musicObject = new MusicState();
        }
        return musicObject;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer){
        this.mediaPlayer = mediaPlayer;
        this.isPlaying = true;
    }

    /**
     * Si la musica esta encendida, la apaga, y viceversa
     * @return true si la musica esta encendida, false si esta apagada
     */
    public boolean changeMusic() {
        if (isPlaying) {
            // Apaga la musica
            isPlaying = false;
            mediaPlayer.pause();
        } else {
            // Prende la musica
            isPlaying = true;
            mediaPlayer.start();
        }
        return isPlaying;
    }

    public static boolean isPlaying(){
        return isPlaying;
    }
}
