        private final SortedSet<ThresholdLogSpan> kvZombieSet;
        private final SortedSet<ThresholdLogSpan> n1qlZombieSet;
        private final SortedSet<ThresholdLogSpan> viewZombieSet;
        private final SortedSet<ThresholdLogSpan> ftsZombieSet;
        private final SortedSet<ThresholdLogSpan> analyticsZombieSet;

        Worker() {
            kvThresholdSet = new TreeSet<ThresholdLogSpan>();
            n1qlThresholdSet = new TreeSet<ThresholdLogSpan>();
            viewThresholdSet = new TreeSet<ThresholdLogSpan>();
            ftsThresholdSet = new TreeSet<ThresholdLogSpan>();
            analyticsThresholdSet = new TreeSet<ThresholdLogSpan>();

            kvZombieSet = new TreeSet<ThresholdLogSpan>();
            n1qlZombieSet = new TreeSet<ThresholdLogSpan>();
            viewZombieSet = new TreeSet<ThresholdLogSpan>();
            ftsZombieSet = new TreeSet<ThresholdLogSpan>();
            analyticsZombieSet = new TreeSet<ThresholdLogSpan>();
        }
