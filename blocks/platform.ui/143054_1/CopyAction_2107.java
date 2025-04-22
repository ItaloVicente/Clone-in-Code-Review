	}

	public CopyAction(Shell shell, Clipboard clipboard, PasteAction pasteAction) {
		this(shell, clipboard);
		this.pasteAction = pasteAction;
	}

	@Override
