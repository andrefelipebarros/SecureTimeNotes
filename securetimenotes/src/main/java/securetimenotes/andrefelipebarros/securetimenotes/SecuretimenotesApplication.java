package securetimenotes.andrefelipebarros.securetimenotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //(exclude = org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration.class)
public class SecuretimenotesApplication {

	public static void main(String[] args) {
		//System.setProperty("management.metrics.binders.processor.enabled", "false");
		SpringApplication.run(SecuretimenotesApplication.class, args);
	}

}
