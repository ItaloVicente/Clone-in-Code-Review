    /**
     * Return whether or not this is the update job. This is used to determine
     * if a final refresh is required.
     *
     * @param job
     * @return boolean <code>true</true> if this is the
     * update job
     */
    boolean isUpdateJob(Job job) {
        return job.equals(updateJob);
    }
