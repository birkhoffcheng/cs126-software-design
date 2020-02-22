import org.apache.commons.cli.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		String json = parseArgs(args);
		Game game = new Game(json);
		game.startGame();
	}

	private static String parseArgs(String[] args) {
		// Reference https://commons.apache.org/proper/commons-cli/javadocs/api-release/
		Options options = new Options();
		options.addOption("u", "url", true, "URL to JSON file");
		options.addOption("f", "file", true, "path to JSON file");
		options.addOption("h", "help", false, "print this help message");

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine commandLineArgs = null;

		try {
			commandLineArgs = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("amazing-adventures", options);
			System.exit(1);
		}

		if (commandLineArgs.hasOption("help")) {
			formatter.printHelp("amazing-adventures", options);
			System.exit(0);
		}

		String url = commandLineArgs.getOptionValue("url");
		String filePath = commandLineArgs.getOptionValue("file");

		if (filePath != null) {
			return getStringFromFile(filePath);
		} else if (url != null) {
			return getStringFromURL(url);
		} else {
			return Data.getFileContents("src", "test", "test_resources", "default.json");
		}
	}

	private static String getStringFromFile(String filePath) {
		Scanner scanner = null;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			scanner = new Scanner(new File(filePath));
		} catch (FileNotFoundException e) {
			System.out.println(filePath + ": No such file");
			System.exit(1);
		}

		while (scanner.hasNextLine()) {
			stringBuilder.append(scanner.nextLine());
		}

		return stringBuilder.toString();
	}

	private static String getStringFromURL(String urlString) {
		try {
			// Taken from https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html
			URL url = new URL(urlString);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder json = new StringBuilder();
			String inputLine = in.readLine();
			while (inputLine != null) {
				json.append(inputLine);
				inputLine = in.readLine();
			}
			in.close();
			return json.toString();
		} catch (Exception e) {
			System.out.println("Invalid URL: " + urlString);
			System.exit(1);
		}
		return null;
	}
}