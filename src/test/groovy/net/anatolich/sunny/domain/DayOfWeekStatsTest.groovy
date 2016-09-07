package net.anatolich.sunny.domain

import net.anatolich.sunny.rest.StatsEntry
import spock.lang.Specification

import java.time.DayOfWeek


class DayOfWeekStatsTest extends Specification {

    def "create stats from map"() {
        given: 'weekday stats map'
        def weekdayStats = [:]
        weekdayStats.put(DayOfWeek.SUNDAY, 10L)
        weekdayStats.put(DayOfWeek.MONDAY, 22L)

        when: 'create stats from map'
        DayOfWeekStats stats = DayOfWeekStats.of(weekdayStats)

        then:
        stats.countMessagesOf(DayOfWeek.SUNDAY) == weekdayStats.get(DayOfWeek.SUNDAY)
        stats.countMessagesOf(DayOfWeek.MONDAY) == weekdayStats.get(DayOfWeek.MONDAY)
        stats.countMessagesOf(DayOfWeek.TUESDAY) == 0
    }

    def "get full stats data"() {
        given: 'week day stats with count of messages for some week days'
        def weekdayStats = [:]
        weekdayStats.put(DayOfWeek.SUNDAY, 10L)
        weekdayStats.put(DayOfWeek.MONDAY, 22L)
        weekdayStats.put(DayOfWeek.TUESDAY, 11L)

        when: 'building stats series'
        DayOfWeekStats stats = DayOfWeekStats.of(weekdayStats)
        List<StatsEntry> statstSeries = stats.getStats()

        then: 'series are present for all week days'
        statstSeries == [
                new StatsEntry(DayOfWeek.MONDAY.name(), 22L),
                new StatsEntry(DayOfWeek.TUESDAY.name(), 11L),
                new StatsEntry(DayOfWeek.WEDNESDAY.name(), 0L),
                new StatsEntry(DayOfWeek.THURSDAY.name(), 0L),
                new StatsEntry(DayOfWeek.FRIDAY.name(), 0L),
                new StatsEntry(DayOfWeek.SATURDAY.name(), 0L),
                new StatsEntry(DayOfWeek.SUNDAY.name(), 10L),
        ]
    }
}
