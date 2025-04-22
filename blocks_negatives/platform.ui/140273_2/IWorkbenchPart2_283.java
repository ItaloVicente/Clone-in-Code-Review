    /**
     * Returns the name of this part. If this value changes the part must fire a
     * property listener event with {@link IWorkbenchPartConstants#PROP_PART_NAME}.
     *
     * @return the name of this view, or the empty string if the name is being managed
     * by the workbench (not <code>null</code>)
     */
    String getPartName();
