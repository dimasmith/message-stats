package net.anatolich.sunny.service;

import net.anatolich.sunny.domain.DayOfWeekStats;
import net.anatolich.sunny.domain.SmsMessage;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class StreamingStatsCalculator implements StatsCalculator {

    @Override
    public DayOfWeekStats calculateMessageCountByDayOfWeek(Stream<SmsMessage> messageStream) {
        final Map<DayOfWeek, Long> stats =
                messageStream.collect(
                        Collectors.groupingBy(
                                message -> message.getDeliveryTime().getDayOfWeek(),
                                Collectors.counting()
                        ));

        return DayOfWeekStats.of(stats);
    }

    @Override
    public MonthStats calculateMessageCountByMonth(Stream<SmsMessage> messageStream) {
        final Map<Month, Long> stats =
                messageStream.collect(
                        Collectors.groupingBy(
                                message -> message.getDeliveryTime().getMonth(),
                                Collectors.counting()
                        ));
        return MonthStats.of(stats);
    }
}
