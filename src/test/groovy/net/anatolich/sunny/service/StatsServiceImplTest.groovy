package net.anatolich.sunny.service

import net.anatolich.sunny.Messages
import net.anatolich.sunny.repository.SmsMessageRepository
import net.anatolich.sunny.domain.DayOfWeekStats
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.DayOfWeek

@SpringBootTest
@ContextConfiguration
class StatsServiceImplTest extends Specification {

    @Autowired
    SmsMessageRepository messageRepository

    StatsService statsService

    void setup() {
        statsService = new StatsServiceImpl(messageRepository)
        messageRepository.deleteAll()
    }

    def "count messages by sender"() {
        given: 'messages are in repository'
        def messages = [
                Messages.createIncomingMessage(),
                Messages.createOutgoingMessage(),
                Messages.createIncomingMessage()]
        messageRepository.save(messages)

        when: 'stats calculated'
        def directionStats = statsService.countByDirection()

        then: 'messages calculated by direction'
        directionStats.incoming == 2
        directionStats.outgoing == 1
    }

    def "count messages send by days of week"() {
        given: 'messages are in repository'
        def messages = [
                Messages.createMessageOn(DayOfWeek.FRIDAY),
                Messages.createMessageOn(DayOfWeek.FRIDAY),
                Messages.createMessageOn(DayOfWeek.SUNDAY),
                Messages.createMessageOn(DayOfWeek.MONDAY)]
        messageRepository.save(messages)

        when: 'stats calculated'
        DayOfWeekStats stats = statsService.calculateStatsByDayOfWeek()

        then: 'messages calculated by direction'
        stats.countMessagesOf(DayOfWeek.FRIDAY) == 2
        stats.countMessagesOf(DayOfWeek.SUNDAY) == 1
        stats.countMessagesOf(DayOfWeek.TUESDAY) == 0
    }
}
