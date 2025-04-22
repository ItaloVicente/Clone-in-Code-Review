		quickAccessContents.hookFilterText(txtQuickAccess);
		shell = new Shell(txtQuickAccess.getShell(), SWT.RESIZE | SWT.ON_TOP);
		shell.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		shell.setMinimumSize(new Point(MINIMUM_DIALOG_WIDTH, MINIMUM_DIALOG_HEIGHT));
		GridLayoutFactory.fillDefaults().applyTo(shell);
		quickAccessContents.createHintText(shell, Window.getDefaultOrientation());
		table = quickAccessContents.createTable(shell, Window.getDefaultOrientation());
		table.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				table.getDisplay().asyncExec(() -> checkFocusLost(table, txtQuickAccess));
			}
		});
		quickAccessContents.createInfoLabel(shell);
