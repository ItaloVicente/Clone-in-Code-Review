	public WorkbenchWizardSelectionPage(String name, IWorkbench aWorkbench, IStructuredSelection currentSelection,
			AdaptableList elements, String triggerPointId) {
		super(name);
		this.wizardElements = elements;
		this.currentResourceSelection = currentSelection;
		this.workbench = aWorkbench;
		this.triggerPointId = triggerPointId;
		setTitle(WorkbenchMessages.Select);
	}
