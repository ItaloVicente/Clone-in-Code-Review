	private Text hintText;
	private boolean displayHintText;

	Text createHintText(Composite composite, int defaultOrientation) {
		hintText = new Text(composite, SWT.FILL);
		hintText.setOrientation(defaultOrientation);
		displayHintText = true;
		return hintText;
	}

	void hideHintText() {
		if (displayHintText) {
			setHintTextToDisplay(false);
		}
	}

	void showHintText(String text, Color color) {
		hintText.setText(text);
		if (color != null) {
			hintText.setForeground(color);
		}
		if (!displayHintText) {
			setHintTextToDisplay(true);
		}
	}

	private void setHintTextToDisplay(boolean toDisplay) {
		GridData data = (GridData) hintText.getLayoutData();
		data.exclude = !toDisplay;
		hintText.setVisible(toDisplay);
		hintText.requestLayout();
		this.displayHintText = toDisplay;
	}

