package net.anatolich.sunny.batch.smsbackuprestore

import net.anatolich.sunny.domain.Direction
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.Month

class MessageConverterTest extends Specification {

    def converter = new MessageConverter()

    def "convert message direction"() {
        given: 'incoming and outgoing message'
        def incoming = Message.builder()
                .type('1').build()
        def outgoing = Message.builder()
                .type('2').build()

        expect: 'numeric types properly mapped to direction'
        converter.process(incoming).direction == Direction.IN
        converter.process(outgoing).direction == Direction.OUT
    }

    def "exception on unsupported direction"() {
        given: 'message with unknown direction'
        def unknown = Message.builder().type('3').build()

        when: 'converting to message'
        converter.process(unknown)

        then: 'exception thrown'
        thrown(IllegalArgumentException)
    }

    def "convert delivery time"() {
        given: 'message received on specific date'
        def message = Message.builder().type('1').date(1472238032937).build()

        when: 'parsing message'
        def smsMessage = converter.process(message)

        then: 'converted message must have proper date ofsetted to Europe/Kiev timezone'
        smsMessage.getDeliveryTime().isEqual(LocalDateTime.of(2016, Month.AUGUST, 26, 22, 0, 32, 937_000_000))
    }
}
