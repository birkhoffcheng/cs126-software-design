public class Game {
    private Team homeTeam;
    private Team awayTeam;
    private String winner;
    private Goal[] goals;

    public Game(Team homeTeam, Team awayTeam, String winner, Goal[] goals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.winner = winner;
        this.goals = goals;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public String getWinner() {
        return winner;
    }

    public Goal[] getGoals() {
        return goals;
    }
}
