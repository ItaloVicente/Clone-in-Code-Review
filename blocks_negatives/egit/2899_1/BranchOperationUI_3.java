		showResultDialog();
	}

	private void showResultDialog() {
		BranchResultDialog.show(bop.getResult(), repository, refName);
		try {
			if (ObjectId.isId(repository.getFullBranch()) && bop.getResult().getStatus() == CheckoutResult.Status.OK)
				showDetachedHeadWarning();
		} catch (IOException e) {
		}
	}

	private void showDetachedHeadWarning() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IPreferenceStore store = Activator.getDefault()
						.getPreferenceStore();

				if (store.getString(UIPreferences.SHOW_DETACHED_HEAD_WARNING)
						.equals(MessageDialogWithToggle.PROMPT)) {
					String toggleMessage = UIText.ConfigurationChecker_doNotShowAgain;
					MessageDialogWithToggle.openInformation(getShell(),
							UIText.BranchOperationUI_DetachedHeadTitle,
							UIText.BranchOperationUI_DetachedHeadMessage,
							toggleMessage, false, store,
							UIPreferences.SHOW_DETACHED_HEAD_WARNING);
				}
			}
		});
