package net.anatolich.sunny.domain;

import lombok.Value;
import net.anatolich.sunny.rest.StatsEntry;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Value
public class DayOfWeekStats {

    private final List<StatsEntry> stats;

    public DayOfWeekStats() {
        stats = new ArrayList<>();
        stats.add(new StatsEntry(DayOfWeek.SUNDAY.name(), 0));
        stats.add(new StatsEntry(DayOfWeek.MONDAY.name(), 1));
        stats.add(new StatsEntry(DayOfWeek.TUESDAY.name(), 2));
        stats.add(new StatsEntry(DayOfWeek.WEDNESDAY.name(), 3));
        stats.add(new StatsEntry(DayOfWeek.THURSDAY.name(), 3));
        stats.add(new StatsEntry(DayOfWeek.FRIDAY.name(), 2));
        stats.add(new StatsEntry(DayOfWeek.SATURDAY.name(), 1));
    }

    private DayOfWeekStats(List<StatsEntry> entries) {
        stats = entries;
    }

    public static DayOfWeekStats of(Map<DayOfWeek, Long> statMap) {
        final List<StatsEntry> weekDayEntries = Arrays.stream(DayOfWeek.values())
                .map(dayOfWeek -> new StatsEntry(dayOfWeek.name(), statMap.getOrDefault(dayOfWeek, 0L)))
                .collect(Collectors.toList());
        return new DayOfWeekStats(weekDayEntries);
    }

    public long countMessagesOf(DayOfWeek dayOfWeek) {
        return stats.stream().filter(statsEntry -> statsEntry.getCategory().equals(dayOfWeek.name()))
                .mapToLong(StatsEntry::getValue)
                .findFirst()
                .orElse(0);
    }
}
