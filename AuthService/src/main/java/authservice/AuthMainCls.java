package authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableEurekaClient
public class AuthMainCls {

	public static void main(String[] args) {
		SpringApplication.run(AuthMainCls.class, args);
	}
	@Bean
	@LoadBalanced
	public RestTemplate getresttemplate() {

		return new RestTemplate();
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder getwebclientbuilder(){

		return WebClient.builder();
	}


}
