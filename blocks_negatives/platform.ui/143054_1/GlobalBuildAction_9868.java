    /**
     * Verify that no manual build is running. If it is then give the use the
     * option to cancel. If they cancel, cancel the jobs and return true,
     * otherwise return false.
     *
     * @return whether or not there is a manual build job running.
     */
    private boolean verifyNoManualRunning() {
