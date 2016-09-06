package net.anatolich.sunny.service;

import net.anatolich.sunny.domain.DayOfWeekStats;
import net.anatolich.sunny.domain.SmsMessage;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface StatsCalculator {

    default DayOfWeekStats calculateMessageCountByDayOfWeek(Iterable<SmsMessage> messages) {
        return calculateMessageCountByDayOfWeek(StreamSupport.stream(messages.spliterator(), false));
    }

    DayOfWeekStats calculateMessageCountByDayOfWeek(Stream<SmsMessage> messageStream);

    default MonthStats calculateMessageCountByMonth(Iterable<SmsMessage> messages) {
        return calculateMessageCountByMonth(StreamSupport.stream(messages.spliterator(), false));
    }

    MonthStats calculateMessageCountByMonth(Stream<SmsMessage> messageStream);
}
