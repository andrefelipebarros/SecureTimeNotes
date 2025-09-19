package securetimenotes.andrefelipebarros.securetimenotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecuretimenotesApplication {

	public static void main(String[] args) {
		System.setProperty("management.metrics.binders.processor.enabled", "false");
		SpringApplication.run(SecuretimenotesApplication.class, args);
	}

}
