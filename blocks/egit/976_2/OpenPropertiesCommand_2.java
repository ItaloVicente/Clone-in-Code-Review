		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage()
							.showView(IPageLayout.ID_PROP_SHEET);
				} catch (PartInitException e1) {
				}
			}
		});
