
package util;

import java.util.concurrent.atomic.AtomicInteger;

public final class idGenerator {
    private final AtomicInteger next;

    public idGenerator(int startInclusive) {
        this.next = new AtomicInteger(startInclusive);
    }

    public int nextId() {
        return next.getAndIncrement();
    }
}
