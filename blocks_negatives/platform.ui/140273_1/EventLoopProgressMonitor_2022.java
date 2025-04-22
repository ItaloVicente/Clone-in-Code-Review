        super.setTaskName(name);
        taskName = name;
        runEventLoop();
    }

    /**
     * @see IProgressMonitor#subTask
     */
    @Override
