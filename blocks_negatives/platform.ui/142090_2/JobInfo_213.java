        return name;
    }

    /**
     * Get the display string based on the current status and the name of the
     * job.
     * @param showProgress a boolean to indicate if we should
     * show progress or not.
     *
     * @return String
     */
    private String getDisplayStringWithStatus(boolean showProgress) {
        if (isCanceled()) {
