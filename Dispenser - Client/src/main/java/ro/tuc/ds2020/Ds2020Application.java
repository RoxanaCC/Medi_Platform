package ro.tuc.ds2020;

import org.springframework.boot.SpringApplication;
import ro.tuc.ds2020.MedPlanInterface;
import ro.tuc.ds2020.views.Login;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import javax.swing.*;

@SpringBootApplication
public class Ds2020Application extends JFrame {

    @Bean
    public HessianProxyFactoryBean hessianInvoker() {
        HessianProxyFactoryBean invoker = new HessianProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8080/MedicationPlanInterface");
        invoker.setServiceInterface(MedPlanInterface.class);
        return invoker;
    }

    public static void main(String[] args) throws Exception {

        MedPlanInterface interfac = SpringApplication.run(Ds2020Application.class, args)
                .getBean(MedPlanInterface.class);
        Login appFrame = new Login(interfac);
        appFrame.setVisible(true);

    }
}
