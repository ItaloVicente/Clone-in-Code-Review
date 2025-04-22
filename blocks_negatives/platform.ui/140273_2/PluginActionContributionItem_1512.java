    }

    /**
     * Unhook the activity and identifier listener (if necessary);
     *
     * @since 3.1
     */
    private void unhookListeners() {
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .removeActivityManagerListener(this);

        IIdentifier id = getIdentifier();
        if (id != null) {
