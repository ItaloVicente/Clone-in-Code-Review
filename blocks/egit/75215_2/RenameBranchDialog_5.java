		final Ref toRename = refFromDialog();

		if (toRename != null) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					BranchRenameDialog dialog = new BranchRenameDialog(
							PlatformUI.getWorkbench().getActiveWorkbenchWindow()
									.getShell(),
							repo, toRename);
					dialog.open();
				}
			});
