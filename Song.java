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
            File path1 = new File(introPath);
            AudioInputStream audioInput1 = AudioSystem.getAudioInputStream(path1);
            intro = AudioSystem.getClip();
            intro.open(audioInput1);

            // Initialize the loop
            File path2 = new File(loopPath);
            AudioInputStream audioInput2 = AudioSystem.getAudioInputStream(path2);
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
    public void loop() {
        if (introFinished || intro.isRunning()) {
            return;
        }
		introFinished = true;
        loop.setFramePosition(0);
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
