				MyListSelectionDialog dlg = new MyListSelectionDialog(shellProvider.getShell(), modelsToSave,
						ArrayContentProvider.getInstance(), new WorkbenchPartLabelProvider(),
						stillOpenElsewhere ? WorkbenchMessages.EditorManager_saveResourcesOptionallyMessage
								: WorkbenchMessages.EditorManager_saveResourcesMessage,
						canCancel, stillOpenElsewhere);
				dlg.setInitialSelections(modelsToSave.toArray());
				dlg.setTitle(WorkbenchMessages.EditorManager_saveResourcesTitle);
