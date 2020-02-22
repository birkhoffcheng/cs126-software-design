public class Player {
    private String name;
    private int age;
    private int jerseyNumber;
    private String position;
    private String hometown;

    public Player(String name, int age, int jerseyNumber, String position, String hometown) {
        this.name = name;
        this.age = age;
        this.jerseyNumber = jerseyNumber;
        this.position = position;
        this.hometown = hometown;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public String getPosition() {
        return position;
    }

    public String getHometown() {
        return hometown;
    }
}
