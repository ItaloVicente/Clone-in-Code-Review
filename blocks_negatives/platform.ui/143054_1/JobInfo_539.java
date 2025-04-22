        this.canceled = true;
        this.job.cancel();
        progressManager.refreshJobInfo(this);
    }

    /**
     * Clear the collection of subtasks an the task info.
     */
    void clearChildren() {
        children.clear();
    }

    void clearTaskInfo() {
