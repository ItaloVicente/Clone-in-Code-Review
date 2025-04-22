
	private static void createContextMenu(final StyledText styledText, final String sha1String) {
		Menu menu = new Menu(styledText);

		final MenuItem copySHA1MenuItem = new MenuItem(menu, SWT.PUSH);
		copySHA1MenuItem.setText(UIText.Header_contextMenu_copy_SHA1);
		final Shell shell = styledText.getShell();
		copySHA1MenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				copyToClipboard(sha1String, shell);
			}
		});

		final MenuItem copyMenuItem = new MenuItem(menu, SWT.PUSH);
		copyMenuItem.setText(UIText.Header_contextMenu_copy);
		copyMenuItem.setEnabled(false);
		copyMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				styledText.copy();
			}
		});
		styledText.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				copyMenuItem.setEnabled(styledText.getSelectionCount() > 0);
			}
		});

		styledText.setMenu(menu);
	}

	private static void copyToClipboard(String str, Shell shell) {
		Clipboard clipboard= new Clipboard(shell.getDisplay());
		try {
			clipboard.setContents(new String[] { str },	new Transfer[] { TextTransfer.getInstance() });
		} catch (SWTError ex) {
			if (ex.code != DND.ERROR_CANNOT_SET_CLIPBOARD)
				throw ex;
			String title= UIText.Header_copy_SHA1_error_title;
			String message= UIText.Header_copy_SHA1_error_message;
			if (MessageDialog.openQuestion(shell, title, message))
				copyToClipboard(str, shell);
		} finally {
			clipboard.dispose();
		}
	}

