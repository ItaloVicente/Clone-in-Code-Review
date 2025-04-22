	public WorkingSetNewWizard(WorkingSetDescriptor[] descriptors) {
		super();
		Assert.isTrue(descriptors != null && descriptors.length > 0);
		this.descriptors = descriptors;
		setWindowTitle(WorkbenchMessages.WorkingSetNewWizard_title);
	}
