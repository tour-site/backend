// // 📁
// src/main/java/com/project/tour/controller/FrontendForwardController.java
// package com.project.tour.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;

// @Controller
// public class FrontendForwardController {

// @RequestMapping(value = {
// "/{path:^(?!api|assets|css|js|img|fonts|favicon\\.ico).*}" })
// public String forward() {
// return "forward:/index.html";
// }
// }

// // 📁
// src/main/java/com/project/tour/controller/FrontendForwardController.java
// package com.project.tour.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;

// @Controller
// public class FrontendForwardController {

// @RequestMapping(value = {
// "/", "/{path:[^\\.]*}", "/**/{path:[^\\.]*}"
// })
// public String forward() {
// return "forward:/index.html";
// }
// }

// // 📁
// src/main/java/com/project/tour/controller/FrontendForwardController.java
// package com.project.tour.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;

// @Controller
// public class FrontendForwardController {

// @RequestMapping(value = {
// "/",
// "/{path:^(?!api|assets|css|js|img|fonts|favicon\\.ico$).*$}",
// "/**/{path:^(?!api|assets|css|js|img|fonts|favicon\\.ico$).*$}"
// })
// public String forward() {
// System.out.println("✅ 프론트 요청 -> index.html로 포워딩됨");
// return "forward:/index.html";
// }
// }
