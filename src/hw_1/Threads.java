package hw_1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Properties;

import static java.lang.System.exit;

/**
 * Created by Mug on 01.11.2017.
 */
public class Threads {


    public static void main(String args[]) {

        FileInputStream fis;
        Properties properties = new Properties();

        try {
            fis = new FileInputStream("src/hw_1/hw_1.properties");
            properties.load(fis);

        }  catch (java.io.IOException e) {
            System.out.print("Wrong input");
            e.printStackTrace();

        }

        Integer amountResources =
                Integer.parseInt(properties.getProperty("amountResources"));
        Integer time =
                Integer.parseInt(properties.getProperty("time"));

        String log = properties.getProperty("input_file");

        ResourceServer server = new ResourceServer(amountResources, log, time);
        Generator generator = new Generator(server, amountResources);

        server.start();
        generator.start();

    }
}
