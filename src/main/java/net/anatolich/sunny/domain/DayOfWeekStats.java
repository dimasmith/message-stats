package net.anatolich.sunny.domain;

import lombok.Value;
import net.anatolich.sunny.rest.StatsEntry;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Value
public class DayOfWeekStats {

    private final Map<DayOfWeek, Long> data;
    private final List<StatsEntry> stats;

    private DayOfWeekStats(Map<DayOfWeek, Long> data) {
        this.data = data;
        this.stats = Arrays.stream(DayOfWeek.values())
                .map(dayOfWeek -> new StatsEntry(dayOfWeek.name(), countMessagesOf(dayOfWeek)))
                .collect(Collectors.toList());
    }

    public static DayOfWeekStats of(Map<DayOfWeek, Long> statMap) {
        return new DayOfWeekStats(statMap);
    }

    public long countMessagesOf(DayOfWeek dayOfWeek) {
        return data.getOrDefault(dayOfWeek, 0L);
    }
}
