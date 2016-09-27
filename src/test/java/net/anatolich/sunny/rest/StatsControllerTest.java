package net.anatolich.sunny.rest;

import net.anatolich.sunny.domain.SenderStats;
import net.anatolich.sunny.service.StatsService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatsController.class)
@RunWith(SpringRunner.class)
public class StatsControllerTest {

    @MockBean
    StatsService statsService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getStatsBySender() throws Exception {
        given(statsService.countByDirection()).willReturn(new SenderStats(5, 10));

        mockMvc.perform(get("/v1/stats/bySender").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("dataSeries[0].value", Matchers.equalTo(5)))
                .andExpect(jsonPath("dataSeries[1].value", Matchers.equalTo(10)));
    }
}