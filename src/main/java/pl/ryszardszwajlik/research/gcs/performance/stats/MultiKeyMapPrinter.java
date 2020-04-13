package pl.ryszardszwajlik.research.gcs.performance.stats;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.springframework.stereotype.Component;

@Component
public class MultiKeyMapPrinter {

    public void print(MultiKeyMap<String, Stat> statistics, String type) {
        Set<String> columnKeys = getColumnKeys(statistics);
        Set<String> rowKeys = getRowKeys(statistics);
        System.out.println(type);
        printHeader(columnKeys);
        printBody(statistics, columnKeys, rowKeys, type);
        System.out.println();
    }

    private void printHeader(Set<String> columnKeys) {
        System.out.print("\t");
        columnKeys.forEach(key -> System.out.printf("%s\t", key));
    }

    private void printBody(MultiKeyMap<String, Stat> statistics, Set<String> columnKeys,
            Set<String> rowKeys, String type) {
        for (String rowKey : rowKeys) {
            System.out.println();
            System.out.printf("%s", rowKey);
            for (String columnKey : columnKeys) {
                Stat stat = statistics.get(columnKey, rowKey, type);
                System.out.printf("\t%s", stat.avg());
            }
        }
    }

    private Set<String> getColumnKeys(MultiKeyMap<String, Stat> statistics) {
        Set<String> rowKeys = new LinkedHashSet<>();
        statistics.mapIterator().forEachRemaining(multiKey -> rowKeys.add(multiKey.getKey(0)));
        return rowKeys;
    }

    private Set<String> getRowKeys(MultiKeyMap<String, Stat> statistics) {
        Set<String> columnKeys = new LinkedHashSet<>();
        statistics.mapIterator().forEachRemaining(multiKey -> columnKeys.add(multiKey.getKey(1)));
        return columnKeys;
    }
}
