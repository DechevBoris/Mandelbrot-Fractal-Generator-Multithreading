package com.boris.dechev;

public class Timer {

    private long startingTime;


    public Timer() {
        this.startingTime = -1;
    }

    public void start(){
        this.startingTime = System.currentTimeMillis();
    }

    public float stop(){
        if(this.startingTime != -1){
            return System.currentTimeMillis() - startingTime;
        }
        else {
            throw new IllegalStateException("Timer is not started yet!");
        }
    }
}
