/*package*/class CopyAction extends SelectionListenerAction {

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
        super(ResourceNavigatorMessages.CopyAction_title);
        Assert.isNotNull(shell);
        Assert.isNotNull(clipboard);
        this.shell = shell;
        this.clipboard = clipboard;
        setToolTipText(ResourceNavigatorMessages.CopyAction_toolTip);
        setId(CopyAction.ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				INavigatorHelpContextIds.COPY_ACTION);
    }

    /**
     * Creates a new action.
     *
     * @param shell the shell for any dialogs
     * @param clipboard a platform clipboard
     * @param pasteAction a paste action
     *
     * @since 2.0
     */
    public CopyAction(Shell shell, Clipboard clipboard, PasteAction pasteAction) {
        this(shell, clipboard);
        this.pasteAction = pasteAction;
    }


    @Override
