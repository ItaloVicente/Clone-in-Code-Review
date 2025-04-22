		fSubTaskName = name;
		updateLabel();
	}

	protected void updateLabel() {
		if (blockedStatus == null) {
			String text = taskLabel();
			fLabel.setText(text);
		} else {
