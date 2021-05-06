package com.example.Atc;

public class Flight {
    String name;
    double weight;
    double time;


    Flight(String name, double weight, double time) {
        this.name = name;
        this.weight = weight;
        this.time = time;
    }

    public String getFName() {
        return name;
    }
    public double getFWeight() {
        return weight;
    }

    public double getFTime() {
        return time;
    }

    public String toString() {

        return name+ " "+ weight+ " "+ time;

    }
}
