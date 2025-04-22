	private int variableType;

	public PathVariableSelectionDialog(Shell parentShell, int variableType) {
		super(parentShell);
		setTitle(IDEWorkbenchMessages.PathVariableSelectionDialog_title);
		this.variableType = variableType;
		pathVariablesGroup = new PathVariablesGroup(false, variableType,
				event -> updateExtendButtonState());
		pathVariablesGroup.setSaveVariablesOnChange(true);
		setShellStyle(getShellStyle() | SWT.SHEET);
	}

	@Override
