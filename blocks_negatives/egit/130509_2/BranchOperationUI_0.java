				IPreferenceStore store = Activator.getDefault()
						.getPreferenceStore();

				if (store
						.getBoolean(UIPreferences.SHOW_DETACHED_HEAD_WARNING)) {
					String toggleMessage = UIText.BranchResultDialog_DetachedHeadWarningDontShowAgain;

					MessageDialogWithToggle dialog = new MessageDialogWithToggle(
							PlatformUI.getWorkbench().getActiveWorkbenchWindow()
									.getShell(),
							UIText.BranchOperationUI_DetachedHeadTitle, null,
							UIText.BranchOperationUI_DetachedHeadMessage,
							MessageDialog.INFORMATION,
							new String[] { IDialogConstants.CLOSE_LABEL }, 0,
							toggleMessage, false);
					dialog.open();
					if (dialog.getToggleState()) {
						store.setValue(UIPreferences.SHOW_DETACHED_HEAD_WARNING,
								false);
					}
