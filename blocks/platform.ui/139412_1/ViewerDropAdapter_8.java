	public void setFeedbackEnabled(boolean value) {
		feedbackEnabled = value;
	}

	public void setSelectionFeedbackEnabled(boolean value) {
		selectFeedbackEnabled = value;
	}

	public void setScrollExpandEnabled(boolean value) {
		expandEnabled = value;
		scrollEnabled = value;
	}

	public void setExpandEnabled(boolean value) {
		expandEnabled = value;
	}

	public void setScrollEnabled(boolean value) {
		scrollEnabled = value;
	}

	public abstract boolean validateDrop(Object target, int operation, TransferData transferType);
