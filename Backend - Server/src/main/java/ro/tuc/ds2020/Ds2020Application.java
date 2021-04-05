package ro.tuc.ds2020;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Value;
import ro.tuc.ds2020.repositories.IntakesRepository;
import ro.tuc.ds2020.repositories.PatientRepository;
import ro.tuc.ds2020.services.MedPlanImplement;
import ro.tuc.ds2020.services.MedPlanInterface;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.TimeZone;


@Validated
@Configuration
@SpringBootApplication(scanBasePackages={"ro.tuc.ds2020"})
@EnableJpaRepositories(basePackageClasses = {PatientRepository.class,IntakesRepository.class})
public class Ds2020Application extends SpringBootServletInitializer {

    static final String topicExchangeName = "exchange";

    static final String queueName = "queue";

    @Autowired
    PatientRepository parientRepository;
    @Autowired
    IntakesRepository intakesRepository;

    @Value("${server.port}")
    private Integer userBucketPath;


    @Bean
    MedPlanInterface planService() {
        return new MedPlanImplement(parientRepository, intakesRepository);
    }

    @Bean(name="/MedicationPlanInterface")
    RemoteExporter sayHelloServiceHessian() {

        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(new MedPlanImplement(parientRepository, intakesRepository));
        exporter.setServiceInterface(MedPlanInterface.class);
        return exporter;
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("sensor");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Ds2020Application.class);
    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(Ds2020Application.class, args);
    }

}
