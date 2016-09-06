package net.anatolich.sunny;

import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class JsonAsserts {

    public static class StatEntrySeriesMatcher {
        private final ResultActions resultActions;
        private int currentEntryNumber = 0;

        public StatEntrySeriesMatcher(ResultActions resultActions) {
            this.resultActions = resultActions;
        }

        public void assertEntry(int number, String category, Long value) throws Exception {
            this.resultActions
                    .andExpect(jsonPath(String.format("$.stats[%d].category", number), Matchers.is(category)))
                    .andExpect(jsonPath(String.format("$.stats[%d].value", number), Matchers.is(value.intValue())));

        }

        public void assertNextEntry(String category, Long value) throws Exception {
            assertEntry(currentEntryNumber, category, value);
            currentEntryNumber++;
        }

        public void resetCounter() {
            currentEntryNumber = 0;
        }
    }
}
