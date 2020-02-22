public class Pass {
    private String passer;
    private String receiver;

    public Pass (String passer, String receiver) {
        this.passer = passer;
        this.receiver = receiver;
    }

    public String getPasser() {
        return passer;
    }

    public String getReceiver() {
        return receiver;
    }
}
