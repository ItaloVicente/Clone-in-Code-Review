    /**
     * Return true if the argument directory is ok to use as a workspace and
     * false otherwise. A version check will be performed, and a confirmation
     * box may be displayed on the argument shell if an older version is
     * detected.
     *
     * @return true if the argument URL is ok to use as a workspace and false
     *         otherwise.
     */
    private boolean checkValidWorkspace(Shell shell, URL url) {
        if (url == null) {
