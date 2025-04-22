
package com.couchbase.client.core.dcp;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BucketStreamAggregatorState implements Iterable<BucketStreamState> {
    public static final BucketStreamAggregatorState BLANK = new BucketStreamAggregatorState(0);
    private final BucketStreamState[] feeds;

    public BucketStreamAggregatorState(final BucketStreamState[] feeds) {
        this.feeds = feeds;
    }

    public BucketStreamAggregatorState(int numPartitions) {
        feeds = new BucketStreamState[numPartitions];
    }

    public void set(int partition, final BucketStreamState state) {
        feeds[partition] = state;
    }

    @Override
    public Iterator<BucketStreamState> iterator() {
        return new BucketStreamAggregatorStateIterator(feeds);
    }

    public BucketStreamState get(int partition) {
        if (feeds.length > partition) {
            return feeds[partition];
        } else {
            return BucketStreamState.BLANK;
        }
    }

    public class BucketStreamAggregatorStateIterator implements Iterator<BucketStreamState> {
        private final BucketStreamState[] feeds;
        private int index;

        public BucketStreamAggregatorStateIterator(final BucketStreamState[] feeds) {
            this.feeds = feeds;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index != feeds.length;
        }

        @Override
        public BucketStreamState next() {
            if (hasNext()) {
                return feeds[index++];
            } else {
                throw new NoSuchElementException("There are no elements. size = " + feeds.length);
            }
        }
    }
}
