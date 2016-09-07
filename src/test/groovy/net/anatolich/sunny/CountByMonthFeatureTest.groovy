package net.anatolich.sunny

import net.anatolich.sunny.batch.ImportJobLauncher
import net.anatolich.sunny.repository.SmsMessageRepository
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration
class CountByMonthFeatureTest extends Specification {

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
        def response = mockMvc.perform(get('/v1/stats/byMonth').accept(APPLICATION_JSON_UTF8))

        then: 'statistics counted correctly'
        response.andExpect(status().isOk())
        def matcher = new JsonAsserts.StatEntrySeriesMatcher(response)
        matcher.assertNextEntry('JANUARY', 0)
        matcher.assertNextEntry('FEBRUARY', 0)
        matcher.assertNextEntry('MARCH', 0)
        matcher.assertNextEntry('APRIL', 0)
        matcher.assertNextEntry('MAY', 0)
        matcher.assertNextEntry('JUNE', 0)
        matcher.assertNextEntry('JULY', 0)
        matcher.assertNextEntry('AUGUST', 22)
        matcher.assertNextEntry('SEPTEMBER', 5)
        matcher.assertNextEntry('OCTOBER', 0)
        matcher.assertNextEntry('NOVEMBER', 0)
        matcher.assertNextEntry('DECEMBER', 0L)
    }
}
