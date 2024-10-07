package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TicketManagerImplTest {
    private final HomeWork hw = new HomeWork();
    private final static String PENSION = "pension";
    private final static String REGULAR = "other";

    @Test
    void emptyQueue() {
        var queue = hw.managerFabric(5);
        assertNull(queue.next());
    }

    @Test
    void otherLast() {
        var queue = hw.managerFabric(100);
        var other = new Ticket(REGULAR);

        queue.add(new Ticket(PENSION));
        queue.add(other);
        queue.add(new Ticket(PENSION));
        queue.next();
        queue.next();

        assertEquals(other, queue.next());
        assertNull(queue.next());
    }

    @Test
    void otherGroupOrdered() throws InterruptedException {
        var queue = hw.managerFabric(50);
        var first = new Ticket(REGULAR);
        Thread.sleep(200);
        var second = new Ticket(REGULAR);

        queue.add(second);
        queue.add(first);

        assertEquals(first, queue.next());
        assertEquals(second, queue.next());
        assertNull(queue.next());
    }

    @Test
    void pensGroupOrdered() throws InterruptedException {
        var queue = hw.managerFabric(50);
        var first = new Ticket(PENSION);
        Thread.sleep(200);
        var second = new Ticket(PENSION);

        queue.add(first);
        queue.add(second);

        assertEquals(first, queue.next());
        assertEquals(second, queue.next());
        assertNull(queue.next());
    }

    @Test
    void multiGroupOrdered() throws InterruptedException {
        var queue = hw.managerFabric(50);
        var third = new Ticket(REGULAR);
        Thread.sleep(200);
        var first = new Ticket(PENSION);
        Thread.sleep(200);
        var fourth = new Ticket(REGULAR);
        Thread.sleep(200);
        var second = new Ticket(PENSION);

        queue.add(third);
        queue.add(first);
        queue.add(fourth);
        queue.add(second);

        assertEquals(first, queue.next());
        assertEquals(second, queue.next());
        assertEquals(third, queue.next());
        assertEquals(fourth, queue.next());
        assertNull(queue.next());
    }

    @Test
    void nextAndAdd() throws InterruptedException {
        var queue = hw.managerFabric(50);
        var first = new Ticket(PENSION);
        Thread.sleep(200);
        var second = new Ticket(PENSION);
        Thread.sleep(200);

        queue.add(first);
        queue.add(second);

        assertEquals(first, queue.next());
        queue.add(first);
        assertEquals(first, queue.next());
    }
}