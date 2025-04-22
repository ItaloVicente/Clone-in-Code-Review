	public BookmarkPropertiesDialog(Shell parentShell) {
		this(parentShell, BookmarkMessages.PropertiesDialogTitle_text);
	}

	public BookmarkPropertiesDialog(Shell parentShell, String title) {
		super(parentShell, title);
		setType(IMarker.BOOKMARK);
	}

	@Override
