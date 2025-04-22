		final ToolItem createItem = new ToolItem(psTB, SWT.PUSH);
		createItem.setImage(getOpenPerspectiveImage());
		createItem.setToolTipText(WorkbenchMessages.OpenPerspectiveDialogAction_tooltip);
		createItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectPerspective();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				selectPerspective();
			}
		});
		new ToolItem(psTB, SWT.SEPARATOR);
