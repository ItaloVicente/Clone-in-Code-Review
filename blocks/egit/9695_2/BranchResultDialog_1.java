
	private static void showDetachedHeadWarning() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IPreferenceStore store = Activator.getDefault()
						.getPreferenceStore();

				if (store.getBoolean(UIPreferences.SHOW_DETACHED_HEAD_WARNING)) {
					String toggleMessage = UIText.BranchResultDialog_dontShowAgain;
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
