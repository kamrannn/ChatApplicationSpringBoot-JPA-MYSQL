package com.chatapplicationspringBoot;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class ChatApplicationSpringBootApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ChatApplicationSpringBootApplication.class, args);
	}

}
