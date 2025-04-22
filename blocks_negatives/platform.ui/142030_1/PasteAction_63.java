    /**
     * Creates a new action.
     *
     * @param shell the shell for any dialogs
     * @param clipboard the clipboard
     */
    public PasteAction(Shell shell, Clipboard clipboard) {
        super(ResourceNavigatorMessages.PasteAction_title);
        Assert.isNotNull(shell);
        Assert.isNotNull(clipboard);
        this.shell = shell;
        this.clipboard = clipboard;
        setToolTipText(ResourceNavigatorMessages.PasteAction_toolTip);
        setId(PasteAction.ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				INavigatorHelpContextIds.PASTE_ACTION);
    }
