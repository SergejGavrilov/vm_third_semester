package hw_1;

import java.util.Random;

/**
 * Created by Mug on 01.11.2017.
 */
public class Generator extends Thread{

    private int maxSleep = 50;
    private int maxResources = 40;
    private final ResourceServer server;


    public Generator(ResourceServer server, int maxResources) {
        this.server = server;
        this.maxResources = maxResources;
    }
    public void run() {
        super.run();

        Random random = new Random();

        while (server.isAlive()) {
            Client client = new Client();
            client.start();

            try {
                Thread.sleep(random.nextInt(maxSleep));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
       // server.stop(Thread.currentThread());
        Thread.getAllStackTraces()
                .keySet()
                .stream()
                .filter(t ->
                        t.getName().startsWith("client") && t.isAlive())
                .forEach(t -> {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });


    }

    private class Client extends Thread {

        public Client() {
            super("client");
        }

        @Override
        public void run() {
            super.run();

            Random random = new Random();
            int numOfRes = random.nextInt(maxResources);

            if (server.getResources(numOfRes)) {

                try {
                    sleep(random.nextInt(maxSleep));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                server.returnResources(numOfRes);
            }
        }
    }
}



