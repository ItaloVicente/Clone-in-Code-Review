	public PathEditor(String name, String labelText,
			String dirChooserLabelText, Composite parent) {
		init(name, labelText);
		this.dirChooserLabelText = dirChooserLabelText;
		createControl(parent);
	}
