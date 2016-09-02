package net.anatolich.sunny

import net.anatolich.sunny.domain.Direction
import net.anatolich.sunny.domain.SenderStats
import net.anatolich.sunny.domain.SmsMessage
import net.anatolich.sunny.repository.SmsMessageRepository
import net.anatolich.sunny.service.StatsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [SmsStatsApplication])
class CountBySenderFeatureTest extends Specification {

    @Autowired
    SmsMessageRepository messageRepository

    @Autowired
    StatsService statsService

    def "import messages and count by sender"() {
        setup: 'create test messages'
        def messages = [createIncomingMessage(), createOutgoingMessage(), createIncomingMessage()]
        when: 'messages are imported'
        importMessage(messages)
        def directionStats = countByDirection()
        then: 'messages calculated by direction'
        directionStats.incoming == 2
        directionStats.outgoing == 1
    }

    SenderStats countByDirection() {
        return statsService.countByDirection()
    }

    def importMessage(Collection<SmsMessage> smsMessages) {
        messageRepository.save(smsMessages)
    }

    SmsMessage createOutgoingMessage() {
        return SmsMessage.builder()
                .direction(Direction.OUT)
                .build()
    }

    SmsMessage createIncomingMessage() {
        return SmsMessage.builder()
                .direction(Direction.IN)
                .build()
    }
}
