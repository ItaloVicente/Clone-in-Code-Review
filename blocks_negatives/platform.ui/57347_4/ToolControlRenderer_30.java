		restoreHiddenItems.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				removeHiddenTags(toolControl);
			}
		});
