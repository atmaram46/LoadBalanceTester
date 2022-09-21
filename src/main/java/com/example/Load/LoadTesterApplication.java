package com.example.Load;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class LoadTesterApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(LoadTesterApplication.class)
				.web(WebApplicationType.NONE)
						.run(args);

		WebClient loadBalancedClient = ctx.getBean(WebClient.Builder.class).build();

		String body = "{\n" +
				"    \"name\": \"arun\",\n" +
				"    \"age\": 1\n" +
				"}";

		for(int i=0; i<1000; i++) {
			String response = loadBalancedClient.post().uri("http://loadSerivce/users/saveUser")
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromObject(body))
					.retrieve().toEntity(String.class)
					.block().getBody();
			System.out.println(response);
		}

	}

}
