    /**
     * The id of this action.
     */

    /**
     * The shell in which to show any dialogs.
     */
    private Shell shell;

    /**
     * System clipboard
     */
    private Clipboard clipboard;

    /**
     * Associated paste action. May be <code>null</code>
     */
    private PasteAction pasteAction;

    /**
     * Creates a new action.
     *
     * @param shell the shell for any dialogs
     * @param clipboard a platform clipboard
     */
    public CopyAction(Shell shell, Clipboard clipboard) {
        super(WorkbenchNavigatorMessages.CopyAction_Cop_);
        Assert.isNotNull(shell);
        Assert.isNotNull(clipboard);
        this.shell = shell;
        this.clipboard = clipboard;
        setToolTipText(WorkbenchNavigatorMessages.CopyAction_Copy_selected_resource_s_);
        setId(CopyAction.ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, "CopyHelpId"); //$NON-NLS-1$
