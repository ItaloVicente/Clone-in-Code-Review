
	@Override
	protected void applyProperties(Button button) {
		super.applyProperties(button);

		if (this.text != null) {
			button.setText(this.text);
		}
		if (this.image != null) {
			button.setImage(this.image);
		}
		this.selectionListeners.forEach(l -> button.addSelectionListener(l));
	}
