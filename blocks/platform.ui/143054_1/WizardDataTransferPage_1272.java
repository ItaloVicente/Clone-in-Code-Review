				return super.getShellStyle() | SWT.SHEET;
			}
		};

		return dialog.open() == 0;
	}

	protected void restoreWidgetValues() {
	}

	protected void saveWidgetValues() {
	}

	protected void updatePageCompletion() {
		boolean pageComplete = determinePageCompletion();
		setPageComplete(pageComplete);
		if (pageComplete) {
			setErrorMessage(null);
		}
	}

	protected void updateWidgetEnablements() {
	}

	protected boolean validateDestinationGroup() {
		return true;
	}

	protected boolean validateOptionsGroup() {
		return true;
	}

	protected boolean validateSourceGroup() {
		return true;
	}

	protected void createOptionsGroup(Composite parent) {
		Group optionsGroup = new Group(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		optionsGroup.setLayout(layout);
		optionsGroup.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
		optionsGroup.setText(IDEWorkbenchMessages.WizardExportPage_options);
		optionsGroup.setFont(parent.getFont());

		createOptionsGroupButtons(optionsGroup);

	}

	protected void displayErrorDialog(String message) {
		MessageDialog.open(MessageDialog.ERROR, getContainer().getShell(), getErrorDialogTitle(), message, SWT.SHEET);
	}

	protected void displayErrorDialog(Throwable exception) {
		String message = exception.getMessage();
		if (message == null) {
