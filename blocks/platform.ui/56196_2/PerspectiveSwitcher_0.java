		boolean showOpenOnPerspectiveBar = PrefUtil.getAPIPreferenceStore()
				.getBoolean(IWorkbenchPreferenceConstants.SHOW_OPEN_ON_PERSPECTIVE_BAR);
		if (showOpenOnPerspectiveBar) {
			final ToolItem openPerspectiveItem = new ToolItem(perspSwitcherToolbar, SWT.PUSH);
			openPerspectiveItem.setImage(getOpenPerspectiveImage());
			openPerspectiveItem.setToolTipText(WorkbenchMessages.OpenPerspectiveDialogAction_tooltip);
			openPerspectiveItem.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					selectPerspective();
				}
