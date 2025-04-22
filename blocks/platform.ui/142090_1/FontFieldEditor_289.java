		return previewer.getControl();
	}

	protected Label getValueControl(Composite parent) {
		if (valueControl == null) {
			valueControl = new Label(parent, SWT.LEFT);
			valueControl.setFont(parent.getFont());
			valueControl.addDisposeListener(event -> valueControl = null);
		} else {
			checkParent(valueControl, parent);
		}
		return valueControl;
	}

	public void setChangeButtonText(String text) {
		Assert.isNotNull(text);
		changeButtonText = text;
		if (changeFontButton != null) {
