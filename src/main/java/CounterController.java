import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CounterController {

    @GetMapping("/counter")
    Map<Object, Object> getCounter() {
        return Map.of("counter", 1);
    }
}
