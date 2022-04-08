package src.main.java;

import java.io.*;
import javax.sound.sampled.*;

public class Song {
    private Clip intro;
    private Clip loop;
    private boolean introFinished = false;

    // Create a song with a given intro audio file and a loop audio file
    public Song(String introPath, String loopPath) {
        try {
            // Initialize the intro
            InputStream path1 = getClass().getClassLoader().getResourceAsStream(introPath);
            AudioInputStream audioInput1 = AudioSystem.getAudioInputStream(new BufferedInputStream(path1));
            intro = AudioSystem.getClip();
            intro.open(audioInput1);

            // Initialize the loop
            InputStream path2 = getClass().getClassLoader().getResourceAsStream(loopPath);
            AudioInputStream audioInput2 = AudioSystem.getAudioInputStream(new BufferedInputStream(path2));
            loop = AudioSystem.getClip();
            loop.open(audioInput2);
        }
        catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    // Play the intro
    public void playIntro() {
        intro.setFramePosition(0);
        intro.start();
    }
    
    // Start looping the song and finish the intro
    public void loop(long loopSample) {
        if (introFinished || intro.getLongFramePosition() < loopSample) {
            return;
        }
		introFinished = true;
        loop.setFramePosition((int) (intro.getLongFramePosition() - loopSample));
        loop.start();
        loop.loop(Clip.LOOP_CONTINUOUSLY);
        intro.stop();
        intro.close();
    }
    
    // Stop the loop from running
    public void stopMusic() {
        if (loop.isRunning()) {
            loop.stop();
            loop.close();
        }
    }
}
