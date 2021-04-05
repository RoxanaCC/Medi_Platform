package ro.tuc.ds2020.services;

import ro.tuc.ds2020.entities.Intakes;

import java.rmi.Remote;
import java.util.Date;
import java.util.List;

public interface MedPlanInterface {

    void start(String s);

    List<String> getPlans();
    void saveTakenNotTaken(String user, String pass, long time, String med, Boolean status);
}
