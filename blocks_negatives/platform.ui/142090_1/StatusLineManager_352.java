        statusLine = null;

        IContributionItem items[] = getItems();
        for (IContributionItem item : items) {
            item.dispose();
        }
    }

    /**
     * Returns the control used by this StatusLineManager.
     *
     * @return the control used by this manager
     */
    public Control getControl() {
        return statusLine;
    }

    /**
     * Returns the progress monitor delegate. Override this method
     * to provide your own object used to handle progress.
     *
     * @return the IProgressMonitor delegate
     * @since 3.0
     */
    protected IProgressMonitor getProgressMonitorDelegate() {
        return (IProgressMonitor) getControl();
    }

    @Override
