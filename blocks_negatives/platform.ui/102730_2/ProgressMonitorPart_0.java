    /**
     * Updates the label with the current task and subtask names.
     */
    protected void updateLabel() {
		if (fLabel.isDisposed() || fLabel.isAutoDirection()) {
			return;
		}
        if (blockedStatus == null) {
            String text = taskLabel();
            fLabel.setText(text);
        } else {
			fLabel.setText(blockedStatus.getMessage());
		}
