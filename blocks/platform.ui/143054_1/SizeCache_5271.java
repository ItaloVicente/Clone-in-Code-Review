
	public SizeCache() {
		this(null);
	}

	public SizeCache(Control control) {
		setControl(control);
	}

	public void setControl(Control newControl) {
		if (newControl != control && !newControl.isDisposed()) {
			control = newControl;
			if (control == null) {
				independentDimensions = true;
				preferredWidthOrLargerIsMinimumHeight = false;
				widthAdjustment = 0;
				heightAdjustment = 0;
			} else {
				independentDimensions = independentLengthAndWidth(control);
				preferredWidthOrLargerIsMinimumHeight = isPreferredWidthMaximum(control);
				computeHintOffset(control);
