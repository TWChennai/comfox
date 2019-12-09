package model.jigsaw;

public class TravelPreferences {
    private Boolean domestic;
    private Boolean international;
    private String travelDetails;

    public TravelPreferences(Boolean domestic, Boolean international, String travelDetails) {
        this.domestic = domestic;
        this.international = international;
        this.travelDetails = travelDetails;
    }

    public Boolean getDomestic() {
        return domestic;
    }

    public Boolean getInternational() {
        return international;
    }

    public String getTravelDetails() {
        return travelDetails;
    }

    @Override
    public String toString() {
        return "TravelPreferences{" +
                "domestic=" + domestic +
                ", international=" + international +
                ", travelDetails='" + travelDetails + '\'' +
                '}';
    }
}