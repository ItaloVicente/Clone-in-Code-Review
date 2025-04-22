		this.previousFocusControl = previousFocusControl;
		createContentsAndTable();
		if (!shell.isVisible()) {
			layoutShell();
			quickAccessContents.preOpen();
			shell.setVisible(true);
			addAccessibleListener();
			quickAccessContents.refresh(txtQuickAccess.getText().toLowerCase());
