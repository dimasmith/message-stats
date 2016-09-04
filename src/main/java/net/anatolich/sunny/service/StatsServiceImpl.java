package net.anatolich.sunny.service;

import net.anatolich.sunny.domain.DayOfWeekStats;
import net.anatolich.sunny.domain.Direction;
import net.anatolich.sunny.domain.SenderStats;
import net.anatolich.sunny.domain.SmsMessage;
import net.anatolich.sunny.repository.SmsMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class StatsServiceImpl implements StatsService {

    private final SmsMessageRepository messageRepository;

    @Autowired
    public StatsServiceImpl(SmsMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public SenderStats countByDirection() {
        final int incoming = messageRepository.countByDirection(Direction.IN);
        final int outgoing = messageRepository.countByDirection(Direction.OUT);
        return new SenderStats(incoming, outgoing);
    }

    @Override
    public DayOfWeekStats calculateStatsByDayOfWeek() {
        final Stream<SmsMessage> messageStream = StreamSupport.stream(messageRepository.findAll().spliterator(), false);
        final Map<DayOfWeek, Long> collect =
                messageStream.collect(
                        Collectors.groupingBy(
                                message -> message.getDeliveryTime().getDayOfWeek(),
                                Collectors.counting()
                        ));

        return DayOfWeekStats.of(collect);
    }
}
