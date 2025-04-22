		QuickAccessDialog dialog = new QuickAccessDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null) {
			@Override
			protected IDialogSettings getDialogSettings() {
				return new DialogSettings("not-persisted"); //$NON-NLS-1$
			}
		};
		dialog.open();
		Text text = dialog.getQuickAccessContents().getFilterText();
		final Table table = dialog.getQuickAccessContents().getTable();
