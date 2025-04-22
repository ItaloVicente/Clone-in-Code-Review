    /**
     * Creates a new action.
     *
     * @param shell the shell for any dialogs
     * @param clipboard the clipboard
     */
    public PasteAction(Shell shell, Clipboard clipboard) {
        super(WorkbenchNavigatorMessages.PasteAction_Past_);
        Assert.isNotNull(shell);
        Assert.isNotNull(clipboard);
        this.shell = shell;
        this.clipboard = clipboard;
        setToolTipText(WorkbenchNavigatorMessages.PasteAction_Paste_selected_resource_s_);
        setId(PasteAction.ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, "HelpId"); //$NON-NLS-1$
