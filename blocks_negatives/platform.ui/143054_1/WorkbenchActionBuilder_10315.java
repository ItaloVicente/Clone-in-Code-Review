        return false;
    }

    /**
     * Return whether or not given id matches the id of the coolitems that
     * the workbench creates.
     */
    public boolean isWorkbenchCoolItemId(String id) {
        if (IWorkbenchActionConstants.TOOLBAR_FILE.equalsIgnoreCase(id)) {
