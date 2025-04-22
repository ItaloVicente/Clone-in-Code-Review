	private Button changeButton;

	private String changeButtonText;

	protected StringButtonFieldEditor() {
	}

	protected StringButtonFieldEditor(String name, String labelText,
			Composite parent) {
		init(name, labelText);
		createControl(parent);
	}

	@Override
