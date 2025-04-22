		public void updateEnabledState() {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				setEnabled(true);
				return;
			}
			if (selectAllAction != null) {
				setEnabled(selectAllAction.isEnabled());
				return;
			}
			setEnabled(false);
		}
	}

	public TextActionHandler(IActionBars actionBar) {
		super();
		actionBar.setGlobalActionHandler(ActionFactory.CUT.getId(),
				textCutAction);
		actionBar.setGlobalActionHandler(ActionFactory.COPY.getId(),
				textCopyAction);
		actionBar.setGlobalActionHandler(ActionFactory.PASTE.getId(),
				textPasteAction);
		actionBar.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),
				textSelectAllAction);
		actionBar.setGlobalActionHandler(ActionFactory.DELETE.getId(),
				textDeleteAction);
	}

	public void addText(Text textControl) {
		if (textControl == null) {
