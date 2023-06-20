package com.ocr.mediscreen_assess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients("com.ocr.mediscreen_assess")
// @ImportAutoConfiguration({FeignAutoConfiguration.class})
public class MediscreenAssessApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediscreenAssessApplication.class, args);
    }

}
