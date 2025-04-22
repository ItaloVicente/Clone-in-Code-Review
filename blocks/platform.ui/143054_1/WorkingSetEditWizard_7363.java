	public WorkingSetEditWizard(IWorkingSetPage editPage) {
		super();
		workingSetEditPage = editPage;
		workingSetEditPage.setWizard(this);
		setWindowTitle(WorkbenchMessages.WorkingSetEditWizard_title);
	}
