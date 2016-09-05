package net.anatolich.sunny;

import net.anatolich.sunny.batch.ImportJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class, SmsStatsApplication.class})
public class SmsStatsApplication implements CommandLineRunner {

    @Value("${import.url:}")
    private String importUrl;
    @Autowired
    private ImportJobLauncher importJobLauncher;

    public static void main(String[] args) {
        SpringApplication.run(SmsStatsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (!importUrl.isEmpty()) {
            importJobLauncher.importMessages(importUrl);
        }
    }
}
