	private void createParentContextMenu(Hyperlink link, String sha1) {
		Menu contextMenu = new Menu(link);

		final MenuItem copySHA1MenuItem = new MenuItem(contextMenu, SWT.PUSH);
		copySHA1MenuItem.setText(UIText.Header_contextMenu_copy_SHA1);
		final Shell shell = link.getShell();
		copySHA1MenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				ClipboardUtils.copySha1ToClipboard(sha1, shell);
			}
		});
		link.setMenu(contextMenu);
	}

