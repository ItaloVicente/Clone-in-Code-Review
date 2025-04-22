        ProgressManager.getInstance().addListener(this);
        debug =
        	PrefUtil.getAPIPreferenceStore().
        		getBoolean(IWorkbenchPreferenceConstants.SHOW_SYSTEM_JOBS);
    }

    /**
     * Add the new collector to the list of collectors.
     *
     * @param newCollector
     */
    void addCollector(IProgressUpdateCollector newCollector) {
