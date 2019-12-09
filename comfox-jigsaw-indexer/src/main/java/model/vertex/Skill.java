package model.vertex;

import model.SkillGroup;

public class Skill {
    private String name;
    private SkillGroup group;
    public Skill(String name, SkillGroup group){
        this.name = name;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public SkillGroup getGroup() {
        return group;
    }

}
