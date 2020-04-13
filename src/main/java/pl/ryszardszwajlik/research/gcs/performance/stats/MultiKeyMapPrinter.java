package pl.ryszardszwajlik.research.gcs.performance.stats;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.springframework.stereotype.Component;

@Component
public class MultiKeyMapPrinter {

    public void print(MultiKeyMap<String, Stat> statistics) {
        printHeader(statistics);
        printBody(statistics);
        System.out.println();
    }

    private void printHeader(MultiKeyMap<String, Stat> statistics) {
        System.out.print("\t");
        statistics.mapIterator().forEachRemaining(multiKey -> {
            String key = multiKey.getKey(0);
            System.out.printf("%s\t", key);
        });
    }

    private void printBody(MultiKeyMap<String, Stat> statistics) {
        Set<String> columnKeys = new HashSet<>();
        Set<String> rowKeys = new HashSet<>();
        statistics.mapIterator().forEachRemaining(multiKey -> {
            columnKeys.add(multiKey.getKey(0));
            rowKeys.add(multiKey.getKey(1));
        });

        for (String rowKey : rowKeys) {
            System.out.println();
            System.out.printf("%s", rowKey);
            for (String columnKey : columnKeys) {
                Stat stat = statistics.get(columnKey, rowKey, "storeFile");
                System.out.printf("\t%s", stat.avg());
            }
        }
    }
}
