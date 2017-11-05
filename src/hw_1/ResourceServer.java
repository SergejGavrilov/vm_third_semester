package hw_1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Mug on 01.11.2017.
 */
public class ResourceServer extends Thread {
    private int curRes;
    private int time;
    private PrintWriter writer;
    private String TAG = "Server: ";

    public ResourceServer(int curRes, String log, int time)
    {
        this.curRes = curRes;
        this.time = time;

        try {
            writer = new PrintWriter(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public synchronized boolean getResources(int amount) {
        if (amount < curRes) {
            curRes -= amount;
            writer.println(TAG + "gave " + amount + " resources");
            writeToLog();
            return true;
        } else {
            writer.println(TAG + "couldn't give resources" +
                        "|number of requested resources " + amount);
            return false;
        }
    }

    //Writes current time and number of available resources
    private synchronized void writeToLog() {
        writer.println(TAG + "current time: " + System.currentTimeMillis()
                        + "|number of available resources: " + curRes);
    }

    public synchronized void returnResources(int amount) {
        curRes += amount;
        writer.println(TAG + "returned " + amount + " resources");
        writeToLog();
    }

    public void stop(Thread t) {
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        writer.println(TAG + "started");
        writeToLog();

        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        writer.println(TAG + "finished");
        writeToLog();
        writer.close();
    }
}


