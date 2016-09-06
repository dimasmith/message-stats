package net.anatolich.sunny.service

import net.anatolich.sunny.Messages
import net.anatolich.sunny.domain.DayOfWeekStats
import net.anatolich.sunny.domain.Direction
import net.anatolich.sunny.repository.SmsMessageRepository
import spock.lang.Specification

import java.time.DayOfWeek

class StatsServiceImplTest extends Specification {

    SmsMessageRepository stubRepository;

    StatsService statsService

    void setup() {
        stubRepository = Mock(SmsMessageRepository)
        statsService = new StatsServiceImpl(stubRepository, new StreamingStatsCalculator())
    }

    def "count messages by sender"() {
        given: 'repository counts messages by direction'
        stubRepository.countByDirection(Direction.IN) >> 2
        stubRepository.countByDirection(Direction.OUT) >> 1

        when: 'stats calculated'
        def directionStats = statsService.countByDirection()

        then: 'messages calculated by direction'
        directionStats.incoming == 2
        directionStats.outgoing == 1
    }

    def "count messages send by days of week"() {
        given: 'messages sent on various weekdays'
        def messages = [
                Messages.createMessageOn(DayOfWeek.FRIDAY),
                Messages.createMessageOn(DayOfWeek.FRIDAY),
                Messages.createMessageOn(DayOfWeek.SUNDAY),
                Messages.createMessageOn(DayOfWeek.MONDAY)]
        stubRepository.findAll() >> messages

        when: 'stats calculated'
        DayOfWeekStats stats = statsService.calculateStatsByDayOfWeek()

        then: 'messages count for each week day calculated'
        stats.countMessagesOf(DayOfWeek.FRIDAY) == 2
        stats.countMessagesOf(DayOfWeek.SUNDAY) == 1
        stats.countMessagesOf(DayOfWeek.TUESDAY) == 0
    }
}
