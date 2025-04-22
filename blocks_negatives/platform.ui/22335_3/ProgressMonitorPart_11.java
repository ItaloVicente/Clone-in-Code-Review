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
