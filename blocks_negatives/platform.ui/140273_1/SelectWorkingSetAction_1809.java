    /**
     * Creates a new instance of the receiver.
     *
     * @param actionGroup the action group this action is created in
     * @param shell shell to use for opening working set selection dialog.
     */
    public SelectWorkingSetAction(WorkingSetFilterActionGroup actionGroup,
            Shell shell) {
        super(WorkbenchMessages.SelectWorkingSetAction_text);
        Assert.isNotNull(actionGroup);
        setToolTipText(WorkbenchMessages.SelectWorkingSetAction_toolTip);
