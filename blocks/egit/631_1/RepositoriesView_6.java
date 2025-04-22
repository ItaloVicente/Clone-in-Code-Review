	private void createCopyPathItem(Menu men, final String path) {

		MenuItem copyPath;
		copyPath = new MenuItem(men, SWT.PUSH);
		copyPath.setText(UIText.RepositoriesView_CopyPathToClipboardMenu);
		copyPath.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Clipboard clipboard = new Clipboard(null);
				TextTransfer textTransfer = TextTransfer.getInstance();
				Transfer[] transfers = new Transfer[] { textTransfer };
				Object[] data = new Object[] { path };
				clipboard.setContents(data, transfers);
				clipboard.dispose();
			}

		});

	}

