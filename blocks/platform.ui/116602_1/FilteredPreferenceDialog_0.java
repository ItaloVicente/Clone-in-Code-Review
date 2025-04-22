		Control control = createBasicHelpControl(parent);
		addExportImportButtonsToHelpControl(control);
		return control;
	}

	protected Control createBasicHelpControl(Composite parent) {
		return super.createHelpControl(parent);
	}

	protected void addExportImportButtonsToHelpControl(Control control) {
		Composite parent = control.getParent();
