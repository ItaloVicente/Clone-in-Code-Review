		propertyChangeListener.propertyChange(new PropertyChangeEvent(this,
				property, oldValue, newValue));
	}

	public String getFieldEditorFontName() {
		return JFaceResources.DIALOG_FONT;
	}

	protected Label getLabelControl() {
		return label;
	}

	public Label getLabelControl(Composite parent) {
		if (label == null) {
			label = new Label(parent, SWT.LEFT);
			label.setFont(parent.getFont());
			String text = getLabelText();
			if (text != null) {
