// 📁 src/main/java/com/project/tour/config/WebConfig.java
package com.project.tour.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
                // ✅ dist 폴더의 모든 정적 파일을 루트에서 서빙
                registry.addResourceHandler("/**")
                                .addResourceLocations("file:./dist/")
                                .setCachePeriod(0); // 개발 중에는 캐시 비활성화

                // ✅ 폰트 파일 전용 핸들러
                registry.addResourceHandler("/assets/font/**")
                                .addResourceLocations("file:./dist/assets/font/")
                                .setCachePeriod(3600);

                // ✅ 이미지 파일 전용 핸들러
                registry.addResourceHandler("/img/**")
                                .addResourceLocations("file:./dist/img/")
                                .setCachePeriod(3600);
        }

        @Override
        public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                                .allowedOrigins("http://localhost:5173", "http://localhost:8321") // ✅ 개발/배포 둘 다
                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                                .allowCredentials(true)
                                .allowedHeaders("*");
        }
}
