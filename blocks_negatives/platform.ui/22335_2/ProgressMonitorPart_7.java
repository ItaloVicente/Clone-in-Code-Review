        super.setFont(font);
        fLabel.setFont(font);
        fProgressIndicator.setFont(font);
    }

    /*
     *  (non-Javadoc)
     * @see org.eclipse.core.runtime.IProgressMonitor#setTaskName(java.lang.String)
     */
    public void setTaskName(String name) {
        fTaskName = name;
        updateLabel();
    }

    /*
     *  (non-Javadoc)
     * @see org.eclipse.core.runtime.IProgressMonitor#subTask(java.lang.String)
     */
    public void subTask(String name) {
        fSubTaskName = name;
        updateLabel();
    }

    /**
     * Updates the label with the current task and subtask names.
     */
    protected void updateLabel() {
        if (blockedStatus == null) {
            String text = taskLabel();
            fLabel.setText(text);
        } else {
