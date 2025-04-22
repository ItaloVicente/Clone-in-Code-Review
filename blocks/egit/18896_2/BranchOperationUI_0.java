
	public void show(final CheckoutResult result) {
		if (result.getStatus() == CheckoutResult.Status.CONFLICTS) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					Shell shell = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell();
					CleanupUncomittedChangesDialog cleanupUncomittedChangesDialog = new CleanupUncomittedChangesDialog(
							shell,
							UIText.BranchResultDialog_CheckoutConflictsTitle,
							NLS.bind(
									UIText.BranchResultDialog_CheckoutConflictsMessage,
									Repository.shortenRefName(target)),
							repository, result.getConflictList());
					cleanupUncomittedChangesDialog.open();
					if (cleanupUncomittedChangesDialog.shouldContinue()) {
						BranchOperationUI op = BranchOperationUI
								.checkoutWithoutQuestion(repository, target);
						op.start();
					}
				}
			});
		} else if (result.getStatus() == CheckoutResult.Status.NONDELETED) {
			boolean show = false;
			List<String> pathList = result.getUndeletedList();
			for (String path : pathList)
				if (new File(repository.getWorkTree(), path).exists()) {
					show = true;
					break;
				}
			if (!show)
				return;
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					Shell shell = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell();
					new NonDeletedFilesDialog(shell, repository, result
							.getUndeletedList()).open();
				}
			});
		} else if (result.getStatus() == CheckoutResult.Status.OK) {
			if (RepositoryUtil.isDetachedHead(repository))
				showDetachedHeadWarning();
		}
	}

	private void showDetachedHeadWarning() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IPreferenceStore store = Activator.getDefault()
						.getPreferenceStore();

				if (store.getBoolean(UIPreferences.SHOW_DETACHED_HEAD_WARNING)) {
					String toggleMessage = UIText.BranchResultDialog_DetachedHeadWarningDontShowAgain;
					MessageDialogWithToggle.openInformation(PlatformUI
							.getWorkbench().getActiveWorkbenchWindow()
							.getShell(),
							UIText.BranchOperationUI_DetachedHeadTitle,
							UIText.BranchOperationUI_DetachedHeadMessage,
							toggleMessage, false, store,
							UIPreferences.SHOW_DETACHED_HEAD_WARNING);
				}
			}
		});
	}

