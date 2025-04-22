    private static Comparator<CouchbaseResponse> LATENCY_COMPARATOR = new Comparator<CouchbaseResponse>() {
        @Override
        public int compare(final CouchbaseResponse o1, final CouchbaseResponse o2) {
            long x = o1.latency();
            long y = o2.latency();
            return (x < y) ? -1 : ((x == y) ? 0 : 1);
        }
    };

