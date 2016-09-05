package net.anatolich.sunny.domain

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
}
