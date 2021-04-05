package ro.tuc.ds2020.services;

import ro.tuc.ds2020.entities.Intakes;
import ro.tuc.ds2020.entities.MedPlan;
import ro.tuc.ds2020.entities.Medication;
import ro.tuc.ds2020.entities.Patient;
import ro.tuc.ds2020.repositories.IntakesRepository;
import ro.tuc.ds2020.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MedPlanImplement implements MedPlanInterface{

    PatientRepository parientRepository;
    IntakesRepository intakesRepository;

    public MedPlanImplement(PatientRepository parientRepository, IntakesRepository intakesRepository) {
        this.parientRepository = parientRepository;
        this.intakesRepository = intakesRepository;
    }

    @Override
    public void start(String s) {
        System.out.println(s);
    }

    @Override
    public List<String> getPlans() {
        List<Patient> patientList = parientRepository.findAll();
        List<String> intakes = new ArrayList<String>();
        for (Patient p : patientList) {
            List<MedPlan> plans = p.getMedPlans();
            if (plans.size() != 0) {
                String user = p.getPerson().getUsername();
                String pass = p.getPerson().getPassword();
                for (MedPlan pl : plans) {
                    String start = pl.getStart_date();
                    String end = pl.getEnd_date();
                    String inter = pl.getIntake_intervals();
                    try {
                        Date dateStart = new SimpleDateFormat("dd.MM.yyyy").parse(start);
                        Date dateEnd = new SimpleDateFormat("dd.MM.yyyy").parse(end);
                        Date current= new Date(System.currentTimeMillis());
                        if(dateStart.compareTo(current) <= 0 && current.compareTo(dateEnd) <= 0) {
                            for (Medication m : pl.getMeds()) {
                                String s = user + "\t\t" + pass + "\t\t" + start + "\t\t" + end +
                                        "\t\t" + inter + "\t\t" + m.getDosage().toString() + "*" + m.getName();
                                intakes.add(s);
                            }
                        }
                    }catch(Exception e){
                        System.out.println("Wrong format!");
                        e.printStackTrace();
                    }

                }
            } else
                System.out.println("There are no medication plans for" + p.getPerson().getName());
        }
        System.out.println(intakes);

        return intakes;
    }


    @Override
    public void saveTakenNotTaken(String user, String pass, long time, String med, Boolean status) {
        Intakes is = new Intakes(user, pass, time, med, status);
		System.out.println(med + " has been ");
		if(status)
			System.out.println("taken");
		else
			System.out.println("not taken");
        intakesRepository.save(is);
    }
}
