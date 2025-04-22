
	@Override
	protected void applyProperties(Label control) {
		super.applyProperties(control);

		if (this.alignment != SWT.NONE) {
			control.setAlignment(this.alignment);
		}
		if (this.text != null) {
			control.setText(this.text);
		}
		if (this.image != null) {
			control.setImage(this.image);
		}
	}
