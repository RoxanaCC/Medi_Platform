package ro.tuc.ds2020;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.dtos.PatientDetailsDTO;
import ro.tuc.ds2020.dtos.SensorDataDTO;
import ro.tuc.ds2020.services.PatientService;
import ro.tuc.ds2020.services.SensorDataService;

import java.util.UUID;


@Component
public class Listener {

    private SensorDataService sensorDataService;
    private SimpMessagingTemplate simpMessagingTemplate;
    private PatientService patientService;

    public Listener(SensorDataService sensorDataService, SimpMessagingTemplate simpMessagingTemplate, PatientService patientService) {
        this.sensorDataService = sensorDataService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.patientService = patientService;
    }

    @RabbitListener(queues = "queue")
    public void receiveMessage(String sensorData){
        JsonObject jsonObject = new JsonParser().parse(sensorData).getAsJsonObject();
        Gson gson = new Gson();
        SensorDataDTO data = gson.fromJson(jsonObject, SensorDataDTO.class);
        data.setProblem(false);
        System.out.println(data.getActivity());
        PatientDetailsDTO patient = patientService.findPatientById(UUID.fromString(data.getPatientID()));

        if(data.getActivity().equals("Sleeping") && (data.getEnd_time() - data.getStart_time() > 25200000)){
            data.setProblem(true);
            simpMessagingTemplate.convertAndSend("/message/notification", "Patient" +
                    patient.getName() + "has spent to much time sleeping");
        }
        if(data.getActivity().equals("Leaving") && (data.getEnd_time() - data.getStart_time() > 18000000)){
            data.setProblem(true);
            simpMessagingTemplate.convertAndSend("/message/notification", "Patient" +
                    patient.getName() + "has spent to much time outside");
        }

        if(data.getActivity().equals("Toileting") && (data.getEnd_time() - data.getStart_time() > 1800000)){
            data.setProblem(true);
            simpMessagingTemplate.convertAndSend("/message/notification", "Patient" +
                    patient.getName() + "has spent to much time in the bathroom");
        }
        if(data.getActivity().equals("Showering") && (data.getEnd_time() - data.getStart_time() > 1800000)){
            data.setProblem(true);
            simpMessagingTemplate.convertAndSend("/message/notification", "Patient" +
                    patient.getName() + "has spent to much time in the bathroom");
        }
        if(data.getActivity().equals("Grooming") && (data.getEnd_time() - data.getStart_time() > 1800000)){
            data.setProblem(true);
            simpMessagingTemplate.convertAndSend("/message/notification", "Patient" +
                    patient.getName() + "has spent to much time in the bathroom");
        }

        sensorDataService.insert(data);
    }
}
