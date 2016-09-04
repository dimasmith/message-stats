package net.anatolich.sunny;

import net.anatolich.sunny.domain.Direction;
import net.anatolich.sunny.domain.SmsMessage;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * Utility class to build messages
 */
public class Messages {

    public static SmsMessage createOutgoingMessage() {
        return SmsMessage.builder()
                .direction(Direction.OUT)
                .build();
    }

    public static SmsMessage createIncomingMessage() {
        return SmsMessage.builder()
                .direction(Direction.IN)
                .build();
    }

    public static SmsMessage createMessageOn(DayOfWeek dayOfWeek) {
        final LocalDateTime deliveryDate = LocalDateTime.now();
        final LocalDateTime adjustedDate = (LocalDateTime) dayOfWeek.adjustInto(deliveryDate);
        return SmsMessage.builder()
                .deliveryTime(adjustedDate)
                .build();
    }
}
