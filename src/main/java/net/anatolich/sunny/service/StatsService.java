package net.anatolich.sunny.service;

import net.anatolich.sunny.domain.MonthStats;
import net.anatolich.sunny.domain.DayOfWeekStats;
import net.anatolich.sunny.domain.Stats;

public interface StatsService {
    Stats countByDirection();

    DayOfWeekStats calculateStatsByDayOfWeek();

    MonthStats calculateStatsByMonth();
}
