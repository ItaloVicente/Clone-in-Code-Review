	public static final String ID = PlatformUI.PLUGIN_ID + ".CopyAction"; //$NON-NLS-1$

	private Shell shell;

	private Clipboard clipboard;

	private PasteAction pasteAction;

	public CopyAction(Shell shell, Clipboard clipboard) {
		super(WorkbenchNavigatorMessages.CopyAction_Cop_);
		Assert.isNotNull(shell);
		Assert.isNotNull(clipboard);
		this.shell = shell;
		this.clipboard = clipboard;
		setToolTipText(WorkbenchNavigatorMessages.CopyAction_Copy_selected_resource_s_);
		setId(CopyAction.ID);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, "CopyHelpId"); //$NON-NLS-1$
