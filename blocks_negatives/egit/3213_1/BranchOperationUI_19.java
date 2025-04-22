	private void showDetachedHeadWarning() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IPreferenceStore store = Activator.getDefault()
						.getPreferenceStore();
				boolean hidden = !store.getBoolean(UIPreferences.SHOW_DETACHED_HEAD_WARNING);
				if (!hidden) {
					String toggleMessage = UIText.ConfigurationChecker_doNotShowAgain;
					MessageDialogWithToggle dialog = MessageDialogWithToggle
							.openInformation(
									getShell(),
									UIText.BranchOperationUI_DetachedHeadTitle,
									UIText.BranchOperationUI_DetachedHeadMessage,
									toggleMessage, false, null, null);
					store.setValue(UIPreferences.SHOW_DETACHED_HEAD_WARNING,
							!dialog.getToggleState());
				}
			}
		});
