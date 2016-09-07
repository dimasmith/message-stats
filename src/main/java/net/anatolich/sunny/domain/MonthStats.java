package net.anatolich.sunny.domain;

import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MonthStats extends AbstractStats<Month> {

    private final Map<Month, Long> data;
    private final List<StatsEntry> stats;

    private MonthStats(Map<Month, Long> data) {
        this.data = Collections.unmodifiableMap(data);
        this.stats = createDataSeries();
    }

    public static MonthStats of(Map<Month, Long> stats) {
        return new MonthStats(stats);
    }

    @Override
    protected String getCategoryName(Month month) {
        return month.name();
    }

    @Override
    protected List<Month> createCategories() {
        return Arrays.asList(Month.values());
    }

    @Override
    public long countMessagesOf(Month month) {
        return data.getOrDefault(month, 0L);
    }

    @Override
    public List<StatsEntry> getDataSeries() {
        return stats;
    }

}
