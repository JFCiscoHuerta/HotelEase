package org.gklyphon.room;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
@EnableFeignClients
@SpringBootApplication
public class RoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomApplication.class, args);
	}

}
