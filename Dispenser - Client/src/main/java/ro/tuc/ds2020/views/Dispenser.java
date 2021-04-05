package ro.tuc.ds2020.views;

;
import ro.tuc.ds2020.Intakes;
import ro.tuc.ds2020.MedPlanInterface;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Dispenser  extends JFrame implements ActionListener {

    JLabel dateTime;
    List<String> plans;
    MedPlanInterface service;
    String user;
    String pass;
    List<Intakes> intakes = new ArrayList<>();
    ArrayList<JLabel> meds=new ArrayList<JLabel>();
    ArrayList<JLabel> times=new ArrayList<JLabel>();
    ArrayList<JButton> take=new ArrayList<JButton>();
    ArrayList<Integer> taken=new ArrayList<Integer>();


    public Dispenser(List<String> plans, MedPlanInterface service, String user, String pass) {

        this.plans = plans;
        this.service=service;
        this.user = user;
        this.pass = pass;
        dateTime = new JLabel(" ");
        SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyy \nHH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String f = ". " + formatter.format(date) + " .";
        dateTime.setText(f);
        showTime();
        getMeds();
        initUI();
    }

    private void initUI() {

        setLayout(new BorderLayout());
        setContentPane(new JLabel(new ImageIcon("X:\\University files\\SD\\Asg 3\\ds2020_30643_ciorea-cristescu_roxana_assignment_3_client\\Dispenser.png")));
        setLayout(new FlowLayout());
        JLabel plans = new JLabel("Your medication plans:");

        dateTime.setForeground(Color.white);
        dateTime.setOpaque(true);
        dateTime.setBackground(Color.black);
        dateTime.setFont(new Font(dateTime.getFont().getName(),dateTime.getFont().getStyle(),20));
        add(dateTime);
        dateTime.setSize(250, 30);
        dateTime.setLocation(220, 60);

        plans.setForeground(Color.white);
        plans.setFont(new Font("Cooper Black", Font.ITALIC, 14));

        setTitle("Pill Dispenser");

        setLayout(null);
        setSize(700, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        for (int i=0; i<meds.size(); i++) {
            add(meds.get(i));
            (meds.get(i)).setSize(150, 20);
            (meds.get(i)).setLocation(100, 420 + i*40);
            add(times.get(i));
            (times.get(i)).setSize(200, 20);
            (times.get(i)).setLocation(250, 420 + i*40);
            add(take.get(i));
            (take.get(i)).setSize(100, 20);
            (take.get(i)).setLocation(450, 420 + i*40);

        }

    }


    public void getMeds() {
        for (String str : plans) {
            String[] data = str.split("\t\t");
            JLabel m = new JLabel(data[5]);
            int start = 0;
            int end = 0;
            if(user.compareTo(data[0]) == 0 && pass.compareTo(data[1]) == 0) {
                Intakes intake = new Intakes(data[0], data[1], data[2], data[3], data[4], data[5]);
                intakes.add(intake);
                JLabel t = new JLabel();
                if(data[4].compareTo("morning") == 0) {
                     t = new JLabel(" ⏰ 9:00 - 10:00");
                    start = 20;
                    end = 24;
                }
                if(data[4].compareTo("noon") == 0) {
                    t = new JLabel(" ⏰ 10:00 - 12:00");
                    start = 10;
                    end = 12;
                }
                if(data[4].compareTo("afternoon") == 0) {
                    t = new JLabel(" ⏰ 12:00 - 14:00");
                    start = 12;
                    end = 14;
                }
                JButton b = new JButton("  TAKE  ");
                m.setForeground(new Color(10, 13, 40));
                t.setForeground(new Color(10, 13, 40));
                //b.setBackground(new Color(10, 13, 40));
                b.setForeground(Color.white);
                b.setBorder(BorderFactory.createBevelBorder(0));
                m.setFont(new Font(m.getFont().getName(), m.getFont().getStyle(), 18));
                t.setFont(new Font(t.getFont().getName(), t.getFont().getStyle(), 20));
                b.setFont(new Font(b.getFont().getName(), b.getFont().getStyle(), 16));
                b.addActionListener(this);
                b.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        b.setBackground(new Color(102, 242, 255));
                        b.setForeground(Color.black);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        b.setBackground(new Color(10, 13, 40));
                        b.setForeground(Color.white);
                    }
                });
                meds.add(m);
                times.add(t);
                take.add(b);
                taken.add(0);
                this.activateButton(start, end, b);
            }
        }
    }

    private void activateButton(int start, int end, JButton b){

        Runnable runnable =
                () -> {
                    while(true){
                        SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyy \nHH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        int hours = date.getHours();
                        if(start <= hours && end > hours && taken.get(take.indexOf(b))==0){
                            b.setEnabled(true);
                            b.setBackground(new Color(10, 13, 40));
                        }else{
                            b.setEnabled(false);
                            b.setBackground(new Color(116, 14, 15));
                        }

                        if(end <= hours && taken.get(take.indexOf(b))==0){
                            taken.set(take.indexOf(b), 2);
                            for(Intakes in :intakes){
                                if(in.getMed().compareTo(meds.get(take.indexOf(b)).getText()) == 0){
                                    service.saveTakenNotTaken(in.getUsername(), in.getPassword(), date.getTime(), in.getMed(), false);
                                }
                            }
                            meds.get(take.indexOf(b)).setForeground(Color.red);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                };

        Thread thread = new Thread(runnable);
        thread.start();

    }
    private void showTime(){


        Runnable runnable =
                () -> {
                    while(true){
                        SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyy \nHH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        String f = " \uD83D\uDCC6 " + formatter.format(date)+ " \n ";
                        dateTime.setText(f);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                };

        Thread thread = new Thread(runnable);
        thread.start();



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0;i<meds.size();i++){
            if(e.getSource() == take.get(i)){
                String med = meds.get(i).getText();
                SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyy \nHH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                for(Intakes in :intakes){
                    if(in.getMed().compareTo(med) == 0){
                        service.saveTakenNotTaken(in.getUsername(), in.getPassword(), date.getTime(), in.getMed(), true);
                    }
                }
                take.get(i).setEnabled(false);
                taken.set(i, 1);
                JLabel l = meds.get(i);
                l.setForeground(new Color(106, 255, 75));
                meds.set(i,l);
            }
        }
    }
}
