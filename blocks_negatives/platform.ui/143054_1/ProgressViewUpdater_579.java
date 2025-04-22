        }
    }

    /**
     * Create the update job that handles the updatesInfo.
     */
    private void createUpdateJob() {
        updateJob = new UIJob(ProgressMessages.ProgressContentProvider_UpdateProgressJob) {
            @Override
