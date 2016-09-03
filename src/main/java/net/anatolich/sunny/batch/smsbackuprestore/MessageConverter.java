package net.anatolich.sunny.batch.smsbackuprestore;

import net.anatolich.sunny.domain.Direction;
import net.anatolich.sunny.domain.SmsMessage;
import org.springframework.batch.item.ItemProcessor;

public class MessageConverter implements ItemProcessor<Message, SmsMessage> {
    @Override
    public SmsMessage process(Message item) throws Exception {
        return SmsMessage.builder()
                .direction(item.getType().equals("1") ? Direction.IN : Direction.OUT)
                .build();
    }
}
