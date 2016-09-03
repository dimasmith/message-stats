package net.anatolich.sunny.batch

import org.springframework.batch.core.BatchStatus
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.jdbc.JdbcTestUtils
import spock.lang.Specification

import javax.sql.DataSource

@SpringBootTest
@ContextConfiguration(classes = [BatchTestConfiguration])
@ActiveProfiles("import")
class ImportBatchFeatureTest extends Specification {

    public static final String MESSAGE_TABLE_NAME = 'SMS_MESSAGE'
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils

    @Autowired
    DataSource dataSource

    @Value("classpath:smsbackuprestore/messages.xml")
    Resource messageFile;

    private JdbcTemplate jdbcTemplate

    void setup() {
        jdbcTemplate = new JdbcTemplate(dataSource)
    }

    def "import messages"() {
        setup: 'clean up tables'
        JdbcTestUtils.deleteFromTables(jdbcTemplate, MESSAGE_TABLE_NAME)

        when: 'messages are imported'
        def status = jobLauncherTestUtils.launchJob().getStatus()

        then: 'job is completed'
        status == BatchStatus.COMPLETED

        and: 'All messages are imported'
        JdbcTestUtils.countRowsInTable(jdbcTemplate, MESSAGE_TABLE_NAME) == 27
    }
}
