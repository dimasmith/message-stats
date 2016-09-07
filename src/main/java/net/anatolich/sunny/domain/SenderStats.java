package net.anatolich.sunny.domain;

import net.anatolich.sunny.rest.StatsEntry;

import java.util.Arrays;
import java.util.List;

public class SenderStats {

    private final int incoming;
    private final int outgoing;
    private final List<StatsEntry> stats;

    public SenderStats(int incoming, int outgoing) {
        this.incoming = incoming;
        this.outgoing = outgoing;
        this.stats = Arrays.asList(
                new StatsEntry(Direction.IN.name(), incoming),
                new StatsEntry(Direction.OUT.name(), outgoing));
    }

    public List<StatsEntry> getStats() {
        return stats;
    }

    public long countMessagesOf(Direction direction) {
        switch (direction) {
            case IN:
                return incoming;
            case OUT:
                return outgoing;
            default:
                return 0;
        }
    }

}
