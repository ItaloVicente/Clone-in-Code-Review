	public PasteAction(Shell shell, Clipboard clipboard) {
		super(ResourceNavigatorMessages.PasteAction_title);
		Assert.isNotNull(shell);
		Assert.isNotNull(clipboard);
		this.shell = shell;
		this.clipboard = clipboard;
		setToolTipText(ResourceNavigatorMessages.PasteAction_toolTip);
		setId(PasteAction.ID);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, INavigatorHelpContextIds.PASTE_ACTION);
	}
