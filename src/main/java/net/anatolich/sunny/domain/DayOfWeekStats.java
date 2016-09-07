package net.anatolich.sunny.domain;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DayOfWeekStats extends AbstractStats<DayOfWeek> {

    private final Map<DayOfWeek, Long> data;
    private final List<StatsEntry> stats;

    private DayOfWeekStats(Map<DayOfWeek, Long> data) {
        this.data = data;
        this.stats = createDataSeries();
    }

    @Override
    public List<StatsEntry> getDataSeries() {
        return stats;
    }

    public static DayOfWeekStats of(Map<DayOfWeek, Long> statMap) {
        return new DayOfWeekStats(statMap);
    }

    @Override
    protected String getCategoryName(DayOfWeek dayOfWeek) {
        return dayOfWeek.name();
    }

    @Override
    protected List<DayOfWeek> createCategories() {
        return Arrays.asList(DayOfWeek.values());
    }

    public long countMessagesOf(DayOfWeek dayOfWeek) {
        return data.getOrDefault(dayOfWeek, 0L);
    }
}
