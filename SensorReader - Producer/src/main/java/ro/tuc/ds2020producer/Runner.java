package ro.tuc.ds2020producer;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.*;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void run(String... args) throws Exception {

        List<String> lines = new ArrayList<String>();

        try {
            File file = new File("activity.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        lines.forEach(line -> {
            String[] data = line.split("\t\t");
            LocalDateTime startDate = LocalDateTime.parse(data[0],
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") );
            long startM = startDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            LocalDateTime endDate = LocalDateTime.parse(data[1],
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") );
            long endM = endDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("patientID", "b26de1ba-9856-4f01-9418-7d9486794727");
            jsonObject.addProperty("start_time", startM);
            jsonObject.addProperty("end_time", endM);
            jsonObject.addProperty("activity", data[2].replaceAll("\\s", ""));

            System.out.println(jsonObject);
            rabbitTemplate.convertAndSend(DS2020ProducerAplication.topicExchangeName, "sensor", jsonObject.toString());
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

        });
    }
}
