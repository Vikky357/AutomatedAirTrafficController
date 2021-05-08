package com.example.Atc;

public class Platform implements  Comparable<Platform>, Runnable {
    String pid;
    double time;
    boolean isFree;
    double inputFlighttime;


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

    public void setInputFlighttime(double inputFlighttime) {
        this.inputFlighttime = inputFlighttime;
    }

    public void run() {
        try {

            Thread.sleep(Math.round(inputFlighttime)*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.isFree = true;
            System.out.println("Runway:"+ this.pid+ " is cleared ");

        }
        return;
    }

}
