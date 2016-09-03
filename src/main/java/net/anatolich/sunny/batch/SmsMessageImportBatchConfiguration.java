package net.anatolich.sunny.batch;

import net.anatolich.sunny.batch.smsbackuprestore.Message;
import net.anatolich.sunny.batch.smsbackuprestore.MessageConverter;
import net.anatolich.sunny.domain.SmsMessage;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@Profile("import")
public class SmsMessageImportBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<SmsMessage> smsMessageWriter() {
        final JdbcBatchItemWriter<SmsMessage> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(message -> {
            final Map<String, Object> values = new HashMap<>();
            values.put("direction", message.getDirection().name());
            return new MapSqlParameterSource(values);
        });
        writer.setSql("INSERT INTO SMS_MESSAGE (direction) VALUES (:direction)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new MessageConverter();
    }

    @Bean
    public StaxEventItemReader<Message> smsBackupRestoreXmlReader() {
        final StaxEventItemReader<Message> reader = new StaxEventItemReader<>();
        reader.setResource(new ClassPathResource("smsbackuprestore/messages.xml"));
        reader.setFragmentRootElementName("sms");
        reader.setUnmarshaller(smsMarshaller());
        return reader;

    }

    @Bean
    public Jaxb2Marshaller smsMarshaller() {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Message.class);
        return marshaller;
    }

    @Bean
    public Step importStep() {
        return stepBuilderFactory.get("importStep")
                .<Message, SmsMessage>chunk(100)
                .reader(smsBackupRestoreXmlReader())
                .processor(messageConverter())
                .writer(smsMessageWriter())
                .build();
    }

    @Bean
    public Job importMessagesJob() {
        return jobBuilderFactory.get("importFromSmsBackupRestore")
                .incrementer(new RunIdIncrementer())
                .flow(importStep())
                .end()
                .build();
    }

}
