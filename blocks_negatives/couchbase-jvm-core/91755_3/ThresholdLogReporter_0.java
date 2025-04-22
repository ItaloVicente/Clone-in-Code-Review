        private long lastThresholdLog;
        private boolean hasThresholdWritten;
        private final SortedSet<ThresholdLogSpan> kvThresholdSet;
        private final SortedSet<ThresholdLogSpan> n1qlThresholdSet;
        private final SortedSet<ThresholdLogSpan> viewThresholdSet;
        private final SortedSet<ThresholdLogSpan> ftsThresholdSet;
        private final SortedSet<ThresholdLogSpan> analyticsThresholdSet;
