package net.anatolich.sunny.domain;

import java.util.List;


public interface Stats<T> {

    /**
     * List ordered stats series entries.
     * List contains entry for all possible stats category (e.g. entry for each day of week)
     */
    List<StatsEntry> getDataSeries();

    /**
     * Get message count by category.
     * @param category item to group messages by
     * @return count of messages or 0 if no messages present for category
     */
    long countMessagesOf(T category);
}
