package jwzp2020.vet;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class AppConfiguration {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

}
