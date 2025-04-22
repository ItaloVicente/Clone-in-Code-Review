    /**
     * Helper class to iterate over {@link BucketStreamAggregatorState}.
     */
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
