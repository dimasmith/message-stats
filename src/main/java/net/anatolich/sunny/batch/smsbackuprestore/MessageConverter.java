package net.anatolich.sunny.batch.smsbackuprestore;

import net.anatolich.sunny.domain.Direction;
import net.anatolich.sunny.domain.SmsMessage;
import org.springframework.batch.item.ItemProcessor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class MessageConverter implements ItemProcessor<Message, SmsMessage> {

    private static final String IN = "1";
    public static final String OUT = "2";

    @Override
    public SmsMessage process(Message item) throws Exception {
        assertSupportedMessageType(item); // TODO: move it to Message itself. It's a part of invariant

        return SmsMessage.builder()
                .direction(item.getType().equals(IN) ? Direction.IN : Direction.OUT)
                .deliveryTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(item.getDate()), ZoneId.of("Europe/Kiev")))
                .build();
    }

    private void assertSupportedMessageType(Message item) {
        if (item.getType() == null) {
            throw new IllegalArgumentException("Message type must be specified");
        }

        if (!item.getType().equals(IN) && !item.getType().equals(OUT)) {
            throw new IllegalArgumentException(String.format("Message type must be either 1 or to. Got [ %s ] instead", item.getType()));
        }
    }
}
