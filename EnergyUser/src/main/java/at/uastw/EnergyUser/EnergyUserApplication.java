package at.uastw.EnergyUser;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@EnableScheduling
public class EnergyUserApplication {

	@Bean
	RestClient.Builder restClientBuilder() {
		return RestClient.builder();
	}

	@Bean
	public Queue usedEnergyQueue() {
		return new Queue("used_energy", true);
	}


	public static void main(String[] args) {
		SpringApplication.run(EnergyUserApplication.class, args);
	}

}
