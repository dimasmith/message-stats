package net.anatolich.sunny.batch.smsbackuprestore

import org.springframework.batch.item.ExecutionContext
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import spock.lang.Specification

class MessageReaderTest extends Specification {

    def "read message"() {
        given: 'xml file containing single message'
        Resource xmlWithMessages = new ClassPathResource('smsbackuprestore/message.xml')

        and: 'message reader reading this file'
        MessageReader reader = new MessageReader()
        reader.setResource(xmlWithMessages)

        when: 'reader reads message'
        reader.open(new ExecutionContext())
        Message message = reader.read()
        reader.close()

        then: 'message is present and fully populated'
        message != null
        message.type == '1'
        message.date == 1472238032937L
    }
}
