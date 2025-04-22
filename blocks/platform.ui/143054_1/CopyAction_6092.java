
	public static final String ID = PlatformUI.PLUGIN_ID + ".CopyAction"; //$NON-NLS-1$

	private Shell shell;

	private Clipboard clipboard;

	private PasteAction pasteAction;

	public CopyAction(Shell shell, Clipboard clipboard) {
		super(ResourceNavigatorMessages.CopyAction_title);
		Assert.isNotNull(shell);
		Assert.isNotNull(clipboard);
		this.shell = shell;
		this.clipboard = clipboard;
		setToolTipText(ResourceNavigatorMessages.CopyAction_toolTip);
		setId(CopyAction.ID);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, INavigatorHelpContextIds.COPY_ACTION);
	}

	public CopyAction(Shell shell, Clipboard clipboard, PasteAction pasteAction) {
		this(shell, clipboard);
		this.pasteAction = pasteAction;
	}

	@Override
