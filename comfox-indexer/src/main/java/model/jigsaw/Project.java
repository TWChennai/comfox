package model.jigsaw;

public class Project {
    private String id;
    private String opportunityId;
    private String name;
    private String type;

    public String getId() {
        return id;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", opportunityId='" + opportunityId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

