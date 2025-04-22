				ListSelectionDialog dlg = ListSelectionDialog.of(modelsToSave)
						.labelProvider(new WorkbenchPartLabelProvider()).preselect(modelsToSave.toArray())
						.title(WorkbenchMessages.EditorManager_saveResourcesTitle)
						.message(stillOpenElsewhere ? WorkbenchMessages.EditorManager_saveResourcesOptionallyMessage
								: WorkbenchMessages.EditorManager_saveResourcesMessage)
						.okButtonText(WorkbenchMessages.SaveableHelper_Save_n_of_m)
						.okButtonTextWhenNoSelection(WorkbenchMessages.SaveableHelper_Save_0_of_m).canCancel(canCancel)
						.asSheet(true)
						.checkboxText(
								stillOpenElsewhere ? WorkbenchMessages.EditorManager_closeWithoutPromptingOption : null)
						.create(shellProvider.getShell());
