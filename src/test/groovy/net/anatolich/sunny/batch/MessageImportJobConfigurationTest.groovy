package net.anatolich.sunny.batch

import net.anatolich.sunny.domain.Direction
import net.anatolich.sunny.domain.SmsMessage
import net.anatolich.sunny.repository.SmsMessageRepository
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest
@ContextConfiguration
class MessageImportJobConfigurationTest extends Specification {

    @Autowired
    ItemWriter messageWriter

    @Autowired
    SmsMessageRepository messageRepository

    void setup() {
        messageRepository.deleteAll()
    }

    def "message is stored correctly"() {
        given: 'message to be persisted'
        SmsMessage message = SmsMessage.builder()
                .direction(Direction.IN)
                .deliveryTime(LocalDateTime.now())
                .build()

        when: 'message is imported'
        messageWriter.write([message])
        def loadedMessage = messageRepository.findAll().iterator().next()
        message.setId(loadedMessage.getId()) // syncing generated ids

        then: 'all fields should be stored to database'
        loadedMessage == message
    }
}
