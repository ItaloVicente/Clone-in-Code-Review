	protected boolean setText(String message) {
		boolean changed = false;
		if (!Objects.equals(message, warningText.getText())) {
			warningText.setText(message);
			changed = true;
		}
		changed |= changeVisibility(true);
		return changed;
