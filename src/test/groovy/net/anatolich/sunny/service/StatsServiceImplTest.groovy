package net.anatolich.sunny.service

import net.anatolich.sunny.Messages
import net.anatolich.sunny.repository.SmsMessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

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

    def "import messages and count by sender"() {
        given: 'messages are in repository'
        def messages = [
                Messages.createIncomingMessage(),
                Messages.createOutgoingMessage(),
                Messages.createIncomingMessage()]
        messageRepository.save(messages)

        when: 'stats calculated'
        def directionStats = statsService.countByDirection()

        then: 'messages calculated by direction'
        messageRepository.findAll() != null
        directionStats.incoming == 2
        directionStats.outgoing == 1
    }


}
