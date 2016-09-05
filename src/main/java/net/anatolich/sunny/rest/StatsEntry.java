package net.anatolich.sunny.rest;

import lombok.Value;

@Value
public class StatsEntry {
    private final String category;
    private final long value;
}
