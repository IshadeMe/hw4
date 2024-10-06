package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeWorkTest {

    HomeWork homeWork = new HomeWork();

    @Test
    void managerFabric() {
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 99, 100, 101, 1000, 200000, 500001})
    void check(int len) {
        List<Integer> expectedQueue = generateQueue(len);
        List<String> pairs = generatePairs(expectedQueue);
        assertEquals(expectedQueue, homeWork.check(pairs));
    }

    private List<String> generatePairs(List<Integer> expectedQueue) {
        List<String> pairs = new ArrayList<>();
        Function<Integer, Integer> map = (x) -> (x < 0 || x >= expectedQueue.size()) ? 0 : expectedQueue.get(x);

        for (int i = 0;
             i < expectedQueue.size(); i++) {
            pairs.add(String.format("%d:%d", map.apply(i - 1), map.apply(i + 1)));
        }
        Collections.shuffle(pairs);
        return pairs;
    }

    private List<Integer> generateQueue(int length) {
        return new Random(OffsetDateTime.now().getNano())
                .ints(1, length * 100)
                .distinct()
                .limit(length)
                .boxed()
                .collect(Collectors.toList());
    }


}