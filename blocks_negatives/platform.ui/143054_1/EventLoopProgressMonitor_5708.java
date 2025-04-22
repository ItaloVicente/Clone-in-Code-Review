        super.internalWorked(work);
        runEventLoop();
    }

    /**
     * @see IProgressMonitor#isCanceled
     */
    @Override
