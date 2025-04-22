    /**
     * Creates a new instance of the receiver.
     *
     * @param id sequential id of the new instance
     * @param actionGroup the action group this contribution item is created in
     */
    public WorkingSetMenuContributionItem(int id,
            WorkingSetFilterActionGroup actionGroup, IWorkingSet workingSet) {
        super(getId(id));
        Assert.isNotNull(actionGroup);
        Assert.isNotNull(workingSet);
        this.id = id;
        this.actionGroup = actionGroup;
        this.workingSet = workingSet;
    }
