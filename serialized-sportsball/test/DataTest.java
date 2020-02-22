import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataTest {

    String gameData;
    Gson gson;

    @Before
    public void before() {
        gameData = Data.getFileContents("test_resources", "sportsball.json");
        gson = new Gson();
    }

    @Test
    public void canReadFileFromFolder() throws Exception {
        String testFileContents = Data.getFileContents("test_resources", "test_file_contents.txt");
        assertEquals("Gone, reduced to atoms", testFileContents);
    }

    @Test
    public void testGetPlayerAgeByName() throws Exception {
        assertEquals(21, Analyze.getPlayerAgeByName(gameData, "William Glass"));
        assertEquals(30, Analyze.getPlayerAgeByName(gameData, "Stephen Bormann"));
    }

    @Test
    public void testGoalsScoredBefore() throws Exception {
        Pass[] pass0 = {
                new Pass("John Yeiser", "Tina Ocampo"),
                new Pass("Tina Ocampo", "Steven White")
        };
        Pass[] pass1 = {
                new Pass("Stephen Bormann", "Malcom Patrick"),
                new Pass("Malcom Patrick", "Mary Kohnz"),
                new Pass("Mary Kohnz", "Tina Ocampo"),
                new Pass("Tina Ocampo", "Malcom Patrick")
        };
        Goal[] expected = {new Goal("Urbana International", 10, "Steven White", null, pass0), new Goal("Urbana International", 11, "Malcom Patrick", null, pass1)};
        assertEquals(gson.toJson(expected, Goal[].class), gson.toJson(Analyze.goalsScoredBefore(gameData, 12), Goal[].class));
    }

    @Test
    public void testGoalAssistedBy() throws Exception {
        String expectedJson = "[{\"team\":\"Champaign United\",\"timestamp\":24,\"scorer\":\"Dale Rose\",\"assist\":\"Guadalupe Nelson\",\"passes\":[{\"passer\":\"Dale Rose\",\"receiver\":\"Guadalupe Nelson\"},{\"passer\":\"Guadalupe Nelson\",\"receiver\":\"Dale Rose\"},{\"passer\":\"Dale Rose\",\"receiver\":\"Guadalupe Nelson\"},{\"passer\":\"Guadalupe Nelson\",\"receiver\":\"Dale Rose\"},{\"passer\":\"Dale Rose\",\"receiver\":\"Guadalupe Nelson\"},{\"passer\":\"Guadalupe Nelson\",\"receiver\":\"Dale Rose\"}]},{\"team\":\"Champaign United\",\"timestamp\":27,\"scorer\":\"William Glass\",\"assist\":\"Guadalupe Nelson\",\"passes\":[{\"passer\":\"Guadalupe Nelson\",\"receiver\":\"Walter Grulkey\"},{\"passer\":\"Walter Grulkey\",\"receiver\":\"William Glass\"},{\"passer\":\"William Glass\",\"receiver\":\"Guadalupe Nelson\"},{\"passer\":\"Guadalupe Nelson\",\"receiver\":\"Walter Grulkey\"},{\"passer\":\"Walter Grulkey\",\"receiver\":\"Guadalupe Nelson\"},{\"passer\":\"Guadalupe Nelson\",\"receiver\":\"William Glass\"}]},{\"team\":\"Champaign United\",\"timestamp\":58,\"scorer\":\"Dale Rose\",\"assist\":\"Guadalupe Nelson\",\"passes\":[{\"passer\":\"Dale Rose\",\"receiver\":\"Guadalupe Nelson\"},{\"passer\":\"Guadalupe Nelson\",\"receiver\":\"Patricia Jones\"},{\"passer\":\"Patricia Jones\",\"receiver\":\"Dale Rose\"},{\"passer\":\"Dale Rose\",\"receiver\":\"Patricia Jones\"},{\"passer\":\"Patricia Jones\",\"receiver\":\"Guadalupe Nelson\"},{\"passer\":\"Guadalupe Nelson\",\"receiver\":\"Dale Rose\"}]}]";
        assertEquals(expectedJson, gson.toJson(Analyze.goalsAssistedBy(gameData, "Guadalupe Nelson")));
    }

    @Test
    public void testPlayersFrom() throws Exception {
        String expectedJson = "[{\"name\":\"Mercedes Jackson\",\"age\":24,\"jerseyNumber\":13,\"position\":\"Midfielder\",\"hometown\":\"Urbana\"},{\"name\":\"Dale Rose\",\"age\":26,\"jerseyNumber\":18,\"position\":\"Midfielder\",\"hometown\":\"Urbana\"},{\"name\":\"Mary Kohnz\",\"age\":21,\"jerseyNumber\":9,\"position\":\"Midfielder\",\"hometown\":\"Urbana\"}]";
        assertEquals(expectedJson, gson.toJson(Analyze.playersFrom(gameData, "Urbana")));
    }

    @Test
    public void testAvgJerseyNumOfScorer() throws Exception {
        assertEquals(13, Analyze.avgJerseyNumOfScorers(gameData));
    }

    @Test
    public void testGetMVP() throws Exception {
        assertEquals("{\"name\":\"Dale Rose\",\"age\":26,\"jerseyNumber\":18,\"position\":\"Midfielder\",\"hometown\":\"Urbana\"}", gson.toJson(Analyze.getMVP(gameData), Player.class));
    }

    @Test
    public void testAvgNumberOfPasses() throws Exception {
        assertEquals((double) 82 / (double) 19, Analyze.avgNumberOfPasses(gameData), 0.0001);
    }

    @Test
    public void testAvgAge() throws Exception {
        assertEquals((double) 532 / (double) 22, Analyze.avgAge(gameData), 0.0001);
    }
}
