package T3;

public class Match{
    double match;
	String title;
	int ntable;

	// Constructors, getters etc.
	public Match() {
		this.match = 0;
		this.title = "";
		this.ntable = 0;
	}

	public Match(double match, String title, int ntable) {
		this.match = match;
		this.title = title;
		this.ntable = ntable;
	}

	public double getMatch() {
		return match;
	}

	public String getTitle() {
		return title;
	}

	public int getTable() {
		return ntable;
	}

	public void setMatch(double match) {
		this.match = match;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTable(int ntable) {
		this.ntable = ntable;
	}
}