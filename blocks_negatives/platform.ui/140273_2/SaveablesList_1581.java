					String message = NLS
							.bind(
									WorkbenchMessages.EditorManager_saveChangesQuestion,
									model.getName());
					dialog = new MessageDialog(shellProvider.getShell(),
							WorkbenchMessages.Save_Resource, null, message,
