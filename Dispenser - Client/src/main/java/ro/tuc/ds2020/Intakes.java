package ro.tuc.ds2020;

public class Intakes {

    private String username;
    private String password;
    private String start_time;
    private String end_time;
    private String interval;
    private String med;

    public Intakes (String username, String password, String start_time, String end_time, String interval, String med){
        this.username = username;
        this.password = password;
        this.start_time = start_time;
        this.end_time = end_time;
        this.interval = interval;
        this.med = med;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getStart_time() { return start_time; }

    public void setEnd_time(String end_time) { this.end_time = end_time; }

    public String getInterval() { return interval; }

    public void setInterval(String interval) { this.interval = interval; }

    public String getMed() { return med; }

    public void setMed(String med) { this.med = med; }
}
