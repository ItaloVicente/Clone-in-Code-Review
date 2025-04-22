		propertiesAction = new PropertyDialogAction(new IShellProvider() {
			@Override
			public Shell getShell() {
				return aSite.getViewSite().getShell();
			}
		},delegateSelectionProvider);
