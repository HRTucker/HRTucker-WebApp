package Chess.Game;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessTimer {
	
	private static final int DELAY = 1000; // milliseconds
    private int secondsRemaining;
    public Player player;
    
    private Timer timer;	
	
	public ChessTimer(int seconds, Player player) {
		
		this.secondsRemaining = seconds;
		this.player = player;
	}

	public void start() {
		this.timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (secondsRemaining <= 0) {
                    timer.cancel();
                    
                }else {
                	secondsRemaining--;            	
                	//System.out.println(secondsRemaining-- + " seconds remaining");
                }
            }
        }, 0, DELAY);
	}
	
	public void pause() {
		timer.cancel();
	}
	
	public void stop() {
		timer.cancel();
	}
	
	public int getSecondsRemaining() {
		return this.secondsRemaining;
	}
}
