package net.anatolich.sunny.service

import net.anatolich.sunny.Messages
import net.anatolich.sunny.domain.DayOfWeekStats
import spock.lang.Specification

import java.time.DayOfWeek

class StreamingStatsCalculatorTest extends Specification {

    StatsCalculator statsService = new StreamingStatsCalculator()

    def "count messages send by days of week"() {
        given: 'set of messages send on various weekdays'
        def messages = [
                Messages.createMessageOn(DayOfWeek.FRIDAY),
                Messages.createMessageOn(DayOfWeek.FRIDAY),
                Messages.createMessageOn(DayOfWeek.SUNDAY),
                Messages.createMessageOn(DayOfWeek.MONDAY)]

        when: 'calculating stats'
        DayOfWeekStats stats = statsService.calculateMessageCountByDayOfWeek(messages)

        then: 'messages count per week day is calculated'
        stats.countMessagesOf(DayOfWeek.FRIDAY) == 2
        stats.countMessagesOf(DayOfWeek.SUNDAY) == 1
        stats.countMessagesOf(DayOfWeek.TUESDAY) == 0
    }
}
