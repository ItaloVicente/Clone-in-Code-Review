    /**
     * Creates a new instance of the receiver.
     *
     * @param actionGroup the action group this action is created in
     * @param shell the parent shell
     */
    public EditWorkingSetAction(WorkingSetFilterActionGroup actionGroup,
            Shell shell) {
        super(WorkbenchMessages.EditWorkingSetAction_text);
        Assert.isNotNull(actionGroup);
        setToolTipText(WorkbenchMessages.EditWorkingSetAction_toolTip);
