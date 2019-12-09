package model.json;

import model.Consultant;
import model.vertex.Project;
import model.Duration;

public class AssignmentJson {
    public String id;
    public Consultant consultant;
    public StaffingRequest staffingRequest;
    public String effort;
    public String shadow;
    public Account account;
    public Project project;
    public Duration duration;

    public class StaffingRequest{
        public String id;
    }

    public class Account{
        public String name;
    }
}
