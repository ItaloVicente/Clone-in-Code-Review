        super.worked(work);
        runEventLoop();
    }

    /**
     * Return the name of the current task.
     * @return Returns the taskName.
     */
    protected String getTaskName() {
        return taskName;
    }
