    /**
     * Creates a new instance of the receiver.
     *
     * @param actionGroup the action group this action is created in
     */
    public ClearWorkingSetAction(WorkingSetFilterActionGroup actionGroup) {
        super(WorkbenchMessages.ClearWorkingSetAction_text);
        Assert.isNotNull(actionGroup);
        setToolTipText(WorkbenchMessages.ClearWorkingSetAction_toolTip);
        setEnabled(actionGroup.getWorkingSet() != null);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.CLEAR_WORKING_SET_ACTION);
        this.actionGroup = actionGroup;
    }
