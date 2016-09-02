package net.anatolich.sunny.service;

import net.anatolich.sunny.domain.SenderStats;

public interface StatsService {
    SenderStats countByDirection();
}
