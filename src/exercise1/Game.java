package exercise1;

public class Game {
	private final String gameID;
	private final String title;
	
	public Game(String gameID, String title) {
		this.gameID = gameID;
		this.title = title;
	}

	public String getGameID() {
		return gameID;
	}

	public String getTitle() {
		return title;
	}
}
