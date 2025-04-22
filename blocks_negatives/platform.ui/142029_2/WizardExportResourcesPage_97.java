    }

    /**
     * Save any editors that the user wants to save before export.
     * @return boolean if the save was successful.
     */
    protected boolean saveDirtyEditors() {
        return IDEWorkbenchPlugin.getDefault().getWorkbench().saveAllEditors(
                true);
    }

    /**
     * Check if widgets are enabled or disabled by a change in the dialog.
     */
    @Override
