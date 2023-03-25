package com.casasky.cryptoanywhere;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SchedulingConfig.class)
@Slf4j
public class CryptoAnywhereApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoAnywhereApplication.class, args);
    }

}
