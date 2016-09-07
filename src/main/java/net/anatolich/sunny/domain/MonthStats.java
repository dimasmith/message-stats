package net.anatolich.sunny.domain;

import net.anatolich.sunny.rest.StatsEntry;

import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonthStats {

    private final Map<Month, Long> data;
    private final List<StatsEntry> stats;

    private MonthStats(Map<Month, Long> data) {
        this.data = Collections.unmodifiableMap(data);
        this.stats = Arrays.stream(Month.values())
                .map(month -> new StatsEntry(month.name(), countMessagesOf(month)))
                .collect(Collectors.toList());
    }

    public static MonthStats of(Map<Month, Long> stats) {
        return new MonthStats(stats);
    }

    public long countMessagesOf(Month month) {
        return data.getOrDefault(month, 0L);
    }

    public List<StatsEntry> getStats() {
        return stats;
    }
}
