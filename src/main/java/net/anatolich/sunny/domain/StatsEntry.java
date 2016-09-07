package net.anatolich.sunny.domain;

import lombok.Value;

@Value
public class StatsEntry {
    private final String category;
    private final long value;
}
