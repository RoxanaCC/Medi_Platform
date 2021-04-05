package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.SensorDataDTO;
import ro.tuc.ds2020.entities.SensorData;

public class SensorDataBuilder {

    private SensorDataBuilder() {
    }

    public static SensorDataDTO toSensorDataDTO(SensorData sensorData) {
        return new SensorDataDTO(sensorData.getId(), sensorData.getPatientID(), sensorData.getStart_time(),
                sensorData.getEnd_time(), sensorData.getActivity(), sensorData.isProblem());
    }

    public static SensorData toEntity(SensorDataDTO sensorDataDTO) {
        return new SensorData(sensorDataDTO.getPatientID(), sensorDataDTO.getStart_time(),
                sensorDataDTO.getEnd_time(), sensorDataDTO.getActivity(), sensorDataDTO.isProblem());
    }
}
