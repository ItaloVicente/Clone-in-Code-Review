			if (modelsToSave.size() == 1) {
				Saveable model = (Saveable) modelsToSave.get(0);
				String message = NLS.bind(WorkbenchMessages.EditorManager_saveChangesQuestion,
						model.getName());
				String[] buttons = new String[] { IDialogConstants.YES_LABEL,
						IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL };
				MessageDialog d = new MessageDialog(shellProvider.getShell(),
						WorkbenchMessages.Save_Resource, null, message, MessageDialog.QUESTION,
						buttons, 0) {
					@Override
					protected int getShellStyle() {
						return super.getShellStyle() | SWT.SHEET;
					}
				};

				int choice = SaveableHelper.testGetAutomatedResponse();
				if (SaveableHelper.testGetAutomatedResponse() == SaveableHelper.USER_RESPONSE) {
					choice = d.open();
				}
