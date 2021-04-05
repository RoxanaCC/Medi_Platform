package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.MedicationDTO;
import ro.tuc.ds2020.entities.Medication;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MedicationBuilder {

    MedicationBuilder() {
    }

    public static MedicationDTO toMedicationDTO(Medication med) {
        return new MedicationDTO(med.getId(), med.getName(), med.getSide_effects(), med.getDosage());
    }

    public static Medication toEntity(MedicationDTO medDTO) {
        return new Medication(medDTO.getName(), medDTO.getSide_effects(), medDTO.getDosage());
    }

    public static List<MedicationDTO> toMedicationDTO(List<Medication> meds) {
        ListIterator<Medication> iterator = meds.listIterator();
        List<MedicationDTO> medsDTO = new ArrayList<>();
        while (iterator.hasNext()){
            Medication med = iterator.next();
            medsDTO.add(toMedicationDTO(med));
        }
        return medsDTO;
    }

    public static List<Medication> toEntity(List<MedicationDTO> medsDTO) {
        ListIterator<MedicationDTO> iterator = medsDTO.listIterator();
        List<Medication> meds = new ArrayList<>();
        while (iterator.hasNext()){
            MedicationDTO med = iterator.next();
            meds.add(toEntity(med));
        }
        return meds;
    }
}
