		if (scale != null && !scale.isDisposed()) {
			scale.setFocus();
		}
	}

	public void setIncrement(int increment) {
		this.incrementValue = increment;
		updateScale();
	}

	public void setMaximum(int max) {
		this.maxValue = max;
		updateScale();
	}

	public void setMinimum(int min) {
		this.minValue = min;
		updateScale();
	}

	public void setPageIncrement(int pageIncrement) {
		this.pageIncrementValue = pageIncrement;
		updateScale();
	}

	private void setValues(int min, int max, int increment, int pageIncrement) {
		this.incrementValue = increment;
		this.maxValue = max;
		this.minValue = min;
		this.pageIncrementValue = pageIncrement;
		updateScale();
	}

	private void updateScale() {
		if (scale != null && !scale.isDisposed()) {
			scale.setMinimum(getMinimum());
			scale.setMaximum(getMaximum());
			scale.setIncrement(getIncrement());
			scale.setPageIncrement(getPageIncrement());
		}
	}

	protected void valueChanged() {
		setPresentsDefaultValue(false);

		int newValue = scale.getSelection();
		if (newValue != oldValue) {
			fireStateChanged(IS_VALID, false, true);
