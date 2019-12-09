package model.edge;

import model.Consultant;
import model.Duration;
import model.vertex.Project;

public class Assignment {
    private String assignmentId;
    private Consultant consultant;
    private String staffingRequest;
    private String effort;
    private String shadow;
    private String accountName;
    private Project project;
    private Duration duration;

    public Assignment(String assignmentId, Consultant consultant, String staffingRequest, String effort,
                      String shadow, String accountName, Project project, Duration duration){
        this.assignmentId = assignmentId;
        this.consultant = consultant;
        this.staffingRequest = staffingRequest;
        this.effort = effort;
        this.shadow = shadow;
        this.accountName = accountName;
        this.project = project;
        this.duration = duration;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public Project getProject() {
        return project;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getShadow() {
        return shadow;
    }

    public String getEffort() {
        return effort;
    }

    public String getStaffingRequest() {
        return staffingRequest;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public Duration getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId='" + assignmentId + '\'' +
                ", consultant=" + consultant +
                ", staffingRequest='" + staffingRequest + '\'' +
                ", effort='" + effort + '\'' +
                ", shadow='" + shadow + '\'' +
                ", accountName='" + accountName + '\'' +
                ", project=" + project +
                ", duration=" + duration +
                '}';
    }
}
