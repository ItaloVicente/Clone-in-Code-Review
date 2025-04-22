        return true;
    }

    /**
     * Return the current perspective setting.
     */
    private String openPerspectiveSetting() {
        return PrefUtil.getAPIPreferenceStore().getString(
                IWorkbenchPreferenceConstants.OPEN_NEW_PERSPECTIVE);
    }

    /**
     * Runs an action for a particular perspective. Opens the perspective supplied
     * in a new window or a new page depending on the workbench preference.
     *
     * @param desc the selected perspective
     */
    @Override
