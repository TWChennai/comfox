package model.jigsaw;

public class Duration {
    private String startsOn;
    private String endsOn;

    public String getStartsOn() {
        return startsOn;
    }

    public String getEndsOn() {
        return endsOn;
    }

    @Override
    public String toString() {
        return "Duration{" +
                "startsOn='" + startsOn + '\'' +
                ", endsOn='" + endsOn + '\'' +
                '}';
    }
}
