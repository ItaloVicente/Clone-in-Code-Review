
	@Override
	protected void applyProperties(Label label) {
		applyProperties(label);

		if (this.alignment != SWT.NONE) {
			label.setAlignment(this.alignment);
		}
		if (this.text != null) {
			label.setText(this.text);
		}
		if (this.image != null) {
			label.setImage(this.image);
		}
	}
