		textControl.addListener(SWT.Activate, textControlListener);
		textControl.addListener(SWT.Deactivate, textControlListener);

		textControl.addKeyListener(keyAdapter);
		textControl.addMouseListener(mouseAdapter);

		if (textControl.isFocusControl()) {
			activeTextControl = textControl;
			updateActionsEnableState();
		}
	}

	public void dispose() {
		setCutAction(null);
		setCopyAction(null);
		setPasteAction(null);
		setSelectAllAction(null);
		setDeleteAction(null);
	}

	public void removeText(Text textControl) {
		if (textControl == null) {
