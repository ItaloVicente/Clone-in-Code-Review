    /**
     * Open a dialog on job when it starts to run and close it
     * when the job is finished. Wait for ProgressManagerUtil#SHORT_OPERATION_TIME
     * before opening the dialog. Do not open if it is already done or
     * if the user has set a preference to always run in the background.
     *
     * Parent the dialog from the shell.
     *
     * @param shell The Shell to parent the dialog from or
     * <code>null</code> if the active shell is to be used.
     * @param job The Job that will be reported in the dialog. job
     * must not be <code>null</code>.
     */
    void showInDialog(Shell shell, Job job);
