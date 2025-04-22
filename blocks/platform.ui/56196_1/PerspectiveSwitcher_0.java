		boolean showOpenOnPerspectiveBar = PrefUtil.getAPIPreferenceStore()
				.getBoolean(IWorkbenchPreferenceConstants.SHOW_OPEN_ON_PERSPECTIVE_BAR);
		if (showOpenOnPerspectiveBar) {
			final ToolItem createItem = new ToolItem(psTB, SWT.PUSH);
			createItem.setImage(getOpenPerspectiveImage());
			createItem.setToolTipText(WorkbenchMessages.OpenPerspectiveDialogAction_tooltip);
			createItem.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					selectPerspective();
				}
