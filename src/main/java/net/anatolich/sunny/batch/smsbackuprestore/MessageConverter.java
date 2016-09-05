package net.anatolich.sunny.batch.smsbackuprestore;

import net.anatolich.sunny.domain.Direction;
import net.anatolich.sunny.domain.SmsMessage;
import org.springframework.batch.item.ItemProcessor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class MessageConverter implements ItemProcessor<Message, SmsMessage> {

    private static final String IN = "1";

    @Override
    public SmsMessage process(Message item) throws Exception {
        return SmsMessage.builder()
                .direction(item.getType().equals(IN) ? Direction.IN : Direction.OUT)
                .deliveryTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(item.getDate()), ZoneId.of("Europe/Kiev")))
                .build();
    }

}
