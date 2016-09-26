package net.anatolich.sunny.domain;

import java.util.Arrays;
import java.util.List;

public class SenderStats extends AbstractStats<Direction> {

    private final int incoming;
    private final int outgoing;
    private final List<StatsEntry> stats;

    public SenderStats(int incoming, int outgoing) {
        this.incoming = incoming;
        this.outgoing = outgoing;
        this.stats = createDataSeries();
    }

    @Override
    public List<StatsEntry> getDataSeries() {
        return stats;
    }

    @Override
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

    @Override
    protected String getCategoryName(Direction direction) {
        return direction.name();
    }

    @Override
    protected List<Direction> createCategories() {
        return Arrays.asList(Direction.IN, Direction.OUT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SenderStats that = (SenderStats) o;

        if (incoming != that.incoming) return false;
        return outgoing == that.outgoing;

    }

    @Override
    public int hashCode() {
        int result = incoming;
        result = 31 * result + outgoing;
        return result;
    }
}
