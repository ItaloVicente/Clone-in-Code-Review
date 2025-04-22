        super.subTask(name);
        runEventLoop();
    }

    @Override
    public void worked(int work) {
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
