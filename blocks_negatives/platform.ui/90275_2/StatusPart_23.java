		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(LOG_VIEW_ID);
				} catch (CoreException ce) {
					StatusManager.getManager().handle(ce, WorkbenchPlugin.PI_WORKBENCH);
				}
