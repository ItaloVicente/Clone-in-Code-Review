        MutableActivityManager clone = new MutableActivityManager(advisor, activityRegistry);
        clone.setEnabledActivityIds(getEnabledActivityIds());
        return clone;
    }

    /**
     * Return the identifier update job.
     *
     * @return the job
     * @since 3.1
     */
    private Job getUpdateJob() {
        if (deferredIdentifierJob == null) {
