package model.edge;

import model.vertex.Skill;

public class SkillRating {
    private Skill skill;
    private String rating;

    public SkillRating(Skill skill, String rating) {
        this.skill = skill;
        this.rating = rating;
    }

    public Skill getSkill() {
        return skill;
    }

    public String getRating() {
        return rating;
    }

}
