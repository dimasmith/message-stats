package net.anatolich.sunny.service;

import net.anatolich.sunny.domain.Direction;
import net.anatolich.sunny.domain.SenderStats;
import net.anatolich.sunny.repository.SmsMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
