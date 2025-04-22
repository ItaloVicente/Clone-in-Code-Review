    /**
     * Initializes the workbench
     */
    private void initAction(){
    	setToolTipText(IDEWorkbenchMessages.MoveResourceAction_toolTip);
        setId(MoveResourceAction.ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
