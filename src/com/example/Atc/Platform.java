package com.example.Atc;

public class Platform implements  Comparable<Platform> {
    String pid;
    double time;
    boolean isFree;

    Platform(String pid, double time) {
        this.pid = pid;
        this.time = time;
        this.isFree = true;
    }

    public int compareTo(Platform obj) {
        return pid.compareTo(obj.getPid());
    }
    public double getPTime() {
        return time;
    }

    public boolean getisFree() {
        return isFree;
    }

    public void setisFree(boolean status) {
        this.isFree = status;
    }

    public String getPid() {
        return this.pid;
    }

    public String toString() {
        return pid+ " "+ time+ " "+ isFree;
    }
}
