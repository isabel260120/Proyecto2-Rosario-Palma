package com.proyecto.proyecto02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication(scanBasePackages = {"Controllers", "Services","BaseDatos" })
public class Proyecto02Application {

    public static void main(String[] args) {
        SpringApplication.run(Proyecto02Application.class, args);
    }

}
