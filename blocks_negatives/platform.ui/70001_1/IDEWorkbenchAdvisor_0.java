				if (new MessageDialog(null,
						IDEWorkbenchMessages.SystemSettingsChange_title, null,
						IDEWorkbenchMessages.SystemSettingsChange_message,
						MessageDialog.QUESTION, new String[] {
								IDEWorkbenchMessages.SystemSettingsChange_yes,
								IDEWorkbenchMessages.SystemSettingsChange_no },
						1).open() == Window.OK) {
