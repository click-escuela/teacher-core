package click.escuela.teacher.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TeacherCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeacherCoreApplication.class, args);
	}
	

}
