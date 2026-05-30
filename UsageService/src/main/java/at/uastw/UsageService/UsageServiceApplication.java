package at.uastw.UsageService;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsageServiceApplication {

	@Bean
	public Queue echoInProducedEnergyQueue(){
		return new Queue("produced_energy", true);
	}
	@Bean
	public Queue echoInUsedEnergyQueue(){
		return new Queue("used_energy", true);
	}
	@Bean
	public Queue echoOutCurrentPercentageQueue() {
		return new Queue("current_percentage", true);
	}

	public static void main(String[] args) {
		SpringApplication.run(UsageServiceApplication.class, args);
	}

}
