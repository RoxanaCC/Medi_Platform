package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.MedPlanDetailsDTO;
import ro.tuc.ds2020.entities.MedPlan;
import ro.tuc.ds2020.entities.Medication;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MedPlanBuilder {

    MedPlanBuilder() {
    }

    public static MedPlanDetailsDTO toMedPlanDTO(MedPlan medPlan) {
        StringBuilder sb = new StringBuilder();
        if(medPlan.getMeds().isEmpty()){
            sb.append(" ");
        }
        else {
            ListIterator<Medication> iterator = medPlan.getMeds().listIterator();
            while (iterator.hasNext()) {
                Medication med = iterator.next();
                sb.append(med.getDosage());
                sb.append(" * ");
                sb.append(med.getName());
                sb.append("; ");
            }
        }
        return new MedPlanDetailsDTO(medPlan.getId(), medPlan.getIntake_intervals(),
                medPlan.getStart_date(), medPlan.getEnd_date(), sb.toString());
    }

    public static MedPlan toEntity(MedPlanDetailsDTO medPlanDTO) {
        return new MedPlan(medPlanDTO.getIntake_intervals(),
                medPlanDTO.getStart_date(), medPlanDTO.getEnd_date(), new ArrayList<Medication>());
    }

    public static List<MedPlanDetailsDTO> toMedPlanDTO(List<MedPlan> medPlans) {
        List<MedPlanDetailsDTO> medPlansDTO = new ArrayList<>();
        if(medPlans.isEmpty()){
            return medPlansDTO;
        }
        ListIterator<MedPlan> iterator = medPlans.listIterator();
        while (iterator.hasNext()){
            MedPlan medPLan = iterator.next();
            medPlansDTO.add(toMedPlanDTO(medPLan));
        }
        return medPlansDTO;
    }

    public static List<MedPlan> toEntity(List<MedPlanDetailsDTO> medPlansDTO) {
        ListIterator<MedPlanDetailsDTO> iterator = medPlansDTO.listIterator();
        List<MedPlan> medPlans = new ArrayList<>();
        while (iterator.hasNext()){
            MedPlanDetailsDTO medPlan = iterator.next();
            medPlans.add(toEntity(medPlan));
        }
        return medPlans;
    }

}
