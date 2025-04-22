	protected void updateLabel() {
		fLabel.getDisplay().asyncExec(() -> {
			if (fLabel.isDisposed() || fLabel.isAutoDirection()) {
				return;
			}
			if (blockedStatus == null) {
				String text = taskLabel();
				fLabel.setText(text);
			} else {
				fLabel.setText(blockedStatus.getMessage());
			}
