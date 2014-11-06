package com.prockup.game.thatsnuts;

public class GameManager {

	private static GameManager INSTANCE;
	
	private final int INITIAL_SCORE = 0;
	private int mnScore;
	
	public GameManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static GameManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new GameManager();
		}
		return  INSTANCE;
	}
	
	public int getScore() {
		return mnScore;
	}
	
	public void incrementScore() {
		mnScore++;
	}
	
	public void reset() {
		mnScore = INITIAL_SCORE;
	}

}
