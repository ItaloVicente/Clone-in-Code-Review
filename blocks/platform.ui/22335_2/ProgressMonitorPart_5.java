		super.setFont(font);
		fLabel.setFont(font);
		fProgressIndicator.setFont(font);
	}

	@Override
	public void setTaskName(String name) {
		fTaskName = name;
		updateLabel();
	}

	@Override
	public void subTask(String name) {
		fSubTaskName = name;
		updateLabel();
	}

	protected void updateLabel() {
		if (blockedStatus == null) {
			String text = taskLabel();
			fLabel.setText(text);
		} else {
