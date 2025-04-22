	public WorkingSetMenuContributionItem(int id, WorkingSetFilterActionGroup actionGroup, IWorkingSet workingSet) {
		super(getId(id));
		Assert.isNotNull(actionGroup);
		Assert.isNotNull(workingSet);
		this.id = id;
		this.actionGroup = actionGroup;
		this.workingSet = workingSet;
	}
