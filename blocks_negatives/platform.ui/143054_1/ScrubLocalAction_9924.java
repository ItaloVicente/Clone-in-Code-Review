    /**
     * Creates a new action.
     *
     * @param shell the shell for any dialogs
     */
    public ScrubLocalAction(Shell shell) {
        super(shell, IDEWorkbenchMessages.ScrubLocalAction_text);
        setToolTipText(IDEWorkbenchMessages.ScrubLocalAction_toolTip);
        setId(ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
