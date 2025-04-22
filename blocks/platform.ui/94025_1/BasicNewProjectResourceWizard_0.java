		LinkedHashMap<String, Integer> buttonLabelToId = new LinkedHashMap<>();
		buttonLabelToId.put(ResourceMessages.NewProject_perspSwitchButtonLabel, IDialogConstants.YES_ID);
		buttonLabelToId.put(IDialogConstants.NO_LABEL, IDialogConstants.NO_ID);
		MessageDialogWithToggle dialog = MessageDialogWithToggle.open(MessageDialog.QUESTION, window.getShell(),
				ResourceMessages.NewProject_perspSwitchTitle, message, null, false, store,
				IDEInternalPreferences.PROJECT_SWITCH_PERSP_MODE, SWT.NONE, buttonLabelToId);
