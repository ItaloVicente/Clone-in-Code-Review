        /**
         * Create a new collector.
         * @param subTask
         * @param work
         * @param monitor
         */
        public Collector(String subTask, double work, IProgressMonitor monitor) {
            this.subTask = subTask;
            this.worked = work;
            this.monitor = monitor;
        }
