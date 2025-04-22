
package org.eclipse.jgit.revwalk;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class DateRevPriorityQueue extends DateRevQueue {
    private PriorityQueue<RevCommit> priorityQueue;

    public DateRevPriorityQueue() {
        this(false);
    }

    DateRevPriorityQueue(boolean firstParent) {
        this(firstParent
    }

    DateRevPriorityQueue(boolean firstParent
        super(firstParent);
        initPriorityQueue(initialCapacity);
    }

    private void initPriorityQueue(int initialCapacity) {
        priorityQueue = new PriorityQueue<>(initialCapacity
                Comparator.comparingInt(RevCommit::getCommitTime).reversed());
    }

    DateRevPriorityQueue(Generator s) throws MissingObjectException
            IncorrectObjectTypeException
        this(s.firstParent);
        for (; ; ) {
            final RevCommit c = s.next();
            if (c == null)
                break;
            add(c);
        }
    }

    void setCapacity(int capacity) {
        if(capacity > 0) {
            Stream<RevCommit> currentElements = priorityQueue.stream();
            initPriorityQueue(capacity);
            currentElements.forEach(priorityQueue::add);
        }
    }

    @Override
    public void add(RevCommit c) {
        priorityQueue.add(c);
    }

    @Override
    public RevCommit next() {
        return priorityQueue.poll();
    }

    public RevCommit peek() {
        return priorityQueue.peek();
    }

    @Override
    public void clear() {
        priorityQueue.clear();
    }

    @Override
    boolean everbodyHasFlag(int f) {
        return !priorityQueue.stream().filter(c -> (c.flags & f) == 0).findAny().isPresent();
    }

    @Override
    boolean anybodyHasFlag(int f) {
        return priorityQueue.stream().filter(c -> (c.flags & f) != 0).findAny().isPresent();
    }

    @Override
    int outputType() {
        return outputType | SORT_COMMIT_TIME_DESC;
    }

    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        priorityQueue.forEach(c -> describe(s
        return s.toString();
    }
}
