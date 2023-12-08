package com.example.fitnessapp;

import java.io.Serializable;

public class Workout implements Serializable {
    private int id;
    private String name;
    private String duration;
    private String instructions;
    private String videoUrl;

    // Constructor
    public Workout(int id, String name, String duration, String instructions, String videoUrl) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.instructions = instructions;
        this.videoUrl = videoUrl;
    }

    // Getters and setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    // Additional methods as needed
}
