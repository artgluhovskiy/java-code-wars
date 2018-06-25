package org.art.projects.java_code_wars.entities;

/**
 * This enum contains the level of difficulty for users and java tasks
 */
public enum DifficultyGroup {

    BEGINNER ("BEGINNER"),
    EXPERIENCED ("EXPERIENCED"),
    EXPERT ("EXPERT");

    String difGroup;

    DifficultyGroup(String difGroup) {
        this.difGroup = difGroup;
    }
}
