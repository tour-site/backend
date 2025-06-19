// 📁 src/main/java/com/project/tour/config/WebConfig.java
package com.project.tour.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                                .allowedOrigins("http://localhost:5173") // ✅ Vite 개발 서버 주소
                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                                .allowCredentials(true)
                                .allowedHeaders("*");
        }
}
