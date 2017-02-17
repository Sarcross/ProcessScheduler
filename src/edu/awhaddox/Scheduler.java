package edu.awhaddox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public final class Scheduler {
    private static Scheduler instance = null;

    private Mode mode;
    private Process[] processList;
    private long waitTime;
    private long activationTime;
    private long timer;
    private int processCount;

    enum Mode
    {
        FCFS,
        SJF,
        RR
    }

    protected Scheduler() {
        processList = new Process[30];
        mode = Mode.FCFS;
        waitTime = 1;
        activationTime = 0;
        timer = 0;
        processCount = 0;
    }

    public static Scheduler getInstance() {
        if(instance == null)
            instance = new Scheduler();
        return instance;
    }

    public static void flushInstance() throws Exception {
        instance = new Scheduler();
    }

    public void populateProcessList() {
        try
        {
            Scanner fileScanner = new Scanner(new File("job.txt"));
            Process temp;
            int counter = 0;
            while(fileScanner.hasNext()) {
                temp = new Process(fileScanner.nextLine(), Long.parseLong(fileScanner.nextLine()));
                processList[counter++] = temp;
                processCount++;

            }
            fileScanner.close();
        }
        catch(FileNotFoundException fne)
        {
            System.out.println("File does not exist.");
        }
    }

    public void setMode(Mode m) throws Exception {
        if(processList[0] != null)
            throw new Exception();
        if(m == Mode.RR)
            throw new Exception();
        mode = m;
    }

    public void setMode(Mode m, long wait) throws Exception {
        if(processList[0] != null)
            throw new Exception();
        if(m == Mode.FCFS) {
            setMode(m);
            return;
        }
        if(m == Mode.SJF) {
            setMode(m);
            return;
        }
        if(wait <= 0) {
            throw new Exception();
        }
        mode = m;
        waitTime = wait;
    }

    public void startScheduling() throws Exception{
        if(activationTime != 0)
            throw new Exception();
        switch(mode)
        {
            case FCFS:
                FCFS();
                break;
            case SJF:
                SJF();
                break;
            case RR:
                RR();
                break;
        }
    }

    private void FCFS() {
        activationTime = System.currentTimeMillis();
        int ndx = 0;
        Process currentProcess = processList[ndx];
        while(currentProcess != null) {
            if(!currentProcess.finished()) {
                currentProcess.setTimeAdded(timer);
                timer += currentProcess.getDuration();
                currentProcess.setElapsed(currentProcess.getDuration());
                System.out.println(currentProcess);
            }

            currentProcess = processList[++ndx];
        }

        System.out.println(this);
    }

    private void SJF() {
        activationTime = System.currentTimeMillis();
        Arrays.sort(processList, 0, processCount);
        int ndx = 0;
        Process currentProcess = processList[ndx];
        while(currentProcess != null) {
            if(!currentProcess.finished()) {
                currentProcess.setTimeAdded(timer);
                timer += currentProcess.getDuration();
                currentProcess.setElapsed(currentProcess.getDuration());
                System.out.println(currentProcess);
            }

            currentProcess = processList[++ndx];
        }

        System.out.println(this);
    }

    private void RR() {
        activationTime = System.currentTimeMillis();
        int ndx = 0;
        int finishedProcesses = 0;
        long remainingTime = 0;
        Process currentProcess = processList[ndx];
        while(finishedProcesses < processCount) {
            if(currentProcess != null) {
                if (!currentProcess.finished()) {
                    currentProcess.setTimeAdded(timer);
                    remainingTime = currentProcess.getDuration() - currentProcess.getElapsed();
                    if(remainingTime < waitTime) {
                        currentProcess.setElapsed(currentProcess.getElapsed() + remainingTime);
                        timer += remainingTime;
                    }
                    else {
                        timer += waitTime;
                        currentProcess.setElapsed(currentProcess.getElapsed() + waitTime);
                    }

                    if(currentProcess.finished())
                        finishedProcesses++;
                    System.out.println(currentProcess);
                }
            }

            currentProcess = processList[++ndx];
            if(ndx > processCount) {
                ndx = 0;
                currentProcess = processList[ndx];
            }
        }

        System.out.println(this);

    }

    public String toString() {
        if(mode == Mode.RR)
            return "Scheduler Timer: " + timer + "\nMode: " + mode.name() + " ( " + waitTime + " )" + "\nProcess Count: " + processCount + "\n";
        return "Scheduler Timer: " + timer + "\nMode: " + mode.name() + "\nProcess Count: " + processCount + "\n";
    }
}
