package net.anatolich.sunny.batch;

import net.anatolich.sunny.batch.smsbackuprestore.Message;
import net.anatolich.sunny.batch.smsbackuprestore.MessageConverter;
import net.anatolich.sunny.batch.smsbackuprestore.MessageReader;
import net.anatolich.sunny.domain.SmsMessage;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class MessageImportJobConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<SmsMessage> smsMessageWriter() {
        final JdbcBatchItemWriter<SmsMessage> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(MessageImportJobConfiguration::mapMessageParameters);
        writer.setSql("INSERT INTO SMS_MESSAGE (direction, delivery_time) VALUES (:direction, :deliveryTime)");
        writer.setDataSource(dataSource);
        return writer;
    }

    private static MapSqlParameterSource mapMessageParameters(SmsMessage message) {
        final Map<String, Object> values = new HashMap<>();
        values.put("direction", message.getDirection().name());
        values.put("deliveryTime", Timestamp.valueOf(message.getDeliveryTime()));
        return new MapSqlParameterSource(values);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new MessageConverter();
    }

    @Bean @JobScope
    public MessageReader messageReader() {
        return new MessageReader();
    }

    @Bean
    public Step importStep() {
        return stepBuilderFactory.get("importStep")
                .<Message, SmsMessage>chunk(100)
                .reader(messageReader())
                .processor(messageConverter())
                .writer(smsMessageWriter())
                .build();
    }

    @Bean(name = "importMessagesJob")
    public Job importMessagesJob() {
        return jobBuilderFactory.get("importMessagesJob")
                .incrementer(new RunIdIncrementer())
                .flow(importStep())
                .end()
                .build();
    }

}
