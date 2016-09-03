package net.anatolich.sunny;

import net.anatolich.sunny.domain.Direction;
import net.anatolich.sunny.domain.SmsMessage;

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
}
