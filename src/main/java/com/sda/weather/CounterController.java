package com.sda.weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;

@RestController
public class CounterController {


    @GetMapping("/counter")
    Map<Object, Object> getCounter(HttpServletRequest request, HttpServletResponse response) {
//        request.getHeaderNames().asIterator().forEachRemaining(System.out::println);
//        response.setHeader("header-from-the-server", "value");

        int counter = 0;
        Cookie[] tableCookies = request.getCookies();
        if (tableCookies == null) {
            counter = 1;
            Cookie cookie = new Cookie("counter", String.valueOf(counter));
            response.addCookie(cookie);
            return Map.of("counter", counter);
        }

        Cookie cookie = Arrays.stream(tableCookies)
                .filter(c -> c.getName().equals("counter"))
                .findFirst()
                .orElseGet(() -> new Cookie("counter", "0"));

        String value = cookie.getValue();
        counter = Integer.parseInt(value);
        counter++;
        cookie.setValue(String.valueOf(counter));
        cookie.setMaxAge(5); // maksymalny czas życia ciasteczka
        response.addCookie(cookie);

        return Map.of("counter", counter);
    }

    @GetMapping("/counter/session")
    Map<Object, Object> getSessionCounter(HttpSession httpSession) {
        Object counter = httpSession.getAttribute("counter");

        if (counter == null) {
            httpSession.setAttribute("counter", 1);
            return Map.of("counter", 1);
        }
        int counterInt = (int) counter;
        counterInt++;
        httpSession.setAttribute("counter", counterInt);
        return Map.of("counter", counterInt);
    }
}
