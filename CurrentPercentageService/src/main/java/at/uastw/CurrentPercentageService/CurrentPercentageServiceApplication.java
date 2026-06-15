package at.uastw.CurrentPercentageService;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CurrentPercentageServiceApplication {

	@Bean
	public Queue echoInCurrentPercentageQueue() {
		return new Queue("current_percentage", true);
	}

	public static void main(String[] args) {
		SpringApplication.run(CurrentPercentageServiceApplication.class, args);
	}

}
