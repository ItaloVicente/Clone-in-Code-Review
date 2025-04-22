		MenuItem pasteItem = new MenuItem(men, SWT.PUSH);
		pasteItem.setText(UIText.RepositoriesView_PasteMenu);
		pasteItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				pasteAction.run();
			}

		});

