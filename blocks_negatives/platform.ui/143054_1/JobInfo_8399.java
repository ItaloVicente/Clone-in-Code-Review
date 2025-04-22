    }

    /**
     * Begin the task called taskName with the supplied work.
     *
     * @param taskName
     * @param work
     */
    void beginTask(String taskName, int work) {
        taskInfo = new TaskInfo(this, taskName, work);
    }

    @Override
