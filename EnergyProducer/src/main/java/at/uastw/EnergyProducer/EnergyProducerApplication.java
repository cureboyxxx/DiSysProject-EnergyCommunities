package at.uastw.EnergyProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@EnableScheduling
public class EnergyProducerApplication {

	@Bean
	RestClient.Builder restClientBuilder() {
		return RestClient.builder();
	}

	public static void main(String[] args) {
		SpringApplication.run(EnergyProducerApplication.class, args);
	}

}
