// 📁 src/main/java/com/project/tour/util/PasswordUtil.java
package com.project.tour.util;

import java.util.Random;

public class PasswordUtil {
    public static String generateTempPassword() {
        Random random = new Random();
        int number = 1000 + random.nextInt(9000); // 1000 ~ 9999
        return String.valueOf(number);
    }
}
