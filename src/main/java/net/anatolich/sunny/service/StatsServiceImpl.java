package net.anatolich.sunny.service;

import net.anatolich.sunny.domain.*;
import net.anatolich.sunny.repository.SmsMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatsServiceImpl implements StatsService {

    private final SmsMessageRepository messageRepository;
    private final StatsCalculator statsCalculator;

    @Autowired
    public StatsServiceImpl(SmsMessageRepository messageRepository, StatsCalculator statsCalculator) {
        this.messageRepository = messageRepository;
        this.statsCalculator = statsCalculator;
    }

    @Override
    public Stats countByDirection() {
        final int incoming = messageRepository.countByDirection(Direction.IN);
        final int outgoing = messageRepository.countByDirection(Direction.OUT);
        return new SenderStats(incoming, outgoing);
    }

    @Override
    public DayOfWeekStats calculateStatsByDayOfWeek() {
        final Iterable<SmsMessage> allMessages = messageRepository.findAll();
        return statsCalculator.calculateMessageCountByDayOfWeek(allMessages);
    }

    @Override
    public MonthStats calculateStatsByMonth() {
        final Iterable<SmsMessage> allMessages = messageRepository.findAll();
        return statsCalculator.calculateMessageCountByMonth(allMessages);
    }

}
