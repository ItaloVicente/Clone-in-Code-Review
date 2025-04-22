    /**
     * The identifier update job.  Lazily initialized.
     */
    private Job deferredIdentifierJob = null;
    
    private final IActivityRegistryListener activityRegistryListener = new IActivityRegistryListener() {
                public void activityRegistryChanged(
                        ActivityRegistryEvent activityRegistryEvent) {
                    readRegistry(false);
                }
            };
