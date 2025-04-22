			Map<String, Integer> buttonLabelToIdMap = new LinkedHashMap<>();
			buttonLabelToIdMap.put(IDEWorkbenchMessages.PromptOnExitDialog_button_label_exit, IDialogConstants.OK_ID);
			buttonLabelToIdMap.put(IDialogConstants.CANCEL_LABEL, IDialogConstants.CANCEL_ID);
			MessageDialogWithToggle dlg =
					new MessageDialogWithToggle(
							parentShell,
