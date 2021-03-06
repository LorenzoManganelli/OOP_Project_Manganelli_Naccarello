package com.Manganelli_Naccarello.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.Manganelli_Naccarello.project.service.ServiceImpl;

@SpringBootApplication (scanBasePackages={"com.Manganelli_Naccarello.project.controller", "com.Manganelli_Naccarello.project.service","com.Manganelli_Naccarello.project.model"})
public class OpenWeatherWindApplication {

	public static void main(String[] cityName) {
		SpringApplication.run(OpenWeatherWindApplication.class, cityName);
	}
}