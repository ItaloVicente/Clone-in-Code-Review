        taskInfo = null;
    }

    /**
     * Compare the the job of the receiver to job2.
     *
     * @param jobInfo
     *            The info we are comparing to
     * @return @see Comparable#compareTo(java.lang.Object)
     */
    private int compareJobs(JobInfo jobInfo) {

        Job job2 = jobInfo.getJob();

        if (job.isUser()) {
            if (!job2.isUser()) {
