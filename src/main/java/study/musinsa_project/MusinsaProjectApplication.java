package study.musinsa_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MusinsaProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusinsaProjectApplication.class, args);
    }

}
