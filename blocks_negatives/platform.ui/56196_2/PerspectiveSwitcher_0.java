		final ToolItem createItem = new ToolItem(perspSwitcherToolbar, SWT.PUSH);
		createItem.setImage(getOpenPerspectiveImage());
		createItem.setToolTipText(WorkbenchMessages.OpenPerspectiveDialogAction_tooltip);
		createItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectPerspective();
			}
