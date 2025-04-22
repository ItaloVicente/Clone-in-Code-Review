				ListSelectionDialog dlg = new ListSelectionDialog(shellProvider.getShell(),
						modelsToSave, new ArrayContentProvider(), new WorkbenchPartLabelProvider(),
						WorkbenchMessages.EditorManager_saveResourcesMessage) {
					@Override
					protected int getShellStyle() {
						return super.getShellStyle() | SWT.SHEET;
					}
				};
				dlg.setInitialSelections(modelsToSave.toArray());
				dlg.setTitle(WorkbenchMessages.EditorManager_saveResourcesTitle);

				if (SaveableHelper.testGetAutomatedResponse() == SaveableHelper.USER_RESPONSE) {
					int result = dlg.open();
					if (result == IDialogConstants.CANCEL_ID) {
						return false;
					}

					modelsToSave = Arrays.asList(dlg.getResult());
				}
