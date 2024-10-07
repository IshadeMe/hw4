package org.example;

public class TicketManagerImpl implements TicketManager {

    private final Ticket[] heap;
    private int size = 0;
    private static final String PENSION = "pension";

    public TicketManagerImpl(Ticket[] heap) {
        this.heap = heap;
    }

    /**
     * Регистрация талона в очереди
     */
    @Override
    public void add(Ticket ticket) {
        heap[size] = ticket;
        checkRules(size++);
    }

    /**
     * Получение следующего талона или null если очередь пуста
     * В первую очередь идут талоны с Ticket.type = "pension", далее все остальные.
     * Внутри групп ("pension" или остальные) упорядочиваем по времени регистрации Ticket.registerTime по возрастанию (FIFO)
     */
    @Override
    public Ticket next() {
        if (size == 0) {
            return null;
        }
        var res = heap[0];
        int newSize = --size;
        heap[0] = heap[newSize];
        heap[newSize] = null;
        heapify();
        return res;
    }

    private void checkRules(int cur) {
        var par = getParent(cur);
        var value = heap[cur];
        while (cur > 0 && bigger(value, heap[par])) {
            heap[cur] = heap[par];
            cur = par;
            par = getParent(cur);
        }
        heap[cur] = value;
    }

    private void heapify() {
        var i = 0;
        while (heap[i] != null) {
            var largest = i;
            var left = 2 * i + 1;
            var right = 2 * i + 2;
            if (left < size && bigger(heap[left], heap[largest])) {
                largest = left;
            }
            if (right < size && bigger(heap[right], heap[largest])) {
                largest = right;
            }
            if (largest == i) {
                break;
            }
            var tmp = heap[i];
            heap[i] = heap[largest];
            heap[largest] = tmp;
            i = largest;
        }
    }

    private int getParent(int i) {
        return (i - 1) / 2;
    }

    private boolean bigger(Ticket t1, Ticket t2) {
        if (null == t1) {
            return false;
        }
        var calc = (PENSION.equals(t1.type) ? 1 : 0)
                - (PENSION.equals(t2.type) ? 1 : 0);
        return calc > 0 || (0 == calc && t1.registerTime.isBefore(t2.registerTime));
    }

}
