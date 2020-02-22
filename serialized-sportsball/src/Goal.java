public class Goal {
    private String team;
    private int timestamp;
    private String scorer;
    private String assist;
    private Pass[] passes;

    public Goal(String team, int timestamp, String scorer, String assist, Pass[] passes) {
        this.team = team;
        this.timestamp = timestamp;
        this.scorer = scorer;
        this.assist = assist;
        this.passes = passes;
    }

    public String getTeam() {
        return team;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getScorer() {
        return scorer;
    }

    public String getAssist() {
        return assist;
    }

    public Pass[] getPasses() {
        return passes;
    }
}
