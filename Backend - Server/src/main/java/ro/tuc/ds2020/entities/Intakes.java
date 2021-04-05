package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Intakes  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "time", nullable = false)
    private long time;

    @Column(name = "med", nullable = false)
    private String med;

    @Column(name = "status", nullable = false)
    private Boolean status;

    public Intakes (){}

    public Intakes (String username, String password, long time, String med, Boolean status){
        this.username = username;
        this.password = password;
        this.time = time;
        this.med = med;
        this.status = status;
    }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public long getTime() { return time; }

    public void setTime(long start_time) { this.time = start_time; }

    public String getMed() { return med; }

    public void setMed(String med) { this.med = med; }

    public Boolean getStatus() { return status; }

    public void setStatus(Boolean status) { this.status = status; }
}
