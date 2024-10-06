package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;


public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Требуется реализовать интерфейс TicketManager в соответствии с JavaDoc описанием.
     * Реализации очередей из стандартной библиотеки не используем.
     */
    public TicketManager managerFabric() {
        return null;
    }


    /**
     * Задача со звездочкой (опционально)
     * <br/>
     * На вход строки:
     * номер_впереди : номер_сзади
     * Если впереди или сзади никого нет - то 0, для первого и последнего в очереди.
     * На выход нужно восстановить порядок номеров в очереди.
     * <p>
     * В тестах генератор тестовых данных (очереди и пары).
     *
     * @see <a href="https://codeforces.com/problemset/problem/490/B?locale=ru">https://codeforces.com/problemset/problem/490/B?locale=ru</a>
     */
    public List<Integer> check(List<String> records) {
        var capacity = records.size();
        var pairs = new HashMap<Integer, Integer>(capacity);
        var auxNext = new HashSet<Integer>(capacity);
        var auxPrev = new HashSet<Integer>(capacity);
        var result = new ArrayList<Integer>(capacity);
        var pattern = Pattern.compile("(\\d+)(\\D+)(\\d+)");

        for (String s : records) {
            var matcher = pattern.matcher(s);
            if (!matcher.find()) throw new IllegalArgumentException(s);
            var next = Integer.valueOf(matcher.group(1));
            var prev = Integer.valueOf(matcher.group(3));
            pairs.put(next, prev);
            auxNext.add(next);
            auxPrev.add(prev);
        }
        auxNext.removeAll(auxPrev);

        var odd = auxNext.iterator().next();
        var even = pairs.remove(0);
        while (null != odd && 0 != odd) {
            result.add(odd);
            odd = pairs.remove(odd);
            if (null != even && 0 != even) {
                result.add(even);
                even = pairs.remove(even);
            }
        }

        return result;
    }

}
