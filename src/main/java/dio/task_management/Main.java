package dio.task_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "dio.task_management")
@EntityScan("dio.task_management.persistence.entity")
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        SpringApplication.run(Main.class, args);
    }
}