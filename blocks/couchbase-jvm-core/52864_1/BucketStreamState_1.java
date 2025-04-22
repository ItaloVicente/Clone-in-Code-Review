
package com.couchbase.client.core.dcp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BucketStreamAggregatorState implements Iterable<BucketStreamState> {
    public static final BucketStreamAggregatorState BLANK = new BucketStreamAggregatorState();

    private final List<BucketStreamState> feeds;

    public BucketStreamAggregatorState() {
        this(0);
    }

    public BucketStreamAggregatorState(int numPartitions) {
        feeds = new ArrayList<BucketStreamState>(numPartitions);
    }

    public void feedState(int partition, final BucketStreamState state) {
        feeds.set(partition, state);
    }

    @Override
    public Iterator<BucketStreamState> iterator() {
        return feeds.iterator();
    }

    public BucketStreamState get(int partition) {
        if (feeds.size() > partition) {
            return feeds.get(partition);
        } else {
            return BucketStreamState.BLANK;
        }
    }
}
