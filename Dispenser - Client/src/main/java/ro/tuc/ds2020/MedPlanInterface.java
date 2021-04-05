package ro.tuc.ds2020;

import java.util.List;

public interface MedPlanInterface {

    void start(String s);

    List<String> getPlans();
    void saveTakenNotTaken(String user, String pass, long time, String med, Boolean status);
}
