import com.google.gson.Gson;
import java.util.*;
public class Analyze {

    private static Game deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Game.class);
    }

    private static Player getPlayerByName(Game game, String name) {
        if (name == null || game == null) {
            return null;
        }

        Player[] players = game.getHomeTeam().getPlayers();
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }

        players = game.getAwayTeam().getPlayers();
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }

        return null;
    }

    /**
     * @param json JSON string representing a Game object
     * @param name The name of the player
     * @return The age of the player if found, -1 if not found.
     */
    public static int getPlayerAgeByName(String json, String name) {
        Game game = deserialize(json);

        Player p = getPlayerByName(game, name);
        if (p != null) {
            return p.getAge();
        }

        return -1;
    }

    /**
     * @param json JSON string representing a Game object
     * @param timestamp The timestamp to compare against
     * @return An array of Goals scored before the timestamp
     */
    public static Goal[] goalsScoredBefore(String json, int timestamp) {
        Game game = deserialize(json);

        Goal[] allGoals = game.getGoals();
        Goal[] buffer = new Goal[allGoals.length];

        int i = 0;
        for (Goal g : allGoals) {
            if (g.getTimestamp() < timestamp) {
                buffer[i] = g;
                i++;
            }
        }

        Goal[] goalsScoredBeforeTimestamp = new Goal[i];
        for (i = 0; i < goalsScoredBeforeTimestamp.length; i++) {
            goalsScoredBeforeTimestamp[i] = buffer[i];
        }
        return goalsScoredBeforeTimestamp;
    }

    /**
     * @param json JSON string representing a Game object
     * @param name The name of the assist player
     * @return An array of Goals assisted by the player
     */
    public static Goal[] goalsAssistedBy(String json, String name) {
        Game game = deserialize(json);

        Goal[] allGoals = game.getGoals();
        Goal[] buffer = new Goal[allGoals.length];

        int i = 0;
        for (Goal g : allGoals) {
            if (name.equals(g.getAssist())) {
                buffer[i] = g;
                i++;
            }
        }

        Goal[] goalsAssistedByPlayer = new Goal[i];
        for (i = 0; i < goalsAssistedByPlayer.length; i++) {
            goalsAssistedByPlayer[i] = buffer[i];
        }
        return goalsAssistedByPlayer;
    }

    /**
     * @param json JSON string representing a Game object
     * @param place A place
     * @return An array of Players whose hometown is that place
     */
    public static Player[] playersFrom(String json, String place) {
        Game game = deserialize(json);

        Player[] teamPlayers = game.getHomeTeam().getPlayers();
        Player[] buffer = new Player[teamPlayers.length + game.getAwayTeam().getPlayers().length];

        int i = 0;
        for (Player p : teamPlayers) {
            if (p.getHometown().equals(place)) {
                buffer[i] = p;
                i++;
            }
        }

        teamPlayers = game.getAwayTeam().getPlayers();
        for (Player p : teamPlayers) {
            if (p.getHometown().equals(place)) {
                buffer[i] = p;
                i++;
            }
        }

        Player[] playersFromPlace = new Player[i];
        for (i = 0; i < playersFromPlace.length; i++) {
            playersFromPlace[i] = buffer[i];
        }

        return playersFromPlace;
    }

    /**
     * @param json JSON string representing a Game object
     * @return The weighed average jersey number of players who scored
     */
    public static int avgJerseyNumOfScorers(String json) {
        Game game = deserialize(json);
        int sum = 0;

        Goal[] goals = game.getGoals();
        for (Goal g : goals) {
            sum += getPlayerByName(game, g.getScorer()).getJerseyNumber();
        }

        return sum / goals.length;
    }

    /**
     * @param json JSON string representing a Game object
     * @return The Player who scored the most goals
     */
    public static Player getMVP(String json) {
        Game game = deserialize(json);

        Goal[] goals = game.getGoals();
        String[] allScorers = new String[goals.length];

        for (int i = 0; i < goals.length; i++) {
            allScorers[i] = goals[i].getScorer();
        }

        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String str : allScorers) {
            int count = map.getOrDefault(str, 0);
            map.put(str, count + 1);
        }

        int maxScores = 0;
        String mvp = null;

        for (String str : allScorers) {
            int playerScores = map.get(str);
            if (playerScores > maxScores) {
                maxScores = playerScores;
                mvp = str;
            }
        }

        return getPlayerByName(game, mvp);
    }

    /**
     * @param json JSON string representing a Game object
     * @return Average number of passes before a goal is scored
     */
    public static double avgNumberOfPasses(String json) {
        Game game = deserialize(json);
        Goal[] goals = game.getGoals();
        int sum = 0;

        for (Goal g : goals) {
            sum += g.getPasses().length;
        }

        return (double) sum / (double) goals.length;
    }

    /**
     * @param json JSON string representing a Game object
     * @return Average age of all players
     */
    public static double avgAge(String json) {
        Game game = deserialize(json);
        Player[] teamPlayers = game.getHomeTeam().getPlayers();
        int sum = 0;

        for (Player p : teamPlayers) {
            sum += p.getAge();
        }

        teamPlayers = game.getAwayTeam().getPlayers();
        for (Player p : teamPlayers) {
            sum += p.getAge();
        }

        return (double) sum / (double) (teamPlayers.length + game.getHomeTeam().getPlayers().length);
    }
}
