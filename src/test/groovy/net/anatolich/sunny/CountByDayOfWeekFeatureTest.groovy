package net.anatolich.sunny

import net.anatolich.sunny.batch.ImportJobLauncher
import net.anatolich.sunny.repository.SmsMessageRepository
import org.hamcrest.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration
class CountByDayOfWeekFeatureTest extends Specification {

    @Autowired
    ImportJobLauncher messageImporter

    @Autowired
    MockMvc mockMvc

    @Value("classpath:smsbackuprestore/messages.xml")
    Resource sampleMessages

    @Autowired
    SmsMessageRepository messageRepository

    void setup() {
        messageRepository.deleteAll()
    }

    def "count messages by day of week"() {
        setup: 'import example messages'
        messageImporter.importMessages(sampleMessages.getURL().toExternalForm())

        when: 'get statistics from endpoint'
        def response = mockMvc.perform(get('/v1/stats/byDayOfWeek').accept(APPLICATION_JSON_UTF8))

        then: 'statistics counted correctly'
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.stats[0].category', Matchers.is('MONDAY')))
                .andExpect(jsonPath('$.stats[0].value', Matchers.is(3)))
                .andExpect(jsonPath('$.stats[1].category', Matchers.is('TUESDAY')))
                .andExpect(jsonPath('$.stats[1].value', Matchers.is(8)))
                .andExpect(jsonPath('$.stats[2].category', Matchers.is('WEDNESDAY')))
                .andExpect(jsonPath('$.stats[2].value', Matchers.is(3)))
                .andExpect(jsonPath('$.stats[3].category', Matchers.is('THURSDAY')))
                .andExpect(jsonPath('$.stats[3].value', Matchers.is(5)))
                .andExpect(jsonPath('$.stats[4].category', Matchers.is('FRIDAY')))
                .andExpect(jsonPath('$.stats[4].value', Matchers.is(4)))
                .andExpect(jsonPath('$.stats[5].category', Matchers.is('SATURDAY')))
                .andExpect(jsonPath('$.stats[5].value', Matchers.is(4)))
                .andExpect(jsonPath('$.stats[6].category', Matchers.is('SUNDAY')))
                .andExpect(jsonPath('$.stats[6].value', Matchers.is(0)))

    }
}
