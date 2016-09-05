package net.anatolich.sunny.batch.smsbackuprestore

import spock.lang.Specification


class MessageTest extends Specification {

    static final String UNSUPPORTED_TYPE = '3'

    def "create message with invalid type"() {
        when: 'create message with unsupported type'
        new Message("3", System.currentTimeMillis())

        then: 'illegal argument exception thrown'
        def e = thrown(IllegalArgumentException)
        e.message.contains(UNSUPPORTED_TYPE)
    }

    def "set incorrect message type"() {
        given: 'new empty message is created'
        Message message = new Message()

        when: 'trying to set unsupported message type'
        message.setType(UNSUPPORTED_TYPE)

        then: 'illegal argument exception thrown'
        def e = thrown(IllegalArgumentException)
        e.message.contains(UNSUPPORTED_TYPE)

    }
}
