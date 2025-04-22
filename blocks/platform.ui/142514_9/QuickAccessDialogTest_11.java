		QuickAccessDialog dialog = new QuickAccessDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null) {
			@Override
			protected IDialogSettings getDialogSettings() {
				return new DialogSettings("not-persisted");
			}
		};
		dialog.open();
		Text text = dialog.getQuickAccessContents().getFilterText();
		Table table = dialog.getQuickAccessContents().getTable();
