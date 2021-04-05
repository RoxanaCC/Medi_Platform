package ro.tuc.ds2020.views;

import ro.tuc.ds2020.Intakes;
import ro.tuc.ds2020.MedPlanInterface;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Login  extends JFrame {

    MedPlanInterface service;
    List<String> plans = new ArrayList<>();

    private JButton logB;
    private JLabel userL;
    private JLabel passL;
    private JTextField userT;
    private JTextField passT;

    private JLabel errorL;


    public Login(MedPlanInterface interfac) {
        this.service = interfac;
        initUI();
    }

    private void initUI() {
        setTitle("Login");
        setSize(700,700);

        plans = this.service.getPlans();
        List<Intakes> intakes = new ArrayList<>();

        setLayout(new BorderLayout());
        setContentPane(new JLabel(new ImageIcon("X:\\University files\\SD\\Asg 3\\ds2020_30643_ciorea-cristescu_roxana_assignment_3_client\\Login.png")));
        setLayout(new FlowLayout());

        setLayout(null);

        userL = new JLabel("Username: ");
        userL.setFont(new Font("Cooper Black", Font.ITALIC, 14));
        add(userL);
        userL.setSize(200, 20);
        userL.setLocation(150, 100);

        passL = new JLabel("Password: ");
        passL.setFont(new Font("Cooper Black", Font.ITALIC, 14));
        add(passL);
        passL.setSize(200, 20);
        passL.setLocation(150, 150);

        userT = new JTextField();
        add(userT);
        userT.setSize(200, 20);
        userT.setLocation(350, 100);

        passT = new JTextField();
        add(passT);
        passT.setSize(200, 20);
        passT.setLocation(350, 150);

        logB = new JButton("Login");
        add(logB);
        logB.setSize(100, 40);
        logB.setLocation(300, 250);

        logB.addActionListener((ActionEvent event) -> {
            service.start("Hello from client!");
            for(String str : plans) {
                String[] data = str.split("\t\t");
                Intakes in = new Intakes(data[0], data[1], data[2], data[3], data[4], data[5]);
                if (userT.getText().compareTo(data[0]) == 0 && passT.getText().compareTo(data[1]) == 0) {
                    Dispenser pills = new Dispenser(plans, service, data[0], data[1]);
                    this.setVisible(false);
                    pills.setVisible(true);
                    break;
                }
            }
            errorL.setText("The username or password is wrong");
        });

        errorL = new JLabel();
        errorL.setForeground(Color.red);
        add(errorL);
        //errorL.setText("The username or password is wrong");
        errorL.setSize(300, 20);
        errorL.setLocation(250, 300);


        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
