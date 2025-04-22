	public DirectoryFieldEditor(String name, String labelText, Composite parent) {
		init(name, labelText);
		setErrorMessage(JFaceResources
				.getString("DirectoryFieldEditor.errorMessage"));//$NON-NLS-1$
		setChangeButtonText(JFaceResources.getString("openBrowse"));//$NON-NLS-1$
		setValidateStrategy(VALIDATE_ON_FOCUS_LOST);
		createControl(parent);
	}
