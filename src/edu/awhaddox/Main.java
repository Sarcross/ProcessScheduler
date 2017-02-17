package edu.awhaddox;

public class Main {

    public static void main(String[] args) throws Exception{
        Scheduler sch = Scheduler.getInstance();
        sch.populateProcessList();
        sch.startScheduling();

        Scheduler.flushInstance();
        sch = Scheduler.getInstance();
        sch.setMode(Scheduler.Mode.SJF);
        sch.populateProcessList();
        sch.startScheduling();

        Scheduler.flushInstance();
        sch = Scheduler.getInstance();
        sch.setMode(Scheduler.Mode.RR, 2);
        sch.populateProcessList();
        sch.startScheduling();

        Scheduler.flushInstance();
        sch = Scheduler.getInstance();
        sch.setMode(Scheduler.Mode.RR, 5);
        sch.populateProcessList();
        sch.startScheduling();
    }
}
