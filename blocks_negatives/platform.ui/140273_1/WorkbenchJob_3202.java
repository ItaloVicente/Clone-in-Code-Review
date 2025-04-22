    /**
     * Add a job change listeners that handles a done
     * event if the result was IStatus.OK.
     *
     */
    private void addDefaultJobChangeListener() {
        addJobChangeListener(new JobChangeAdapter() {
