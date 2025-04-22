        super.setCanceled(b);
        taskName = null;
        runEventLoop();
    }

    /**
     * @see IProgressMonitor#setTaskName
     */
    @Override
