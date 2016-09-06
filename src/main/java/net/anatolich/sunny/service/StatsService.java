package net.anatolich.sunny.service;

import net.anatolich.sunny.domain.SenderStats;
import net.anatolich.sunny.domain.DayOfWeekStats;

public interface StatsService {
    SenderStats countByDirection();

    DayOfWeekStats calculateStatsByDayOfWeek();

    MonthStats calculateStatsByMonth();
}
