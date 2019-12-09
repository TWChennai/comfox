package model.jigsaw;


public class Assignment {
    private String id;
    private Consultant consultant;
    private StaffingRequest staffingRequest;
    private String effort;
    private String shadow;
    private Account account;
    private Project project;
    private Duration duration;

    public Assignment(String id, Consultant consultant, StaffingRequest staffingRequest, String effort, String shadow, Account account, Project project, Duration duration) {
        this.id = id;
        this.consultant = consultant;
        this.staffingRequest = staffingRequest;
        this.effort = effort;
        this.shadow = shadow;
        this.account = account;
        this.project = project;
        this.duration = duration;

    }

    public String getId() {
        return id;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getShadow() {
        return shadow;
    }

    public String getEffort() {
        return effort;
    }

    public String getStaffingRequest() {
        return staffingRequest.getId();
    }

    public Project getProject() {
        return project;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public String getAccount() {
        return account.getName();
    }

    private class StaffingRequest {
        private String id;

        public String getId() {
            return id;
        }
    }

    private class Account {
        private String name;

        public String getName() {
            return name;
        }
    }
}
