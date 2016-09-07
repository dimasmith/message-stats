package net.anatolich.sunny.domain;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility base class for stats implementations.
 * @param <T>
 */
abstract class AbstractStats<T> implements Stats<T> {

    /**
     * Extract name from category.
     * @param category any item stats data is grouped by
     */
    protected abstract String getCategoryName(T category);

    /**
     * Provide all categories in order.
     * Stats entries for those categories will be generated
     * with {@link #createDataSeries()}
     */
    protected abstract List<T> createCategories();

    final List<StatsEntry> createDataSeries() {
        return createCategories().stream()
                .map(category -> new StatsEntry(getCategoryName(category), countMessagesOf(category)))
                .collect(Collectors.toList());
    }
}
