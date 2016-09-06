package net.anatolich.sunny.service

import net.anatolich.sunny.Messages
import net.anatolich.sunny.domain.DayOfWeekStats
import net.anatolich.sunny.domain.SmsMessage
import spock.lang.Specification

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

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

    def "count messages by month"() {
        given: 'set of messages send on various month'
        def messages = [
                SmsMessage.builder().deliveryTime(LocalDate.of(2016, Month.MAY, 15).atStartOfDay()).build(),
                SmsMessage.builder().deliveryTime(LocalDate.of(2015, Month.OCTOBER, 27).atStartOfDay()).build(),
                SmsMessage.builder().deliveryTime(LocalDate.of(2016, Month.JUNE, 29).atStartOfDay()).build(),
                SmsMessage.builder().deliveryTime(LocalDate.of(2014, Month.JUNE, 15).atStartOfDay()).build(),
                SmsMessage.builder().deliveryTime(LocalDate.of(2016, Month.JULY, 15).atStartOfDay()).build(),
                SmsMessage.builder().deliveryTime(LocalDate.of(2016, Month.JULY, 17).atStartOfDay()).build()]

        when: 'calculating stats'
        MonthStats stats = statsService.calculateMessageCountByMonth(messages)

        then: 'messages count per week day is calculated'
        stats.countMessagesOf(Month.MAY) == 1L
        stats.countMessagesOf(Month.OCTOBER) == 1L
        stats.countMessagesOf(Month.JUNE) == 2L
        stats.countMessagesOf(Month.JULY) == 2L
        stats.countMessagesOf(Month.NOVEMBER) == 0L // no messages on this month
    }
}
