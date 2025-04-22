			boolean canceled = SaveableHelper.waitForBackgroundSaveJobs(modelsToSave);
			if (canceled) {
				return false;
			}
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
