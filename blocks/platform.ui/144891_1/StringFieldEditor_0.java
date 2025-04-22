	public StringFieldEditor(String name, String labelText, int width, int heigth, int strategy,
			Composite parent) {
		init(name, labelText);
		widthInChars = width;
		heigthInChars = heigth;
		setValidateStrategy(strategy);
		isValid = false;
		errorMessage = JFaceResources.getString("StringFieldEditor.errorMessage");//$NON-NLS-1$
		createControl(parent);
	}

