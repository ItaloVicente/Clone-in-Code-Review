        private final SortedSet<ThresholdLogSpan> kvZombieSet = new TreeSet<ThresholdLogSpan>();
        private final SortedSet<ThresholdLogSpan> n1qlZombieSet = new TreeSet<ThresholdLogSpan>();
        private final SortedSet<ThresholdLogSpan> viewZombieSet = new TreeSet<ThresholdLogSpan>();
        private final SortedSet<ThresholdLogSpan> ftsZombieSet = new TreeSet<ThresholdLogSpan>();
        private final SortedSet<ThresholdLogSpan> analyticsZombieSet = new TreeSet<ThresholdLogSpan>();

        private long lastZombieLog;
        private boolean hasZombieWritten;
