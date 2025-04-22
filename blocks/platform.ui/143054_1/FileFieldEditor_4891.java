	private String[] extensions = null;

	private File filterPath = null;

	private boolean enforceAbsolute = false;

	protected FileFieldEditor() {
	}

	public FileFieldEditor(String name, String labelText, Composite parent) {
		this(name, labelText, false, parent);
	}

	public FileFieldEditor(String name, String labelText, boolean enforceAbsolute, Composite parent) {
		this(name, labelText, enforceAbsolute, VALIDATE_ON_FOCUS_LOST, parent);
	}
	public FileFieldEditor(String name, String labelText,
			boolean enforceAbsolute, int validationStrategy, Composite parent) {
		init(name, labelText);
		this.enforceAbsolute = enforceAbsolute;
		setErrorMessage(JFaceResources
				.getString("FileFieldEditor.errorMessage"));//$NON-NLS-1$
		setChangeButtonText(JFaceResources.getString("openBrowse"));//$NON-NLS-1$
		setValidateStrategy(validationStrategy);
		createControl(parent);
	}

	@Override
