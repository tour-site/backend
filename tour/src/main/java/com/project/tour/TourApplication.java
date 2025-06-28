// 📁 src/main/java/com/project/tour/TourApplication.java
package com.project.tour;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TourApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul")); // ✅ JVM 타임존 설정
        SpringApplication.run(TourApplication.class, args);
        System.out.println("✅ 서버 실행됨");
    }
}
