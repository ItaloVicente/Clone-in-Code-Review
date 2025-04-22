    /**
     * Return an array of recent workspaces sorted with the most recently used at
     * the start.
     */
    public String[] getRecentWorkspaces() {
        return recentWorkspaces;
    }

    /**
     * The argument workspace has been selected, update the receiver.  Does not
     * persist the new values.
     */
    public void workspaceSelected(String dir) {
        selection = dir;
    }

    /**
     * Toggle value of the showDialog persistent setting.
     */
    public void toggleShowDialog() {
        showDialog = !showDialog;
    }
