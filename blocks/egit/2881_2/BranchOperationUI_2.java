					MessageDialogWithToggle dialog = MessageDialogWithToggle
							.openInformation(
									getShell(),
									UIText.BranchOperationUI_DetachedHeadTitle,
									UIText.BranchOperationUI_DetachedHeadMessage,
									toggleMessage, false, null, null);
					store.setValue(UIPreferences.SHOW_DETACHED_HEAD_WARNING,
							!dialog.getToggleState());
