	protected void applyProperties(C control) {
		if (this.enabled != null) {
			control.setEnabled(this.enabled.booleanValue());
		}
		if (this.tooltipText != null) {
			control.setToolTipText(this.tooltipText);
		}
		if (this.layoutData != null) {
			control.setLayoutData(this.layoutData);
		}
