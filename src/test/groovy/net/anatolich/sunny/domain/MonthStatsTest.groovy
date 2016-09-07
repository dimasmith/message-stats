package net.anatolich.sunny.domain

import spock.lang.Specification

import java.time.Month


class MonthStatsTest extends Specification {

    def "create stats from month count map"() {
        given: 'map with message count grouped by month'
        Map<Month, Long> counts = [:]
        counts.put(Month.MAY, 5L)
        counts.put(Month.JUNE, 10L)

        when: 'creating stats based on this map'
        MonthStats stats = MonthStats.of(counts)

        then: 'values are same as in source map'
        stats.countMessagesOf(Month.MAY) == 5L
        stats.countMessagesOf(Month.JUNE) == 10L

        and: 'values for missing month are 0'
        stats.countMessagesOf(Month.JULY) == 0L
    }

    def "build stats series"() {
        given: 'month stats with counts for some month'
        Map<Month, Long> counts = [:]
        counts.put(Month.MAY, 5L)
        counts.put(Month.JUNE, 10L)
        counts.put(Month.JULY, 15L)
        counts.put(Month.AUGUST, 20L)
        MonthStats stats = MonthStats.of(counts)

        when: 'building stats series'
        List<StatsEntry> dataSeries = stats.getDataSeries()

        then: 'series are present for all months'
        dataSeries == [
                new StatsEntry(Month.JANUARY.name(), 0L),
                new StatsEntry(Month.FEBRUARY.name(), 0L),
                new StatsEntry(Month.MARCH.name(), 0L),
                new StatsEntry(Month.APRIL.name(), 0L),
                new StatsEntry(Month.MAY.name(), 5L),
                new StatsEntry(Month.JUNE.name(), 10L),
                new StatsEntry(Month.JULY.name(), 15L),
                new StatsEntry(Month.AUGUST.name(), 20L),
                new StatsEntry(Month.SEPTEMBER.name(), 0L),
                new StatsEntry(Month.OCTOBER.name(), 0L),
                new StatsEntry(Month.NOVEMBER.name(), 0L),
                new StatsEntry(Month.DECEMBER.name(), 0L)
        ]
    }
}
