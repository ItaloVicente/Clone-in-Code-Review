	        FinishedJobs finishedJobs) {
        this.job = enclosingJob;
        this.progressManager = progressManager;
        this.finishedJobs = finishedJobs;
    }

    /**
     * Add the subtask to the receiver.
     *
     * @param subTaskName
     */
    void addSubTask(String subTaskName) {
        children.add(new SubTaskInfo(this, subTaskName));
    }

    /**
     * Add the amount of work to the job info.
     *
     * @param workIncrement
     */
    void addWork(double workIncrement) {
        if (taskInfo == null) {
