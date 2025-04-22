    /**
     *  Creates a collector object to accumulate work and subtask calls.
     * @param subTask
     * @param work
     */
    private void createCollector(String subTask, double work) {
        collector = new Collector(subTask, work, getWrappedProgressMonitor());
        display.asyncExec(collector);
    }
