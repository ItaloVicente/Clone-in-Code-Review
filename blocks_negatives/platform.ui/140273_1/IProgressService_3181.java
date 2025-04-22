    /**
     * Register the ImageDescriptor to be the icon used for
     * all jobs that belong to family within the workbench.
     * @param icon ImageDescriptor that will be used when the job is being displayed
     * @param family The family to associate with
     * @see Job#belongsTo(Object)
     */
    void registerIconForFamily(ImageDescriptor icon, Object family);
