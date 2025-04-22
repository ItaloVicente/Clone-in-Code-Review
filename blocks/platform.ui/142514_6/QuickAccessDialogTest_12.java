		QuickAccessDialog dialog = new QuickAccessDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null) {
			@Override
			protected IDialogSettings getDialogSettings() {
				return new DialogSettings("not-persisted"); //$NON-NLS-1$
			}
		};
		dialog.open();
		final Table table = dialog.getQuickAccessContents().getTable();
		Text text = dialog.getQuickAccessContents().getFilterText();
