package edu.awhaddox;

public class Process implements Comparable<Process> {
    private String name;
    private long duration;
    private long elapsed;
    private long timeAdded;

    public Process(String name, long dur) {
        this.name = name;
        this.duration = dur;
        this.elapsed = 0;
        this.timeAdded = System.currentTimeMillis();
    }

    public Process(Process p) {
        this.name = p.name;
        this.duration = p.duration;
        this.elapsed = p.elapsed;
        this.timeAdded = p.timeAdded;
    }

    public boolean finished() {
        return elapsed == duration;
    }

    public int compareTo(Process p) {
        if(p == null)
            return (int) duration;
        if(duration < p.duration)
            return (int) -duration;
        if(duration > p.duration)
            return (int) p.duration;
        return 0;//(int) duration;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(long timeAdded) {
        this.timeAdded = timeAdded;
    }

    public long getElapsed() {

        return elapsed;
    }

    public void setElapsed(long elapsed) {
        if(elapsed > this.duration)
            this.elapsed = duration;
        else
            this.elapsed = elapsed;
    }

    public long getDuration() {

        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String toString() {
        if(finished())
            return ">>>Name: " + name + " | Duration: " + duration + " | Elapsed: " + elapsed + " | Remaining: " + (duration - elapsed) +" | Time Added: " + timeAdded;
        return "Name: " + name + " | Duration: " + duration + " | Elapsed: " + elapsed + " | Remaining: " + (duration - elapsed) +" | Time Added: " + timeAdded;
    }
}
